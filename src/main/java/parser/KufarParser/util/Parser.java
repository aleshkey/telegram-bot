package parser.KufarParser.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;

public class Parser {

    public static JSONArray parse(String url){
        JSONArray jsonArray = new JSONArray();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements listings = doc.select("section");

            for (Element listing : listings) {
                JSONObject object = new JSONObject();
                String title = Objects.requireNonNull(listing.selectFirst("h3")).text();

                String price = Objects.requireNonNull(listing.selectFirst("span")).text();

                String link = Objects.requireNonNull(listing.selectFirst("a")).attr("href");

                object.put("title", title);
                object.put("price", price);
                object.put("link", link);
                jsonArray.put(object);
            }
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
        return jsonArray;
    }
}
