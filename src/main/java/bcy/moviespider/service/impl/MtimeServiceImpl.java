package bcy.moviespider.service.impl;

import bcy.moviespider.dao.MtimeCommentDao;
import bcy.moviespider.dao.MtimeDao;
import bcy.moviespider.entity.MTime;
import bcy.moviespider.entity.MTimeComment;
import bcy.moviespider.service.MtimeService;
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

@Service
public class MtimeServiceImpl implements MtimeService {

    @Autowired
    MtimeDao mtimeDao;
    @Autowired
    MtimeCommentDao mtimeCommentDao;

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
    public void catchShowHotList(String url) {
        String data = getUrl(url);
        if (data == null || data.equals("")) {
            return;
        }
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(data);
        JsonArray ms = json.get("ms").getAsJsonArray();
        for (int i = 0; i < ms.size(); i++) {
            JsonObject subObject = ms.get(i).getAsJsonObject();
            MTime mTime = new MTime();
            mTime.setMovieId(subObject.get("id").getAsLong());
            mTime.setName(subObject.get("tCn").getAsString());
            mTime.setRate(subObject.get("r").getAsDouble());
            System.out.println(mTime);
            mtimeDao.save(mTime);
        }
    }

    @Override
    public void catchComingList(String url) {
        String data = getUrl(url);
        if (data == null || data.equals("")) {
            return;
        }
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(data);
        JsonArray ms = json.get("moviecomings").getAsJsonArray();
        for (int i = 0; i < ms.size(); i++) {
            JsonObject subObject = ms.get(i).getAsJsonObject();
            MTime mTime = new MTime();
            mTime.setMovieId(subObject.get("id").getAsLong());
            mTime.setName(subObject.get("title").getAsString());
            mTime.setRate(-1);
            System.out.println(mTime);
            mtimeDao.save(mTime);
        }
    }

    @Override
    public void catchComment() {
        List<MTime> mTimes = mtimeDao.findAll();
        String url = "https://api-m.mtime.cn/Showtime/HotMovieComments.api?pageIndex=";
        for (MTime m : mTimes) {
            JsonParser parser = new JsonParser();
            int index = 1;
            String response = "";
            JsonObject json = null;
            JsonObject data;
            JsonArray cts;
            while (true) {
                response = getUrl(url + index + "&movieId=" + m.getMovieId());
                json = (JsonObject) parser.parse(response);
                data = json.getAsJsonObject("data");
                cts = json.get("cts").getAsJsonArray();
                if (cts.size() <= 0) {
                    break;
                }
                for (int i = 0; i < cts.size(); i++) {
                    JsonObject subObject = cts.get(i).getAsJsonObject();
                    MTimeComment mTimeComment = new MTimeComment();
                    mTimeComment.setUsername(subObject.get("ca").getAsString());
                    mTimeComment.setCaimg(subObject.get("caimg").getAsString());
                    mTimeComment.setTime(new Timestamp(subObject.get("cd").getAsLong()));
                    mTimeComment.setMovieId(m.getMovieId());
                    mTimeComment.setContent(subObject.get("ce").getAsString());
                    mTimeComment.setCommentCount(subObject.get("commentCount").getAsInt());
                    mTimeComment.setRate(subObject.get("cr").getAsDouble());
                    mtimeCommentDao.save(mTimeComment);
                }
            }
        }
    }

}
