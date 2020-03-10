package com.itsmartkit.mybatis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class Student implements Serializable {

  private Long id;

  private String name;

  private Byte age;

  private String tel;

  private String address;

  private String course;

  private Integer score;

  private Date createTime;

}
