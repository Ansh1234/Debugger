package awesomedroidapps.com.debugger;

import android.content.Context;

import org.jnetpcap.JBufferHandler;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.PcapIf;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.packet.JRegistry;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import awesomedroidapps.com.debugger.utils.FileUtils;

/**
 * @author anshul.jain on 2/19/2016.
 */
public class PacketCaptureHandling {

  public static void handleCapture(Context context){
    InputStream inputStream  = null;
    try {
      inputStream = context.getResources().getAssets().open("newtest.pcap");
    } catch (IOException e) {
      e.printStackTrace();
    }
    File file = FileUtils.copyAssets(context, inputStream);
    String FILENAME = file.getAbsolutePath();

    final StringBuilder errbuf = new StringBuilder();

    final Pcap pcap = Pcap.openOffline(FILENAME, errbuf);
    if (pcap == null) {
      System.err.println(errbuf); // Error is stored in errbuf if any
      return;
    }


    if (pcap == null) {
      System.err.printf("Error while opening device for capture: "
          + errbuf.toString());
      return;
    }

    functionPcap2(pcap);
    /*************************************************************************
     * Last thing to do is close the pcap handle
     ************/
    pcap.close();
  }


  static void functionPcap2(Pcap pcap){
    PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {

      public void nextPacket(PcapPacket packet, String user) {

        System.out.printf("Received packet at %s caplen=%-4d len=%-4d %s\n",
            new Date(packet.getCaptureHeader().timestampInMillis()),
            packet.getCaptureHeader().caplen(),  // Length actually captured
            packet.getCaptureHeader().wirelen(), // Original length
            user                                 // User supplied object
        );
      }
    };

    /***************************************************************************
     * Fourth we enter the loop and tell it to capture 10 packets. The loop
     * method does a mapping of pcap.datalink() DLT value to JProtocol ID, which
     * is needed by JScanner. The scanner scans the packet buffer and decodes
     * the headers. The mapping is done automatically, although a variation on
     * the loop method exists that allows the programmer to sepecify exactly
     * which protocol ID to use as the data link type for this pcap interface.
     **************************************************************************/
    pcap.loop(10, jpacketHandler, "jNetPcap rocks!");
  }

  static void functionPcap1(Pcap pcap){

    /***************************************************************************
     * Third we create a packet handler which will receive packets from the
     * libpcap loop.
     **************************************************************************/
    Ip4 ip = new Ip4();
    Ethernet eth = new Ethernet();
    Http http = new Http();
    Tcp tcp = new Tcp();

    PcapHeader hdr = new PcapHeader(JMemory.POINTER);
    JBuffer buf = new JBuffer(JMemory.POINTER);

    /***************************************************************************
     * Third - we must map pcap's data-link-type to jNetPcap's protocol IDs.
     * This is needed by the scanner so that it knows what the first header in
     * the packet is.
     **************************************************************************/
    int id = JRegistry.mapDLTToId(pcap.datalink());

    /***************************************************************************
     * Fourth - we peer header and buffer (not copy, think of C pointers)
     **************************************************************************/
    while (pcap.nextEx(hdr, buf) == Pcap.NEXT_EX_OK) {

      /*************************************************************************
       * Fifth - we copy header and buffer data to new packet object
       ************************************************************************/
      PcapPacket packet = new PcapPacket(hdr, buf);

      /*************************************************************************
       * Six- we scan the new packet to discover what headers it contains
       ************************************************************************/
      packet.scan(id);

            /*
             * We use FormatUtils (found in org.jnetpcap.packet.format package), to
             * convert our raw addresses to a human readable string.
             */
//      if (packet.hasHeader(eth)) {
//        String str = FormatUtils.mac(eth.source());
//        System.out.printf("#%d: eth.src=%s\n", packet.getFrameNumber(), str);
//      }
//      if (packet.hasHeader(ip)) {
//        String str = FormatUtils.ip(ip.source());
//        System.out.printf("#%d: ip.src=%s\n", packet.getFrameNumber(), str);
//      }
      if(packet.hasHeader(http)){
        packet.getHeader(http);
        final String content_length =     http.fieldValue(Http.Response.Content_Length);
        final String response_code = http.fieldValue(Http.Response.ResponseCode);
        //Find if the given packet is a Request/Response Pkt : First get the TCP header
        packet.getHeader(tcp);
        Integer int_tcp_source = new Integer(tcp.source());
        Integer int_tcp_destination = new Integer(tcp.destination());
        if(int_tcp_source!=80 && content_length==null){
          //It is a Request pkt :
          packet.getHeader(http);
          final String ref = http.fieldValue(Http.Request.Referer);
          final String requestUrl = http.fieldValue(Http.Request.RequestUrl);
          final String requestMethod =  http.fieldValue(Http.Request.RequestMethod);
          final String userAgent = http.fieldValue(Http.Request.User_Agent);
          final String connection = http.fieldValue(Http.Request.Connection);
          final String acceptEncoding = http.fieldValue(Http.Request.Accept_Encoding);
          final String cookie = http.fieldValue(Http.Request.Cookie);
          String page_url = http.fieldValue(Http.Request.Host);
          System.out.println("Request Information");
          System.out.println("requestUrl = "+requestUrl);
          System.out.println("requestMethod = "+requestMethod);
          System.out.println("userAgent = "+userAgent);
          System.out.println("connection = "+connection);
          System.out.println("Accept Encoding = "+acceptEncoding);
          System.out.println("Cookie = "+cookie);
          System.out.println("Host = "+page_url);
          System.out.println("-------------------");
        }else{
          packet.getHeader(http);
          final String responseCode = http.fieldValue(Http.Response.ResponseCode);
          final String server = http.fieldValue(Http.Response.Server);

          //System.out.println("it is a response packet");
        }

      PcapHeader header = packet.getCaptureHeader();

    }


  }}

}

