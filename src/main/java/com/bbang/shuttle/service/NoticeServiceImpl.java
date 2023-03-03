package com.bbang.shuttle.service;

import com.bbang.shuttle.criTest.Criteria;
import com.bbang.shuttle.criTest.SearchCriteria;
import com.bbang.shuttle.mapperInterface.NoticeMapper;
import com.bbang.shuttle.vo.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class NoticeServiceImpl implements NoticeService {
  //=> 아래 생성문의 "=" 의 역할 (반드시 생성해야함)
  //BoardDAO dao;
  //BoardDAO dao = new BoardDAO(); // -> @Repository ,ㅡ.,service

  // => BoardMapper interface 로 교체,
  //    이 Mapper 를 통해서 BoardMapper.xml 의 SQL 구문 접근
  private final NoticeMapper mapper;

  @Autowired
  public NoticeServiceImpl(NoticeMapper mapper) {
    this.mapper = mapper;
  }

  // ** SearchCriteria PageList
  @Override
  public List<NoticeVO> searchList(SearchCriteria cri) {
    return mapper.searchList(cri);
  }
  @Override
  public int searchTotalCount(SearchCriteria cri) {
    return mapper.searchTotalCount(cri);
  }

  // ** Criteria PageList
  @Override
  public List<NoticeVO> criList(Criteria cri) {
    return mapper.criList(cri);
  }
  @Override
  public int criTotalCount() {
    return mapper.criTotalCount();
  }

  // ** selectList
  @Override
  public List<NoticeVO> selectList() {
    return mapper.selectList();
  }
  // ** selectOne
  @Override
  public NoticeVO selectOne(NoticeVO vo) {
    return mapper.selectOne(vo);
  }
  // ** Insert
  @Override
  public int insert(NoticeVO vo) {
    return mapper.insert(vo);
  }
  // ** Update
  @Override
  public int update(NoticeVO vo) {
    return mapper.update(vo);
  }
  // ** Delete
  @Override
  public int delete(NoticeVO vo) {
    return mapper.delete(vo);
  }
  // ** 조회수 증가
  @Override
  public int countUp(NoticeVO vo) {
    return mapper.countUp(vo);
  }

}
