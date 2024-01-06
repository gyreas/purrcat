package aadesaed.cat.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Props {
  private Map<String, String> props_Map = new HashMap<String, String>();

  public Props(String f) {
    try {
      Path p = Paths.get(f);
      BufferedReader resource = Files.newBufferedReader(p);

      String line;
      while (true) {
        line = resource.readLine();
        if (line == null) break;
        String[] kv = line.split(": ");
        String key = kv[0];
        String value = kv[1];
        this.props_Map.put(key, value);
      }
    } catch (IOException e) {
      System.err.printf("Unable to read property files: %s\n", e.toString());
    }
  }

  public String get_Version() {
    return this.props_Map.get("Version");
  }

  public String get_License() {
    return this.props_Map.get("License");
  }

  public String get_App_Name() {
    return this.props_Map.get("Package");
  }

  public String get_Author() {
    return this.props_Map.get("Author");
  }
}
