package awesomedroidapps.com.debugger;

import android.content.Context;

import org.jnetpcap.JBufferHandler;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.PcapIf;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;

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

    List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs

    /***************************************************************************
     * First get a list of devices on this system
     **************************************************************************/
    int r = Pcap.findAllDevs(alldevs, errbuf);

    PcapDumper dumper = pcap.dumpOpen(FILENAME); // output file

    /***************************************************************************
     * Fouth we typically create a java handler at this point, which hands the
     * received packets over to the dumper. We are invoking a specilized loop
     * handler that will provide a more efficient native handler function that
     * will dump our packets to the dumper without having to enter java
     * environment.
     **************************************************************************/
    // No java handler needed

    /***************************************************************************
     * Fifth we enter the loop and tell it to capture 10 packets. We pass in the
     * dumper created in step 3. The handler is provided natively.
     **************************************************************************/
    pcap.loop(10, dumper);
    File fileNew = new File(FILENAME);
    System.out.printf("%s file has %d bytes in it!\n", fileNew, file.length());

    PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {

      public void nextPacket(PcapPacket packet, String user) {

        System.out.printf("Received at %s caplen=%-4d len=%-4d %s\n",
            new Date(packet.getCaptureHeader().timestampInMillis()),
            packet.getCaptureHeader().caplen(), // Length actually captured
            packet.getCaptureHeader().wirelen(), // Original length
            user // User supplied object
        );
      }
    };






  }

}
