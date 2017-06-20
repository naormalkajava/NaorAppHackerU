package com.example.ericbell.myapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by eric.bell on 6/19/2017.
 */

public class IO {

    public static String readWeb (String url ,String charest) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line;
        URL address = new URL(url);
        URLConnection con = address.openConnection();
        InputStream in = con.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in , charest));
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        finally {
            reader.close();
        }
        return builder.toString();
    }
}
