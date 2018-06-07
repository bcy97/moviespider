package bcy.moviespider.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mtime")
public class MTime {

    @Id
    @Column(name = "movieId")
    private long movieId;
    @Column(name = "movieName")
    private String name;
    private double rate;

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "MTime{" +
                "movieId=" + movieId +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                '}';
    }
}
