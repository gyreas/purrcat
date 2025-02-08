package aadesaed.cat.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Props {
    private Map<String, String> propsMap = new HashMap<String, String>();

    public Props(String f) {
        try {
            BufferedReader resource =
                    new BufferedReader(new InputStreamReader(Props.class.getResourceAsStream(f)));

            String line;
            while (true) {
                line = resource.readLine();
                if (line == null) break;
                String[] kv = line.split(": ");
                String key = kv[0];
                String value = kv[1];
                this.propsMap.put(key, value);
            }
            resource.close();
        } catch (IOException e) {
            System.err.printf("Unable to read property files: %s\n", e.toString());
        }
    }

    public String getVersion() {
        return this.propsMap.get("Version");
    }

    public String getLicense() {
        return this.propsMap.get("License");
    }

    public String getAppName() {
        return this.propsMap.get("Package");
    }

    public String getAuthor() {
        return this.propsMap.get("Author");
    }
}
