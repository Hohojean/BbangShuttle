package com.bbang.shuttle.controller;

import com.bbang.shuttle.criTest.PageMaker;
import com.bbang.shuttle.criTest.SearchCriteria;
import com.bbang.shuttle.service.NoticeService;
import com.bbang.shuttle.service.NoticeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

// 공지사항
// 1. CRUD
// 관리자 - 등록, 삭제, 쓰기 권한.
// 읽기는 모두 가능하게.

// 2. 페이징 기능
// 3. 검색 기능

public class NoticeController {

  @Autowired
  NoticeService service;

  // ** Criteria PageList
  // => ver01 : Criteria cri
  // => ver02 : SearchCriteria cri
  @RequestMapping(value = "/bcrilist", method= RequestMethod.GET )
  public ModelAndView bcrilist(ModelAndView mv, SearchCriteria cri, PageMaker pageMaker) {
    // 1) Criteria 처리
    // => rowsPerPage, currPage 값은 Parameter 로 전달 : 자동으로 set
    // => 그러므로 currPage 를 이용해서 setSnoEno 만 하면됨.
    cri.setSnoEno();

    // ** ver02
    // => SearchCriteria: searchType, keyword 는 Parameter로 전달되어 자동으로 set

    // 2) Service 처리
    //mv.addObject("banana", service.criList(cri));  // ver01
    mv.addObject("banana", service.searchList(cri)); // ver02

    // 3) View 처리 => PageMaker
    // => cri, totalRowsCount (DB에서 읽어와야함)
    pageMaker.setCriteria(cri);
    //pageMaker.setTotalRowsCount(service.criTotalCount());     // ver01: 전체 Rows 갯수
    pageMaker.setTotalRowsCount(service.searchTotalCount(cri)); // ver02: 조건과 일치하는 Rows 갯수
    mv.addObject("pageMaker", pageMaker);

    mv.setViewName("/board/bCriList");
    return mv;
  }//bcrilist

  // ** BoardDetail: /bdetail
  @RequestMapping(value = "/bdetail", method=RequestMethod.GET)
  public ModelAndView bdetail(HttpServletRequest request, ModelAndView mv, BoardVO vo) {
    // ** Detail & 조회수 증가 , Update Form 출력

    // => 조회수 증가 : 테이블의 cnt=cnt+1
    //    - 글보는이(loginID)와 글쓴이가 다를때
    //	  - 글보는이(loginID)가 "admin" 이 아닌경우
    //    - 수정요청이 아닌경우

    vo=service.selectOne(vo);
    if ( vo!=null ) {
      // ** 조회수 증가
      // => 로그인 id 확인
      String loginID = (String)request.getSession().getAttribute("loginID");
      if ( !vo.getId().equals(loginID) &&
          !"admin".equals(loginID) &&
          !"U".equals(request.getParameter("jCode")) ) {
        // => 조회수 증가
        if ( service.countUp(vo)>0 ) vo.setCnt(vo.getCnt()+1);
      } //if_증가조건
    } //조회수 증가

    mv.addObject("apple", vo);
    // ** View 처리
    // => Update 요청이면 bupdateForm.jsp
    String uri = "/board/boardDetail";
    if ( "U".equals(request.getParameter("jCode")) ) {
      uri = "/board/bupdateForm";
    }
    mv.setViewName(uri);
    return mv;
  }//bdetail

  // ** Insert: /binsert
  // => Get: binsertForm
  // => Post : Service 처리
  @RequestMapping(value="/binsert", method=RequestMethod.GET )
  public ModelAndView binsertForm(ModelAndView mv) {
    mv.setViewName("/board/binsertForm");
    return mv;
  } //binsertForm

  @RequestMapping(value="/binsert", method=RequestMethod.POST )
  public ModelAndView binsert(ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
    // ** Service
    // => 성공 : redirect blist
    // => 실패 : binsertForm (재입력 유도)
    String uri="redirect:blist";
    if ( service.insert(vo)>0 ) {
      // => 성공: message 처리
      rttr.addFlashAttribute("message", "~~ 새글 등록 성공 ~~");
    }else {
      // => 실패: message, uri 처리
      mv.addObject("message", "~~ 새글 등록 실패, 다시 하세요 ~~");
      uri="/board/binsertForm" ;
    }
    mv.setViewName(uri);
    return mv;
  } //binsert

  // ** Update: /bupdate
  @RequestMapping(value="/bupdate", method=RequestMethod.POST )
  public ModelAndView bupdate(ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
    // ** Service
    // => 성공 : redirect : bdetail , seq 필요
    // => 실패 : bupdateForm (재수정 유도)
    String uri="redirect:bdetail?seq="+vo.getSeq();

    if ( service.update(vo)>0 ) {
      // => 성공: message 처리
      rttr.addFlashAttribute("message", "~~ 글 수정 성공 ~~");
    }else {
      // => 실패: message, uri 처리
      mv.addObject("apple", vo);
      mv.addObject("message", "~~ 글 수정 실패, 다시 하세요 ~~");
      uri="/board/bupdateForm" ;
    }
    mv.setViewName(uri);
    return mv;
  } //bupdate

  // ** Delete: bdelete
  @RequestMapping(value="/bdelete", method=RequestMethod.GET )
  public ModelAndView bdelete(ModelAndView mv, BoardVO vo, RedirectAttributes rttr) {
    // ** Service
    // => 성공 : redirect blist
    // => 실패 : redirect bdetail (seq가 필요)
    String uri="redirect:blist";
    if ( service.delete(vo)>0 ) {
      // => 성공: message 처리
      rttr.addFlashAttribute("message", "~~ 글 삭제 성공 ~~");
    }else {
      // => 실패: message, redirect bdetail (seq가 필요)
      rttr.addFlashAttribute("message", "~~ 글 삭제 실패 ~~");
      uri="redirect:bdetail?seq="+vo.getSeq();
    }
    mv.setViewName(uri);
    return mv;
  } //bdelete

}
