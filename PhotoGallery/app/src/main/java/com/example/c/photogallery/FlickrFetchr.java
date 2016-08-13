package com.example.c.photogallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c on 2016-08-07.
 */
public class FlickrFetchr {
    private static final String API_KEY = "61d85f40793d1fef143ada94ecc2bdd4";
    private static final String METHOD_RECENT = "flickr.photos.getRecent";
    private static final String METHOD_SEARCH = "flickr.photos.search";
    private static final Uri END_POINT = Uri.parse("https://api.flickr.com/services/rest/")
            .buildUpon()
            .appendQueryParameter("api_key", API_KEY)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .appendQueryParameter("extras", "url_s")
            .build();

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        //네트워크 loopj , volley

        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlSpec);
            connection = (HttpURLConnection) url.openConnection();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + " with " + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public ArrayList<GalleryItem> fetchRecentPhotos() {
        String url = buildUrl(METHOD_RECENT, null);
        return downloadGalleryItems(url);
    }

    public ArrayList<GalleryItem> searchPhotos(String query) {
        if(query.equals("")){
            fetchRecentPhotos();
        }
        String url = buildUrl(METHOD_SEARCH, query);
        return downloadGalleryItems(url);
    }

    private String buildUrl(String method, String query) {
        Uri.Builder uriBuilder = END_POINT.buildUpon().appendQueryParameter("method", method);
        if(method.equals(METHOD_SEARCH)){
            uriBuilder.appendQueryParameter("text",query);
        }

        return uriBuilder.toString();

    }


    private ArrayList<GalleryItem> downloadGalleryItems(String url) {
        ArrayList<GalleryItem> items = new ArrayList<GalleryItem>();
//        String url = Uri.parse("https://api.flickr.com/services/rest/")
//                .buildUpon()
//                .appendQueryParameter("method", METHOD_RECENT)
//                .appendQueryParameter("api_key", API_KEY)
//                .appendQueryParameter("format", "json")
//                .appendQueryParameter("nojsoncallback", "1")
//                .appendQueryParameter("extras", "url_s")
//                .build().toString();
        try {
            String jsonString = getUrlString(url);
            Log.d("json", jsonString);

            JSONObject jsonBody = new JSONObject(jsonString);
            parseItem(items, jsonBody);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

    private void parseItem(List<GalleryItem> items, JSONObject jsonBody) throws JSONException {
        //안드로이드용 json 라이브러리 Gson

        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");
        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);
            GalleryItem item = new GalleryItem();
            item.setId(photoJsonObject.getString("id"));
            item.setCaption(photoJsonObject.getString("title"));
            if (photoJsonObject.has("url_s")) {
                item.setUrl(photoJsonObject.getString("url_s"));
            }
            items.add(item);
        }
    }
}
