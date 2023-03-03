package com.bbang.shuttle.service;

import com.bbang.shuttle.criTest.Criteria;
import com.bbang.shuttle.criTest.SearchCriteria;
import com.bbang.shuttle.vo.NoticeVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface NoticeService {

  // ** SearchCriteria PageList
  List<NoticeVO> searchList(SearchCriteria cri);
  int searchTotalCount(SearchCriteria cri);

  // ** Criteria PageList
  List<NoticeVO> criList(Criteria cri);
  int criTotalCount();

  // ** selectList
  List<NoticeVO> selectList();

  // ** selectOne
  NoticeVO selectOne(NoticeVO vo);

  // ** Insert
  int insert(NoticeVO vo);

  // ** Update
  int update(NoticeVO vo);

  // ** Delete
  int delete(NoticeVO vo);

  // ** 조회수 증가
  int countUp(NoticeVO vo);


}
