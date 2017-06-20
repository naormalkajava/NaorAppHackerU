package com.example.ericbell.myapplication;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric.bell on 6/19/2017.
 */

public class YnetDataSource {

    interface  OnYnetArrivedListener {
        void ynetArrived (List<Ynet> data);
    }


    public static void getYnet (final OnYnetArrivedListener listener) {
        new AsyncTask<Void, Void, List<Ynet>>() {
            @Override
            protected List<Ynet> doInBackground(Void... voids) {
              try {
                  String xml = IO.readWeb("http://www.ynet.co.il/Integration/StoryRss2.xml", "Windows-1255");
                  List<Ynet> data = parse(xml);
                  return data;
              }
              catch (Exception e){

                  e.printStackTrace();
              }
              return null;
            }

            @Override
            protected void onPostExecute(List<Ynet> ynets) {
                listener.ynetArrived(ynets);
            }
        }.execute();
    }


    private static List<Ynet> parse (String xml) {
        List<Ynet> data = new ArrayList<>();
        Document document = Jsoup.parse(xml);
        Elements elements = document.getElementsByTag("item");
        for (Element element : elements) {
            String title = element.getElementsByTag("title").first().text().replace("<![CDATA[", "").replace("]]>", "");
            String description = element.getElementsByTag("description").first().text();
            Document descriptionDocument = Jsoup.parse(description);
            String link = descriptionDocument.getElementsByTag("a").first().attr("href");
            String imageSource = descriptionDocument.getElementsByTag("img").first().attr("src");
            String contect = descriptionDocument.text();
            data.add(new Ynet(contect,imageSource,title,link));
        }
        return data;
    }


    static class Ynet {
        String description;
        String ImageSource;
        String title;
        String link;

        public Ynet(String description, String imageSource, String title, String link) {
            this.description = description;
            ImageSource = imageSource;
            this.title = title;
            this.link = link;
        }

        @Override
        public String toString() {
            return "Ynet{" +
                    "description='" + description + '\'' +
                    ", ImageSource='" + ImageSource + '\'' +
                    ", title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    '}';
        }

        public String getDescription() {
            return description;
        }

        public String getImageSource() {
            return ImageSource;
        }

        public String getTitle() {
            return title;
        }

        public String getLink() {
            return link;
        }
    }
}
