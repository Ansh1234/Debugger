package awesomedroidapps.com.debugger;

import android.net.VpnService;
import android.util.Log;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author anshul.jain on 2/16/2016.
 */
public class VpnClient {

   static InetSocketAddress mServer = new InetSocketAddress("www.google.com", 180);
  public static void main(String[] args) {
    DatagramChannel tunnel = null;

    boolean connected = false;
    try {
      // Create a DatagramChannel as the VPN tunnel.
      tunnel = DatagramChannel.open();
      tunnel.connect(mServer);
      System.out.println(tunnel.isConnected());
      handshake(tunnel);
      // Protect the tunnel before connecting to avoid loopback.
      }catch (Exception e){
      e.printStackTrace();
    }
  }

  private static void handshake(DatagramChannel tunnel) throws Exception {
    // To build a secured tunnel, we should perform mutual authentication
    // and exchange session keys for encryption. To keep things simple in
    // this demo, we just send the shared secret in plaintext and wait
    // for the server to send the parameters.
    // Allocate the buffer for handshaking.
    ByteBuffer packet = ByteBuffer.allocate(1024);
    // Control messages always start with zero.
    packet.put((byte) 0).put("anshul".getBytes()).flip();
    // Send the secret several times in case of packet loss.
    for (int i = 0; i < 3; ++i) {
      packet.position(0);
      tunnel.write(packet);
    }
    packet.clear();
    // Wait for the parameters within a limited time.
    for (int i = 0; i < 50; ++i) {
      Thread.sleep(100);
      // Normally we should not receive random packets.
      int length = tunnel.read(packet);
      if (length > 0 && packet.get(0) == 0) {
    //    configure(new String(packet.array(), 1, length - 1).trim());
        return;
      }
    }
    throw new IllegalStateException("Timed out");
  }
//  private void configure(String parameters) throws Exception {
//    // If the old interface has exactly the same parameters, use it!
//    if (mInterface != null && parameters.equals(mParameters)) {
//      Log.i(TAG, "Using the previous interface");
//      return;
//    }
//    // Configure a builder while parsing the parameters.
//    VpnService.Builder builder = new VpnService.Builder();
//    for (String parameter : parameters.split(" ")) {
//      String[] fields = parameter.split(",");
//      try {
//        switch (fields[0].charAt(0)) {
//          case 'm':
//            builder.setMtu(Short.parseShort(fields[1]));
//            break;
//          case 'a':
//            builder.addAddress(fields[1], Integer.parseInt(fields[2]));
//            break;
//          case 'r':
//            builder.addRoute(fields[1], Integer.parseInt(fields[2]));
//            break;
//          case 'd':
//            builder.addDnsServer(fields[1]);
//            break;
//          case 's':
//            builder.addSearchDomain(fields[1]);
//            break;
//        }
//      } catch (Exception e) {
//        throw new IllegalArgumentException("Bad parameter: " + parameter);
//      }
//    }
//    // Close the old interface since the parameters have been changed.
//    try {
//      mInterface.close();
//    } catch (Exception e) {
//      // ignore
//    }
//    // Create a new interface using the builder and save the parameters.
//    mInterface = builder.setSession(mServerAddress)
//        .setConfigureIntent(mConfigureIntent)
//        .establish();
//    mParameters = parameters;
//    Log.i(TAG, "New interface: " + parameters);
//  }
}
