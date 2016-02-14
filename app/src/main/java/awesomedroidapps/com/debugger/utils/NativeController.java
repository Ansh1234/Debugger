package awesomedroidapps.com.debugger.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anshul.jain on 2/14/2016.
 */
public class NativeController {

  public static List<Integer> returnRunningProcesses(String packageName) {

    ArrayList<Integer> runningProcessesList = new ArrayList<>();
    try {

      Process proc = Runtime.getRuntime().exec("ps " + packageName);
      InputStream inputStream = proc.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      //Ignore the headers.
      String headers = reader.readLine();

      while (reader.read() != -1) {
        String output = reader.readLine();
        String[] fields = output.split("\\s+");
        String pid = fields[1];
        runningProcessesList.add(Integer.parseInt(pid));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return runningProcessesList;
  }
}
