package com.bbang.shuttle.vo;

import lombok.Data;

@Data
public class NoticeVO {
  private int noticeNo;
  private String noticeTitle;
  private String noticeContent;
  private String regDate;
  private String noticeType;
  private int cnt;
}
