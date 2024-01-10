package com.yupi.springbootinit;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2023/11/14 12:46
 * @Author: Lowell
 * @Description:
 **/

@SpringBootTest
public class CrawlerTest {

    @Resource
    private PostService postService;

    @Test
    void testFetchPicture() throws IOException {
        int current = 1;
        String url = String.format("https://cn.bing.com/images/search?q=山峰图片&qpvt=山峰图片&form=BESBTB&first=1&ensearch="+ current);
        Document doc = Jsoup.connect(url).get();
//        System.out.println("doc = " + doc);
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictures = new ArrayList<>();
        System.out.println("elements = " + elements);
        for (Element element : elements){
            // 1、取图片地址（murl）
            String m = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");
//            System.out.println("murl = " + murl);
            // 2、取标题
            String title = element.select(".inflnk").get(0).attr("aria-label");
//            System.out.println("title = " + title);
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
            pictures.add(picture);
        }
        /*Elements  newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
        }*/
        System.out.println("pictures = " + pictures);
    }


    @Test
    void testFetchPassage() {
        // 1、获取数据
        String json = "{\n" +
                "  \"current\": 1,\n" +
                "  \"pageSize\": 8,\n" +
                "  \"sortField\": \"createTime\",\n" +
                "  \"sortOrder\": \"descend\",\n" +
                "  \"category\": \"文章\",\n" +
                "  \"reviewStatus\": 1\n" +
                "}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest.post(url)
                .body(json)
                .execute().body();
//        System.out.println("result = " + result);
        // 2、JSON转对象
        Map<String, Object> map = JSONUtil.toBean(result, Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject tempRecord = (JSONObject) record;
            Post post = new Post();
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            JSONArray tags = (JSONArray)tempRecord.get("tags");
            List<String> tagList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));
            post.setUserId(1L);
            postList.add(post);

        }
        System.out.println("postList = " + postList);
       /* System.out.println("records = " + records);
        System.out.println("map = " + map);*/

        // 3、数据入库
        boolean b = postService.saveBatch(postList);
        Assertions.assertTrue(b);
    }

}





























