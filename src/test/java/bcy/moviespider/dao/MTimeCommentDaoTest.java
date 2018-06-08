package bcy.moviespider.dao;

import bcy.moviespider.MoviespiderApplication;
import bcy.moviespider.entity.MTimeComment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoviespiderApplication.class)
public class MTimeCommentDaoTest {

    @Autowired
    MtimeCommentDao mtimeCommentDao;

    @Test
    public void testSave() throws Exception {
        MTimeComment mTimeComment = new MTimeComment();
        mTimeComment.setUsername("特本打");
        mTimeComment.setCaimg("aaa");
        mTimeComment.setTime(new Timestamp(1490697296));
        mTimeComment.setMovieId(123456);
        mTimeComment.setContent("非常好");
        mTimeComment.setCommentCount(5);
        mTimeComment.setRate(5.5);
        mtimeCommentDao.save(mTimeComment);
    }
}
