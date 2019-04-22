package myMovies_new;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class TempUtils {
   
    //μεθοδος μετατροπης String απο json σε Date
    public static Date convertStringToDate(String dateOfMovie) throws ParseException{
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();
        date = dateFormat.parse(dateOfMovie);
        
        return date;
    }
    
    //κατεβασμα String json με περιεχομενο τα genres
    public String getAllGenresID(String baseUrl, String apiKey) throws MalformedURLException, IOException {
        
            StringBuilder sb = new StringBuilder();
            String urlStr = baseUrl + "?api_key=";
            urlStr += apiKey;

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;

            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();
         return sb.toString();
    }
    
    //κατεβασμα String json με περιεχομενο τις movies
    public String getAllMoviesID(String baseUrl, String apiKey, Integer pageNum) throws MalformedURLException, IOException {
       
            StringBuilder sb = new StringBuilder();
            String urlStr = baseUrl + "&api_key=";
            urlStr += apiKey;
            urlStr += "&include_adult=false&include_video=false&page=";
            urlStr +=pageNum;
            urlStr += "&primary_release_year=>%3D2000&with_genres=28%7C%7C10749%7C%7C878";

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;

            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();
        return sb.toString();
    }
}
