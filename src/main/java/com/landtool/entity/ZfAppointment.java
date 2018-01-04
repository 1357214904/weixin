package com.landtool.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZfAppointment {

  private long appointmentId;
  private String name;
  private String iphone;
  private String identityCard;
  private String address;
  private java.sql.Timestamp appointmentTime;
  private String Text;

}
