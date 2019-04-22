package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "MOVIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movie.findAll", query = "SELECT m FROM Movie m")
    , @NamedQuery(name = "Movie.findByMovieId", query = "SELECT m FROM Movie m WHERE m.movieId = :movieId")
    , @NamedQuery(name = "Movie.findByMovieName", query = "SELECT m FROM Movie m WHERE m.movieName = :movieName")
    , @NamedQuery(name = "Movie.findByMovieReleaseDate", query = "SELECT m FROM Movie m WHERE m.movieReleaseDate = :movieReleaseDate")
    , @NamedQuery(name = "Movie.findByMovieRating", query = "SELECT m FROM Movie m WHERE m.movieRating = :movieRating")
    , @NamedQuery(name = "Movie.findByMovieOverview", query = "SELECT m FROM Movie m WHERE m.movieOverview = :movieOverview")
    , @NamedQuery(name = "Movie.findByMovieRatingDesc", query = "SELECT m FROM Movie m ORDER BY m.movieRating DESC")        //OUR QUERY
    , @NamedQuery(name = "Movie.findByGenreAndReleaseDate", query = "SELECT m FROM Movie m WHERE m.genreIdFk = :genreIdFk AND m.movieReleaseDate>= :startDate AND m.movieReleaseDate<= :endDate")})      //OUR QUERY
public class Movie implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MOVIE_ID")
    private Integer movieId;
    @Basic(optional = false)
    @Column(name = "MOVIE_NAME")
    private String movieName;
    @Basic(optional = false)
    @Column(name = "MOVIE_RELEASE_DATE")
    @Temporal(TemporalType.DATE)
    private Date movieReleaseDate;
    //@Max(value=1000)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MOVIE_RATING")
    private Float movieRating;
    @Column(name = "MOVIE_OVERVIEW")
    private String movieOverview;
    @JoinColumn(name = "FAVORITE_LIST_ID_FK", referencedColumnName = "FAVORITE_LIST_ID")
    @ManyToOne
    private FavoriteList favoriteListIdFk;
    @JoinColumn(name = "GENRE_ID_FK", referencedColumnName = "GENRE_ID")
    @ManyToOne
    private Genre genreIdFk;

    public Movie() {
    }

    public Movie(Integer movieId) {
        this.movieId = movieId;
    }

    public Movie(Integer movieId, String movieName, Date movieReleaseDate) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieReleaseDate = movieReleaseDate;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        Integer oldMovieId = this.movieId;
        this.movieId = movieId;
        changeSupport.firePropertyChange("movieId", oldMovieId, movieId);
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        String oldMovieName = this.movieName;
        this.movieName = movieName;
        changeSupport.firePropertyChange("movieName", oldMovieName, movieName);
    }

    public Date getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(Date movieReleaseDate) {
        Date oldMovieReleaseDate = this.movieReleaseDate;
        this.movieReleaseDate = movieReleaseDate;
        changeSupport.firePropertyChange("movieReleaseDate", oldMovieReleaseDate, movieReleaseDate);
    }

    public Float getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(Float movieRating) {
        Float oldMovieRating = this.movieRating;
        this.movieRating = movieRating;
        changeSupport.firePropertyChange("movieRating", oldMovieRating, movieRating);
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        String oldMovieOverview = this.movieOverview;
        this.movieOverview = movieOverview;
        changeSupport.firePropertyChange("movieOverview", oldMovieOverview, movieOverview);
    }

    public FavoriteList getFavoriteListIdFk() {
        return favoriteListIdFk;
    }

    public void setFavoriteListIdFk(FavoriteList favoriteListIdFk) {
        FavoriteList oldFavoriteListIdFk = this.favoriteListIdFk;
        this.favoriteListIdFk = favoriteListIdFk;
        changeSupport.firePropertyChange("favoriteListIdFk", oldFavoriteListIdFk, favoriteListIdFk);
    }

    public Genre getGenreIdFk() {
        return genreIdFk;
    }

    public void setGenreIdFk(Genre genreIdFk) {
        Genre oldGenreIdFk = this.genreIdFk;
        this.genreIdFk = genreIdFk;
        changeSupport.firePropertyChange("genreIdFk", oldGenreIdFk, genreIdFk);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (movieId != null ? movieId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movie)) {
            return false;
        }
        Movie other = (Movie) object;
        if ((this.movieId == null && other.movieId != null) || (this.movieId != null && !this.movieId.equals(other.movieId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Movie[ movieId=" + movieId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
}
