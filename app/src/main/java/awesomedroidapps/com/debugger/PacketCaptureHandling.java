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
      inputStream = context.getResources().getAssets().open("test.pcap");
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

    /***************************************************************************
     * Third we create a packet handler which will receive packets from the
     * libpcap loop.
     **************************************************************************/
    Ip4 ip = new Ip4();
    Ethernet eth = new Ethernet();
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
      if (packet.hasHeader(eth)) {
        String str = FormatUtils.mac(eth.source());
        System.out.printf("#%d: eth.src=%s\n", packet.getFrameNumber(), str);
      }
      if (packet.hasHeader(ip)) {
        String str = FormatUtils.ip(ip.source());
        System.out.printf("#%d: ip.src=%s\n", packet.getFrameNumber(), str);
      }

      PcapHeader header = packet.getCaptureHeader();

    }

    /*************************************************************************
     * Last thing to do is close the pcap handle
     ************/
    pcap.close();
  }

}

