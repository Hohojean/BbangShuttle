package com.bbang.shuttle.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class NoticeVO {
  private int noticeNo;
  private String noticeTitle;
  private String noticeContent;
  private String regDate;
  private String noticeType;
  private int cnt;
}
