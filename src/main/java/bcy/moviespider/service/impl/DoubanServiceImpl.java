package bcy.moviespider.service.impl;

import bcy.moviespider.dao.DoubanDao;
import bcy.moviespider.dao.MtimeDao;
import bcy.moviespider.entity.DoubanInfo;
import bcy.moviespider.entity.MTime;
import bcy.moviespider.entity.MTimeComment;
import bcy.moviespider.service.DoubanService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public String getUrl(String url) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                if (entity != null) {
                    // 打印响应内容
                    String responseBody = EntityUtils.toString(entity);
                    return responseBody;
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void getMovieInfo() {
        List<MTime> mTimes = mtimeDao.findAll();
        String url = "https://api.douban.com/v2/movie/search?q=";
        for (MTime m : mTimes) {
//            if(m.getMovieId()>256235){
            DoubanInfo doubanInfo=new DoubanInfo();

            String idResponse=getUrl(url+m.getName());
//        String idResponse=getUrl("https://api.douban.com/v2/movie/search?q=超时空同居");
            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(idResponse);
            JsonArray array=json.get("subjects").getAsJsonArray();

            if(array.size()!=0) {
                long id = array.get(0).getAsJsonObject().get("id").getAsLong();

                String infoResponse = getUrl("https://api.douban.com/v2/movie/subject/" + id);
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
                doubanInfo.setWriters(writerResult.toString());
                doubanInfo.setDoubanRating(ratingObject.get("average").getAsDouble());

                doubanInfo.setPubdates(pub);

                if (infoJson.get("mainland_pubdate") != null) {
                    doubanInfo.setMainland_pubdate(infoJson.get("mainland_pubdate").getAsString());
                } else {
                    doubanInfo.setMainland_pubdate("");
                }
                doubanInfo.setDurations(durations);
                doubanInfo.setDoubanId(id);

                System.out.println(doubanInfo);
                doubanDao.save(doubanInfo);
//            }
            }
        }
    }
}
