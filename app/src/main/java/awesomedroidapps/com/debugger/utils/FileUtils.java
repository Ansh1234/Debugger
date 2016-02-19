package awesomedroidapps.com.debugger.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author anshul.jain on 2/19/2016.
 */
public class FileUtils {

  public static File copyAssets(Context context,InputStream in) {

    OutputStream out = null;
    String filename = "test.pcap";
    try {
      File outFile = new File(context.getExternalFilesDir(null), filename);
      out = new FileOutputStream(outFile);
      copyFile(in, out);
      return outFile;
    } catch(IOException e) {
      Log.e("tag", "Failed to copy asset file: " + filename, e);
      return null;
    }
    finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          // NOOP
        }
      }
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          // NOOP
        }
      }
    }
  }
  private static void copyFile(InputStream in, OutputStream out) throws IOException {
    byte[] buffer = new byte[1024];
    int read;
    while((read = in.read(buffer)) != -1){
      out.write(buffer, 0, read);
    }
  }
}
