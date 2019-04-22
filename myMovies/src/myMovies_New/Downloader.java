package myMovies_new;

import static myMovies_new.myMoviesNew.apiKey;
import static myMovies_new.myMoviesNew.myMoviesNewGenresURL;
import static myMovies_new.myMoviesNew.myMoviesNewReleaseDateURL;


public class Downloader { 
    
    //μεθοδος για κατεβασμα δεδομενων genres σε String
    public static String downloadAllGenresString() throws Exception {
        TempUtils downloader = new TempUtils();
        String jsonmyGenres = downloader.getAllGenresID(myMoviesNewGenresURL, apiKey);
        return jsonmyGenres;
    }
    
    //μεθοδος για κατεβασμα δεδομενων movies σε String
    public static String downloadAllMoviesString(Integer pageNumber) throws Exception {
        int pageCount = pageNumber;
        TempUtils downloader = new TempUtils();
        String jsonmyMoviesNew = downloader.getAllMoviesID(myMoviesNewReleaseDateURL, apiKey, pageCount);
        return jsonmyMoviesNew;
    }
  
}
