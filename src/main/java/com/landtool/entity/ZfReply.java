package com.landtool.entity;


import java.util.List;

public class ZfReply {

  private int replyid;
  private long formid;
  private int dataid;
  private String reply;
  private String replyby;
  private long replytime;
  private String ip;

  public List <ZfFormBianminzixun> getZfFormBianminzixuns() {
    return zfFormBianminzixuns;
  }

  public void setZfFormBianminzixuns(List <ZfFormBianminzixun> zfFormBianminzixuns) {
    this.zfFormBianminzixuns = zfFormBianminzixuns;
  }

  private List<ZfFormBianminzixun> zfFormBianminzixuns;


  public long getReplyid() {
    return replyid;
  }

  public void setReplyid(int replyid) {
    this.replyid = replyid;
  }


  public long getFormid() {
    return formid;
  }

  public void setFormid(long formid) {
    this.formid = formid;
  }


  public int getDataid() {
    return dataid;
  }

  public void setDataid(int dataid) {
    this.dataid = dataid;
  }


  public String getReply() {
    return reply;
  }

  public void setReply(String reply) {
    this.reply = reply;
  }


  public String getReplyby() {
    return replyby;
  }

  public void setReplyby(String replyby) {
    this.replyby = replyby;
  }


  public long getReplytime() {
    return replytime;
  }

  public void setReplytime(long replytime) {
    this.replytime = replytime;
  }


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

}
