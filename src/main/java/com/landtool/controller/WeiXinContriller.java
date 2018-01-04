package com.landtool.controller;

import com.xiaoleilu.hutool.http.HttpUtil;
import com.xiaoleilu.hutool.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: lizhao
 * @Date: 2017-12-19 9:18
 * @Description: 微信跳转页面
 *
 */
@Controller

@RequestMapping("/weixin")
public class WeiXinContriller {

    @RequestMapping(value = {"/suggestion"},method = RequestMethod.GET )
    public String suggestion(){
        String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx4946ef772a088948&secret=2cb51c334aa7c74cf2b6ebaee49b37a0 ";
        Object json=HttpUtil.get(url);
        JSONObject jsonObject = new JSONObject(json);
        String access_token=jsonObject.get("access_token").toString();

        String urls="https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="+access_token;

        String a="{\"type\":\"news\",\"offset\":0, \"count\":10}";
        Object jsons=HttpUtil.post(urls,a);

        JSONObject jsonObjects = new JSONObject(jsons);
        System.out.println(jsonObjects);
        return null;
    }
}
