package bcy.moviespider.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "mtime_comments")
public class MTimeComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "movieId")
    private long movieId;

    private String caimg;

    @Column(name = "ca")
    private String username;

    @Column(name = "cd")
    private Timestamp time;

    @Column(name = "ce")
    private String content;

    @Column(name = "commentCount")
    private int commentCount;

    @Column(name = "cr")
    private double rate;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getCaimg() {
        return caimg;
    }

    public void setCaimg(String caimg) {
        this.caimg = caimg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
