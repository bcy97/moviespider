package bcy.moviespider.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author YZ
 * @Date 2018/6/7
 */
@Entity
@Table(name="movieInfo")
public class DoubanInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="byname")
    private String byname;
    @Column(name="year")
    private int year;
    @Column(name="countries")
    private String countries;
    @Column(name="image")
    private String image;
    @Column(name="summary")
    private String summary;
    @Column(name="genres")
    private String genres;
    @Column(name="directors")
    private String directors;
    @Column(name="casts")
    private String casts;
    @Column(name="writers")
    private String writers;
    @Column(name="doubanRating")
    private double doubanRating;
    @Column(name="pubdates")
    private String pubdates;
    @Column(name="mainland_pubdate")
    private String mainland_pubdate;
    @Column(name="durations")
    private String durations;
    @Column(name="doubanId")
    private long doubanId;

    public String getByname() {
        return byname;
    }

    public void setByname(String byname) {
        this.byname = byname;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getCasts() {
        return casts;
    }

    public void setCasts(String casts) {
        this.casts = casts;
    }

    public String getWriters() {
        return writers;
    }

    public void setWriters(String writers) {
        this.writers = writers;
    }

    public double getDoubanRating() {
        return doubanRating;
    }

    public void setDoubanRating(double doubanRating) {
        this.doubanRating = doubanRating;
    }

    public String getDurations() {
        return durations;
    }

    public void setDurations(String durations) {
        this.durations = durations;
    }

    public long getDoubanId() {
        return doubanId;
    }

    public void setDoubanId(long doubanId) {
        this.doubanId = doubanId;
    }

    public int getYear() {

        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPubdates() {
        return pubdates;
    }

    public void setPubdates(String pubdates) {
        this.pubdates = pubdates;
    }

    public String getMainland_pubdate() {
        return mainland_pubdate;
    }

    public void setMainland_pubdate(String mainland_pubdate) {
        this.mainland_pubdate = mainland_pubdate;
    }
}
