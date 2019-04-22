package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.FavoriteList;
import model.Genre;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-02-05T17:51:58")
@StaticMetamodel(Movie.class)
public class Movie_ { 

    public static volatile SingularAttribute<Movie, Date> movieReleaseDate;
    public static volatile SingularAttribute<Movie, Float> movieRating;
    public static volatile SingularAttribute<Movie, String> movieOverview;
    public static volatile SingularAttribute<Movie, Genre> genreIdFk;
    public static volatile SingularAttribute<Movie, Integer> movieId;
    public static volatile SingularAttribute<Movie, FavoriteList> favoriteListIdFk;
    public static volatile SingularAttribute<Movie, String> movieName;

}