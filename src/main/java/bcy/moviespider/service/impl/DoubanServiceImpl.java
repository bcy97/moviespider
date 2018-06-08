package bcy.moviespider.service.impl;

import bcy.moviespider.dao.DbCommentDao;
import bcy.moviespider.dao.DoubanDao;
import bcy.moviespider.dao.MtimeDao;
import bcy.moviespider.entity.DbComment;
import bcy.moviespider.entity.DoubanInfo;
import bcy.moviespider.entity.MTime;
import bcy.moviespider.service.DoubanService;
import bcy.moviespider.util.HttpUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author YZ
 * @Date 2018/6/7
 */
@Service
public class DoubanServiceImpl implements DoubanService {
    @Autowired
    MtimeDao mtimeDao;
    @Autowired
    DoubanDao doubanDao;
    @Autowired
    DbCommentDao dbCommentDao;

    @Override
    public void getMovieInfo() {
        List<MTime> mTimes = mtimeDao.findAll();
        String url = "https://api.douban.com/v2/movie/search?q=";
        for (MTime m : mTimes) {
//            if(m.getMovieId()>256235){
            DoubanInfo doubanInfo = new DoubanInfo();

            String idResponse = HttpUtil.getUrl(url + m.getName());
//        String idResponse=getUrl("https://api.douban.com/v2/movie/search?q=超时空同居");
            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(idResponse);
            JsonArray array = json.get("subjects").getAsJsonArray();

            if (array.size() != 0) {
                long id = array.get(0).getAsJsonObject().get("id").getAsLong();

                String infoResponse = HttpUtil.getUrl("https://api.douban.com/v2/movie/subject/" + id);
                JsonObject infoJson = (JsonObject) parser.parse(infoResponse);
                JsonObject ratingObject = (JsonObject) infoJson.get("rating");

                JsonArray castArray = infoJson.get("casts").getAsJsonArray();
                JsonArray castResult = new JsonArray();
                for (int i = 0; i < castArray.size(); i++) {
                    castResult.add(((JsonObject) castArray.get(i)).get("name"));
                }

                JsonArray directorArray = infoJson.get("directors").getAsJsonArray();
                JsonArray directorResult = new JsonArray();
                for (int i = 0; i < directorArray.size(); i++) {
                    directorResult.add(((JsonObject) directorArray.get(i)).get("name"));
                }

                String durations = "";
                if (infoJson.get("durations") != null) {
                    durations = infoJson.get("durations").getAsJsonArray().get(0).getAsString();
                }

                JsonArray writerResult = new JsonArray();
                if (infoJson.get("writers") != null) {
                    JsonArray writerArray = infoJson.get("writers").getAsJsonArray();
                    for (int i = 0; i < writerArray.size(); i++) {
                        writerResult.add(((JsonObject) writerArray.get(i)).get("name"));
                    }
                }

                String pub = "";
                if (infoJson.get("pubdates") != null) {
                    pub = infoJson.get("pubdates").getAsJsonArray().get(0).getAsString();
                }


                doubanInfo.setName(m.getName());
                doubanInfo.setByname(infoJson.get("aka").toString());
                doubanInfo.setYear(infoJson.get("year").getAsInt());
                doubanInfo.setCountries(infoJson.get("countries").getAsJsonArray().toString());
                doubanInfo.setImage(((JsonObject) infoJson.get("images")).get("medium").getAsString());
                doubanInfo.setSummary(infoJson.get("summary").getAsString());
                doubanInfo.setGenres(infoJson.get("genres").toString());
                doubanInfo.setDirectors(directorResult.toString());
                doubanInfo.setCasts(castResult.toString());
                doubanInfo.setDoubanRating(ratingObject.get("average").getAsDouble());


                doubanInfo.setDoubanId(id);

                System.out.println(doubanInfo);
                doubanDao.save(doubanInfo);
//            }
            }
        }
    }

    @Override
    public void getMovieComment() {

        String prefix = "https://api.douban.com/v2/movie/subject/";
        String suffix = "/comments?apikey=0b2bdeda43b5688921839c8ecb20399b&count=200&client=&udid=";

        List<DoubanInfo> infos = doubanDao.findAll();
        JsonParser parser = new JsonParser();
        for (DoubanInfo info : infos) {
            String url = prefix + info.getDoubanId() + suffix;
            String response = HttpUtil.getUrl(url);
            JsonObject data = (JsonObject) parser.parse(response);
            JsonArray comments = data.getAsJsonArray("comments");
            for (int i = 0; i < comments.size(); i++) {
                JsonObject comment = comments.get(i).getAsJsonObject();
                DbComment dbComment = new DbComment();
                dbComment.setDoubanId(info.getDoubanId());

                JsonObject author = comment.get("author").getAsJsonObject();
                dbComment.setUid(author.get("uid").getAsString());
                dbComment.setAvatar(author.get("avatar").getAsString());
                dbComment.setSignature(author.get("signature").getAsString());
                dbComment.setUsername(author.get("name").getAsString());

                dbComment.setContent(comment.get("content").getAsString());
                dbComment.setTime(Timestamp.valueOf(comment.get("created_at").getAsString()));

                JsonObject rating = comment.get("rating").getAsJsonObject();
                dbComment.setRating(rating.get("value").getAsInt());

                System.out.println(dbComment);
                try {
                    dbCommentDao.save(dbComment);
                } catch (Exception e) {
                    System.out.println("////////////////////////////////////////////");
                    System.out.println(dbComment);
                    System.out.println("////////////////////////////////////////////");
                }
            }
        }
    }
}
