package bcy.moviespider.service.impl;

import bcy.moviespider.dao.MtimeCommentDao;
import bcy.moviespider.dao.MtimeDao;
import bcy.moviespider.entity.MTime;
import bcy.moviespider.entity.MTimeComment;
import bcy.moviespider.service.MtimeService;
import bcy.moviespider.util.HttpUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class MtimeServiceImpl implements MtimeService {

    @Autowired
    MtimeDao mtimeDao;
    @Autowired
    MtimeCommentDao mtimeCommentDao;


    @Override
    public void catchShowHotList(String url) {
        String data = HttpUtil.getUrl(url);
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
        String data = HttpUtil.getUrl(url);
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
                response = HttpUtil.getUrl(url + index + "&movieId=" + m.getMovieId());
                json = (JsonObject) parser.parse(response);
                data = json.getAsJsonObject("data");
                cts = data.get("cts").getAsJsonArray();
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
                    System.out.println(mTimeComment);
                    try {
                        mtimeCommentDao.save(mTimeComment);
                    } catch (Exception e) {
                        System.out.println("/////////////////////////////////////");
                        System.out.println(mTimeComment);
                        System.out.println("/////////////////////////////////////");
                        continue;
                    }
                }
                index++;
            }
        }
    }

}
