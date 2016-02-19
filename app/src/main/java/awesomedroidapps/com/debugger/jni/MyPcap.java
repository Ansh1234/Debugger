package awesomedroidapps.com.debugger.jni;

import org.jnetpcap.JBufferHandler;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.nio.JBuffer;

/**
 * @author anshul.jain on 2/19/2016.
 */
public class MyPcap {
  public  native <T> void capture();
}
