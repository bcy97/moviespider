package bcy.moviespider.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "douban_comments")
public class DbComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "doubanId")
    private long doubanId;
    private String uid;
    private String avatar;
    private String signature;
    @Column(name = "name")
    private String username;
    private String content;
    @Column(name = "create_at")
    private Timestamp time;

    private int rating;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDoubanId() {
        return doubanId;
    }

    public void setDoubanId(long doubanId) {
        this.doubanId = doubanId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "DbComment{" +
                "id=" + id +
                ", uid=" + uid +
                ", avatar='" + avatar + '\'' +
                ", signature='" + signature + '\'' +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}
