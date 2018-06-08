package bcy.moviespider.service;

import bcy.moviespider.MoviespiderApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author YZ
 * @Date 2018/6/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoviespiderApplication.class)
public class DoubanServiceTest {
    @Autowired
    DoubanService doubanService;

    @Test
    public void testGetInfo() {
        doubanService.getMovieInfo();
    }

    @Test
    public void testGetComment() {
        doubanService.getMovieComment();
    }
}
