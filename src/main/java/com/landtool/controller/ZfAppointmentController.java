package com.landtool.controller;



import com.github.pagehelper.PageHelper;
import com.landtool.dao.ZfAppointmentMapper;
import com.landtool.entity.*;
import com.landtool.utils.MD5Util;
import com.landtool.utils.PageBean;
import com.landtool.utils.sendGet;

import com.xiaoleilu.hutool.*;
import com.xiaoleilu.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController ;
import sun.font.FontRunIterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author lizhao
 */

@EnableAutoConfiguration
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/ZfAppointment")
class ZfAppointmentController {

    @Autowired
    private ZfAppointmentMapper zfAppointment;


    /**
     * @Author: lizhao
     * @Date: 2017-12-15 9:38
     * @param :null
     * @Description:查询活动预约表
     *
     */
    @RequestMapping(value={"/selectAll"}, method= RequestMethod.GET)
    public List<ZfAppointment> selectAll(){
        PageHelper.startPage(1,1);

        List<ZfAppointment> zf=zfAppointment.selectAll();
        int countNums=zfAppointment.selectALLItem();
        PageBean<ZfAppointment> pageData=new PageBean<>(1,2,countNums);
        pageData.setItems(zf);


        return pageData.getItems();
    }

    /**
     * @Author: lizhao
     * @Date: 2017-12-15 9:53
     * @param data:zf_Appointment表格实体类
     * @Description:插入活动预约表数据
     *
     */
    @RequestMapping(value = {"/insertZf"},method = RequestMethod.POST)
    public void insertZf(ZfAppointment data){

        zfAppointment.addZfAppointment(data);

    }

    /**
     * @Author: lizhao
     * @Date: 2017-12-15 9:40
     * @Description:江门飞沙滩
     *
     */
    @RequestMapping(value = {"/jiangmenfeishatan"},method = RequestMethod.GET,produces = "text/plain;charset=UTF-8")
    public String jiangmenfeishatan(){
        String url = "http://g.hyyb.org/systems/HyybServices/City_jiangmen/ForecastXML_single.php?sea=%E6%B1%9F%E9%97%A8%E9%A3%9E%E6%B2%99%E6%BB%A9";
        // 访问链接并获取页面内容
        String result = sendGet.sendGet(url);
        String zhengzhe="<table[^>]*>[\\s\\S]*</table>";
        String text = sendGet.RegexString(result,zhengzhe).replace("\t","").trim();
        System.out.println(text);

        List<String> list=new ArrayList<>();
        int cishu=5;
        for (int i = 0; i <cishu ; i++) {
            String linshi=text.substring(text.indexOf("<tr>"),text.indexOf("</tr>"))+"</tr>";
            list.add(linshi) ;
            text=text.replace(linshi,"").trim();
        }
        String yubaoquyu= list.get(0).substring(list.get(0).indexOf("<tr><td colspan =\"6\">"), list.get(0).indexOf("</td></tr>")).replace("<tr><td colspan =\"6\">","");
        //第二行
        List dierhang=sendGet.tiqu6(list.get(1),new ArrayList());
        //第三行 江门飞沙 三天的数据
        List disanhang=sendGet.tiqu6(list.get(2),new ArrayList());
        List disihang=sendGet.tiqu6(list.get(3),new ArrayList());
        List diwuhang=sendGet.tiqu6(list.get(4),new ArrayList());

        return "{ \"yubaoquyu\":" + "\"" + yubaoquyu + "\"" + ",\"yubaoshijian\":[{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + disanhang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + disanhang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + disanhang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + disanhang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + disanhang.get(4) + "\"" + "," + "\"" + dierhang.get(5) + "\"" + ":" + "\"" + disanhang.get(5) + "\"" + "},{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + disihang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + disihang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + disihang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + disihang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + disihang.get(4) + "\"" + "," + "\"" + dierhang.get(5) + "\"" + ":" + "\"" + disihang.get(5) + "\"" + "},{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + diwuhang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + diwuhang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + diwuhang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + diwuhang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + diwuhang.get(4) + "\"" + "," + "\"" + dierhang.get(5) + "\"" + ":" + "\"" + diwuhang.get(5) + "\"" + "}]}";

    }


    /**
     * @Author: lizhao
     * @Date: 2017-12-15 9:40
     * @param:null
     * @Description:万山底渔场
     *
     */
    @RequestMapping(value = {"/wanshandiyuchang"},method =RequestMethod.GET )
    public String wanshandiyuchang(){
        // 定义即将访问的链接
        String url = "http://g.hyyb.org/systems/HyybServices/City_jiangmen/ForecastXML_single.php?sea=%E4%B8%87%E5%B1%B1%E5%BA%95%E6%B8%94%E5%9C%BA";
        // 访问链接并获取页面内容
        String result = sendGet.sendGet(url);
        String zhengzhe="<table[^>]*>[\\s\\S]*</table>";
        String text = sendGet.RegexString(result,zhengzhe).replace("\t","").trim();
        System.out.println(text);

        List<String> list=new ArrayList<>();
        int cishu=5;
        for (int i = 0; i <cishu ; i++) {
            String linshi=text.substring(text.indexOf("<tr>"),text.indexOf("</tr>"))+"</tr>";
            list.add(linshi) ;
            text=text.replace(linshi,"").trim();
        }
        String yubaoquyu= list.get(0).substring(list.get(0).indexOf("<tr><td colspan =\"7\">"), list.get(0).indexOf("</td></tr>")).replace("<tr><td colspan =\"7\">","");
        //第二行
        List dierhang=sendGet.tiqu7(list.get(1),new ArrayList());
        //第三行 江门飞沙 三天的数据
        List disanhang=sendGet.tiqu7(list.get(2),new ArrayList());
        List disihang=sendGet.tiqu7(list.get(3),new ArrayList());
        List diwuhang=sendGet.tiqu7(list.get(4),new ArrayList());

        return "{ \"yubaoquyu\":" + "\"" + yubaoquyu + "\"" + ",\"yubaoshijian\":[{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + disanhang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + disanhang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + disanhang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + disanhang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + disanhang.get(4) + "\"" + "," + "\"" + dierhang.get(5) + "\"" + ":" + "\"" + disanhang.get(5) + "\"" + "," + "\"" + dierhang.get(6) + "\"" + ":" + "\"" + disanhang.get(6) + "\"" + "},{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + disihang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + disihang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + disihang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + disihang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + disihang.get(4) + "\"" + "," + "\"" + dierhang.get(5) + "\"" + ":" + "\"" + disihang.get(5) + "\"" + "," + "\"" + dierhang.get(6) + "\"" + ":" + "\"" + disihang.get(6) + "\"" + "},{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + diwuhang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + diwuhang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + diwuhang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + diwuhang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + diwuhang.get(4) + "\"" + "," + "\"" + dierhang.get(5) + "\"" + ":" + "\"" + diwuhang.get(5) + "\"" + "," + "\"" + dierhang.get(6) + "\"" + ":" + "\"" + diwuhang.get(6) + "\"" + "}]}";
    }


    /**
     * @Author: lizhao
     * @Date: 2017-12-15 9:40
     * @param :null
     * @Description:沙堤口渔场
     *
     */
    @RequestMapping(value ={"/shatikouyuchang"},method =RequestMethod.GET )
    public  String shatikouyuchang(){
        // 定义即将访问的链接
        String url = "http://g.hyyb.org/systems/HyybServices/City_jiangmen/ForecastXML_single.php?sea=%E6%B2%99%E5%A0%A4%E5%8F%A3%E6%B8%94%E5%9C%BA";
        // 访问链接并获取页面内容
        String result = sendGet.sendGet(url);
        String zhengzhe="<table[^>]*>[\\s\\S]*</table>";
        String text = sendGet.RegexString(result,zhengzhe).replace("\t","").trim();
        System.out.println(text);

        List<String> list= new ArrayList<>();
        int cishu=5;
        for (int i = 0; i <cishu ; i++) {
            String linshi=text.substring(text.indexOf("<tr>"),text.indexOf("</tr>"))+"</tr>";
            list.add(linshi) ;
            text=text.replace(linshi,"").trim();
        }
        String yubaoquyu= list.get(0).substring(list.get(0).indexOf("<tr><td colspan =\"7\">"), list.get(0).indexOf("</td></tr>")).replace("<tr><td colspan =\"7\">","");
        //第二行
        List dierhang=sendGet.tiqu7(list.get(1),new ArrayList());
        //第三行 江门飞沙 三天的数据
        List disanhang=sendGet.tiqu7(list.get(2),new ArrayList());
        List disihang=sendGet.tiqu7(list.get(3),new ArrayList());
        List diwuhang=sendGet.tiqu7(list.get(4),new ArrayList());

        return "{ \"yubaoquyu\":" + "\"" + yubaoquyu + "\"" + ",\"yubaoshijian\":[{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + disanhang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + disanhang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + disanhang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + disanhang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + disanhang.get(4) + "\"" + "," + "\"" + dierhang.get(5) + "\"" + ":" + "\"" + disanhang.get(5) + "\"" + "," + "\"" + dierhang.get(6) + "\"" + ":" + "\"" + disanhang.get(6) + "\"" + "},{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + disihang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + disihang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + disihang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + disihang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + disihang.get(4) + "\"" + "," + "\"" + dierhang.get(5) + "\"" + ":" + "\"" + disihang.get(5) + "\"" + "," + "\"" + dierhang.get(6) + "\"" + ":" + "\"" + disihang.get(6) + "\"" + "},{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + diwuhang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + diwuhang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + diwuhang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + diwuhang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + diwuhang.get(4) + "\"" + "," + "\"" + dierhang.get(5) + "\"" + ":" + "\"" + diwuhang.get(5) + "\"" + "," + "\"" + dierhang.get(6) + "\"" + ":" + "\"" + diwuhang.get(6) + "\"" + "}]}";
    }

    //银洲湖港区
    /**
     * @Author: lizhao
     * @Date: 2017-12-15 9:40
     * @param: null
     * @Description:银洲湖港区
     *
     */
    @RequestMapping(value = {"/yinzhouhugangqu"},method =RequestMethod.GET)
    public String yinzhouhugangqu(){
        // 定义即将访问的链接
        String url = "http://g.hyyb.org/systems/HyybServices/City_jiangmen/ForecastXML_single.php?sea=%E9%93%B6%E6%B4%B2%E6%B9%96%E6%B8%AF%E5%8C%BA";
        // 访问链接并获取页面内容
        String result = sendGet.sendGet(url);
        String zhengzhe="<table[^>]*>[\\s\\S]*</table>";
        String text = sendGet.RegexString(result,zhengzhe).replace("\t","").trim();
        System.out.println(text);

        List<String> list= new ArrayList<>();
        int cishu=5;
        for (int i = 0; i <cishu ; i++) {
            String linshi=text.substring(text.indexOf("<tr>"),text.indexOf("</tr>"))+"</tr>";
            list.add(linshi) ;
            text=text.replace(linshi,"").trim();
        }
        String yubaoquyu= list.get(0).substring(list.get(0).indexOf("<tr><td colspan =\"5\">"), list.get(0).indexOf("</td></tr>")).replace("<tr><td colspan =\"5\">","");
        //第二行
        List dierhang=sendGet.tiqu5(list.get(1),new ArrayList());
        //第三行 江门飞沙 三天的数据
        List disanhang=sendGet.tiqu5(list.get(2),new ArrayList());
        List disihang=sendGet.tiqu5(list.get(3),new ArrayList());
        List diwuhang=sendGet.tiqu5(list.get(4),new ArrayList());

        return "{ \"yubaoquyu\":" + "\"" + yubaoquyu + "\"" + ",\"yubaoshijian\":[{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + disanhang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + disanhang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + disanhang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + disanhang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + disanhang.get(4) + "\"" + "},{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + disihang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + disihang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + disihang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + disihang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + disihang.get(4) + "\"" + "},{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + diwuhang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + diwuhang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + diwuhang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + diwuhang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + diwuhang.get(4) + "\"" + "}]}";
    }

    /**
     * @Author: lizhao
     * @Date: 2017-12-15 9:41
     * @param :null
     * @Description:上川岛旅游区
     *
     */
    @RequestMapping(value = {"/shangchaundaolvyouqu"},method =RequestMethod.GET,produces = "text/plain;charset=UTF-8")
    public  String shangchaundaolvyouqu(){
        String url = "http://g.hyyb.org/systems/HyybServices/City_jiangmen/ForecastXML_single.php?sea=%E4%B8%8A%E5%B7%9D%E5%B2%9B%E6%97%85%E6%B8%B8%E5%8C%BA";
        // 访问链接并获取页面内容
        String result =sendGet.sendGet(url);
        String zhengzhe="<table[^>]*>[\\s\\S]*</table>";
        String text =sendGet.RegexString(result,zhengzhe).replace("\t","").trim();
        System.out.println(text);

        List<String> list=new ArrayList<>();
        int cishu=5;
        for (int i = 0; i <cishu ; i++) {
            String linshi=text.substring(text.indexOf("<tr>"),text.indexOf("</tr>"))+"</tr>";
            list.add(linshi) ;
            text=text.replace(linshi,"").trim();
        }
        String yubaoquyu= list.get(0).substring(list.get(0).indexOf("<tr><td colspan =\"5\">"), list.get(0).indexOf("</td></tr>")).replace("<tr><td colspan =\"5\">","");

        //第二行
        List dierhang=sendGet.tiqu5(list.get(1),new ArrayList());
        //第三行 江门飞沙 三天的数据
        List disanhang=sendGet.tiqu5(list.get(2),new ArrayList());
        List disihang=sendGet.tiqu5(list.get(3),new ArrayList());
        List diwuhang=sendGet.tiqu5(list.get(4),new ArrayList());
        return "{ \"yubaoquyu\":" + "\"" + yubaoquyu + "\"" + ",\"yubaoshijian\":[{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + disanhang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + disanhang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + disanhang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + disanhang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + disanhang.get(4) + "\"" + "},{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + disihang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + disihang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + disihang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + disihang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + disihang.get(4) + "\"" + "},{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + diwuhang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + diwuhang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + diwuhang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + diwuhang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + diwuhang.get(4) + "\"" + "}]}";

    }


    /**
     * @Author: lizhao
     * @Date: 2017-12-28 9:08
     * @param :null
     * @Description:江门王府州
     *
     */
    @RequestMapping(value = {"/jiangmenwangfuzhou"},method =RequestMethod.GET,produces = "text/plain;charset=UTF-8")
    public  String jiangmenwangfuzhou(){
        String url="http://g.hyyb.org/systems/HyybServices/City_jiangmen/MForecastXML_single.php?sea=%E6%B1%9F%E9%97%A8%E7%8E%8B%E5%BA%9C%E6%B4%B2";
        String result =sendGet.sendGet(url);
        String zhengzhe="<table[^>]*>[\\s\\S]*</table>";
        String text =sendGet.RegexString(result,zhengzhe).replace("\t","").trim();
        System.out.println(text);

        List<String> list=new ArrayList<>();
        int cishu=5;
        for (int i = 0; i <cishu ; i++) {
            String linshi=text.substring(text.indexOf("<tr>"),text.indexOf("</tr>"))+"</tr>";
            list.add(linshi) ;
            text=text.replace(linshi,"").trim();
        }
        String yubaoquyu= list.get(0).substring(list.get(0).indexOf("<tr><td colspan =\"5\">"), list.get(0).indexOf("</td></tr>")).replace("<tr><td colspan =\"5\">","");

        //第二行
        List dierhang=sendGet.tiqu5(list.get(1),new ArrayList());
        //第三行 江门飞沙 三天的数据
        List disanhang=sendGet.tiqu5(list.get(2),new ArrayList());
        List disihang=sendGet.tiqu5(list.get(3),new ArrayList());
        List diwuhang=sendGet.tiqu5(list.get(4),new ArrayList());
        return "{ \"yubaoquyu\":" + "\"" + yubaoquyu + "\"" + ",\"yubaoshijian\":[{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + disanhang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + disanhang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + disanhang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + disanhang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + disanhang.get(4) + "\"" + "},{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + disihang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + disihang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + disihang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + disihang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + disihang.get(4) + "\"" + "},{" + "\"" + dierhang.get(0) + "\"" + ":" + "\"" + diwuhang.get(0) + "\"" + "," + "\"" + dierhang.get(1) + "\"" + ":" + "\"" + diwuhang.get(1) + "\"" + "," + "\"" + dierhang.get(2) + "\"" + ":" + "\"" + diwuhang.get(2) + "\"" + "," + "\"" + dierhang.get(3) + "\"" + ":" + "\"" + diwuhang.get(3) + "\"" + "," + "\"" + dierhang.get(4) + "\"" + ":" + "\"" + diwuhang.get(4) + "\"" + "}]}";

    }

    /**
     * @Author: lizhao
     * @Date: 2017-12-15 9:41
     * @param :null
     * @Description:查询无公害产品列表
     *
     */
    @RequestMapping(value = {"/selectPollutionfree"},method =RequestMethod.GET )
    public List<ZfPollutionfree> selectPollutionfree(){
       return zfAppointment.SelectPollutionfree();
    }


    /**
     * @Author: lizhao
     * @Date: 2017-12-15 9:41
     * @param :null
     * @Description:办事指南页面数据加载
     *
     */
    @RequestMapping(value = {"/selectChangYongDianHua"},method = RequestMethod.GET)
    public List<ZfChangyongdianhua> selectChangYongDianHua(){
        return  zfAppointment.SelectChangyongdianhua();
    }

    /**
     * @Author: lizhao
     * @Date: 2017-12-15 9:42
     * @param :ZfSuggestion
     * @Description:意见征求(用户插入数据接口)
     *
     */

    @RequestMapping(value = {"/suggestion"},method = RequestMethod.POST)
    public  void suggestion(ZfSuggestion data){
        zfAppointment.suggestion(data);
    }

    /**
     * @Author: lizhao
     * @Date: 2017-12-19 10:47
     * @Description:读取建议
     *
     */
    @RequestMapping(value = {"/selectSuggestion"},method = RequestMethod.GET)
    public List<ZfSuggestion> selectSuggestion(){
        return  zfAppointment.selectSuggestion();
    }

    /**
     * @Author: lizhao
     * @Date: 2017-12-19 13:41
     * @param currentPage:当前页
     * @param pageSize:每页记录数
     * @Description:工作动态
     *
     */
    @RequestMapping(value = {"/selectZongheyaowen"},method = RequestMethod.GET)
    public PageBean selectZongheyaowen(int currentPage,int pageSize){

        PageHelper.startPage(currentPage, pageSize);
        List<ZfZongheyaowen> list = zfAppointment.selectZongheyaowen();
        int count=zfAppointment.selectZongheyaowenList();
        PageBean<ZfZongheyaowen> pageData=new PageBean<>(currentPage,pageSize,count);
        pageData.setItems(list);
        return  pageData;
    }

    /**
     * @Author: lizhao
     * @Date: 2017-12-19 16:30
     * @param
     * @Description:通知通告政策法规是一张表
     *
     */
    @RequestMapping(value = {"/selectZcfg"},method = RequestMethod.GET)
    public PageBean selectZcfg(int currentPage,int pageSize){
        PageHelper.startPage(currentPage, pageSize);
        List<ZfZcfg> list=zfAppointment.selectZcfg();
        int count=zfAppointment.selectZcfgSize();
        PageBean<ZfZcfg> pageData=new PageBean<>(currentPage,pageSize,count);
        pageData.setItems(list);
        return  pageData;
    }



    /**
     * @Author: lizhao
     * @Date: 2017-12-27 10:02
     * @param
     * @Description:登陆
     *
     */
    @RequestMapping(value = {"/login"},method = RequestMethod.GET)
    public Map<String,Object> login(ZfUser zfUser, HttpServletRequest request, HttpServletResponse response){
        List<ZfUser> list=zfAppointment.selectUser(zfUser.getUser());

        Map<String,Object> map=new HashMap <>();
        if(list.size()==0){
            //代表没有这用户 0代表没有
            map.put("data",0);
        }else {
            if(MD5Util.getMD5(zfUser.getPassword()).equals(list.get(0).getPassword())){
                map.put("data",list);
                Cookie cookie=new Cookie("userId",list.toString());
                cookie.setMaxAge(30*60);
                response.addCookie(cookie);
            }else {
                //密码不正确
                map.put("data",1);
            }
        }
        return  map;

    }


    /**
     * @Author: lizhao
     * @Date: 2017-12-27 10:38
     * @param
     * @Description:注册
     *
     */
    @RequestMapping(value = {"/register"},method = RequestMethod.POST)
    public int register(ZfUser zfUser){
         //查询用户名是否有重复
        int data=0;
        //0代表注册成功 1代表失败
         List<ZfUser> list=zfAppointment.selectUser(zfUser.getUser());
         if(list.size()==0){
             zfUser.setPassword(MD5Util.getMD5(zfUser.getPassword()));
             zfAppointment.insertUser(zfUser);
             return data;
         }else {
             data=1;
             return data;
         }

    }
    
    /**
     * @Author: lizhao
     * @Date: 2017-12-29 8:57
     * @param
     * @Description:读取便民咨询列表的数据
     *
     */
    @RequestMapping(value = {"/bianminzhixun"},method = RequestMethod.GET)
    public  List<ZfReply> bianminzhixun(){
        List<ZfReply> zfReply=zfAppointment.selectReply();
        for (int i = 0; i < zfReply.size(); i++) {
            List<ZfFormBianminzixun> zfFormBianminzixunsList=zfAppointment.selectBianminzixun(zfReply.get(i).getDataid());
            if (zfFormBianminzixunsList.size()==0){
                zfReply.remove(0);
            }else {
                zfReply.get(i).setZfFormBianminzixuns(zfFormBianminzixunsList);
            }

        }

        return  zfReply;
    }

    /**
     * @Author: lizhao
     * @Date: 2017-12-29 10:13
     * @param
     * @Description:添加便民咨询2
     *
     */
    @RequestMapping(value = {"/insertBMZX"},method = RequestMethod.GET)
    public void insertBMZX(ZfFormBianminzixun zfFormBianminzixun){
        zfFormBianminzixun.setDatetime(System.currentTimeMillis());
        zfFormBianminzixun.setUserid(0);
        zfAppointment.insertBMZX(zfFormBianminzixun);

    }

    /**
     * @Author: lizhao
     * @Date: 2017-12-29 11:48
     * @param
     * @Description:查询意见反馈列表
     *
     */
    public List<ZfNews> zfNewsList(){
        return zfAppointment.zfNewsList();
    }



    public   String  getResult(String inputStr)
    {
        System.out.println("=======加密前的数据:"+inputStr);
        BigInteger bigInteger=null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] inputData = inputStr.getBytes();
            md.update(inputData);
            bigInteger = new BigInteger(md.digest());
        } catch (Exception e) {e.printStackTrace();}
        System.out.println("MD5加密后:" + bigInteger.toString(16));
        return bigInteger.toString(16);
    }



}
