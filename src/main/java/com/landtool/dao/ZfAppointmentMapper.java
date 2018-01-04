package com.landtool.dao;

import com.landtool.entity.*;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ZfAppointmentMapper {

    @Insert("INSERT INTO zf_appointment (`name`,`iphone`,`identityCard`, `address`,`appointmentTime`,`Text`)" +
            "values(#{name},#{iphone},#{identityCard},#{address},#{appointmentTime},#{Text}) ")
    void addZfAppointment(ZfAppointment zfAppointment);

    @Select("select * from zf_appointment ")
    List<ZfAppointment> selectAll();
    @Select("select count(*) from zf_appointment ")
    int selectALLItem();

    @Select("select * from zf_pollutionfree")
    List<ZfPollutionfree> SelectPollutionfree();

    @Select("select * from zf_changyongdianhua")
    List<ZfChangyongdianhua> SelectChangyongdianhua();

    @Insert("INSERT INTO `zhengfu`.`zf_suggestion` ( `suggestionId`,  `releaseSuggestion`,  `releaseTime`)" +
            "values (#{suggestionId},#{releaseSuggestion},#{releaseTime})  ")
    void suggestion(ZfSuggestion zfSuggestion);

    @Select("select * from zf_suggestion")
    List<ZfSuggestion> selectSuggestion();

    @Select("SELECT * FROM zf_zongheyaowen ORDER BY id DESC")
    List<ZfZongheyaowen> selectZongheyaowen();
    @Select("Select count(*) from zf_zongheyaowen")
    int selectZongheyaowenList();

    @Select("select * from zf_zcfg")
    List<ZfZcfg> selectZcfg();

    @Select("select count(*) from zf_zcfg")
    int selectZcfgSize();

    @Insert("insert into zf_user (userId,user,password)values (#{userId},#{user},#{password})")
    void insertUser(ZfUser zfuser);

    @Select("select * from zf_user where user=#{user}")
    List<ZfUser> selectUser(String user);

    @Select("SELECT * FROM zf_reply ORDER BY replytime ")
    List<ZfReply> selectReply();

    @Select("select * from zf_form_bianminzixun where dataid=#{dataid}")
    List<ZfFormBianminzixun> selectBianminzixun(int dataid);

    @Insert("INSERT INTO `zhengfu`.`zf_form_bianminzixun` (`datetime`, `name`,`email`,`phone`,`title`,`content`) " +
            "VALUES(#{datetime},#{name},#{email},#{phone},#{title},#{content})")
    void insertBMZX(ZfFormBianminzixun zfFormBianminzixun);

    @Select("select * from zf_news where catid = 218")
    List<ZfNews> zfNewsList();

}
