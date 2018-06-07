package bcy.moviespider.service;

import bcy.moviespider.MoviespiderApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoviespiderApplication.class)
public class MtimeServiceTest {

    @Autowired
    MtimeService mtimeService;

    @Test
    public void testCatchMovieList() {
        String url1 = "https://api-m.mtime.cn/Showtime/LocationMovies.api?locationId=292";
        String url2 = "https://api-m.mtime.cn/Movie/MovieComingNew.api?locationId=292";
        mtimeService.catchShowHotList(url1);
        mtimeService.catchComingList(url2);
    }

    @Test
    public void testCatchComment() throws Exception {
        mtimeService.catchComment();
    }
}
