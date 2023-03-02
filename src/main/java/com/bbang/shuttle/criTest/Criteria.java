package com.bbang.shuttle.criTest;

import lombok.Getter;
import lombok.ToString;

//** Criteria : (판단이나 결정을 위한) 기준
//=> 출력할 Row 를 select 하기 위한 클래스
//=> 이것을 위한 기준 값들을 관리

//** PageMaker : UI에 필요한 정보 완성

//** Paging 을 하려면 ... **
//=> 1Page에 출력할 Row 갯수 :  10개
//=> 현재 출력 Page
//=> 출력할 List (Rows) 
//	-> start Row 순서번호 : 계산필요
//=> Criteria Class 에 정의 

//=> 1Page 출력 PageNo 갯수 : 10개
//	-> PagreBlock 의 First Page No 
//	-> PagreBlock 의 Last Page No
//	-> 전진, 후진 표시여부
//	-> go 전체의 First Page 표시여부
//	-> go 전체의 Last Page 표시여부
//=> PageMaker Class 로 처리 

@Getter
@ToString
public class Criteria {
	private int rowsPerPage; // 1Page에 출력할 Row 갯수
	private int	currPage;  // 현재 출력 Page
	private int	sno;                  // start Row 순서번호 : 계산필요
	private int eno; // end RowNo (Oracle만 필요함)
	
	// 1) 필요한 초기값 생성자로 초기화 (Default 생성자)
	public Criteria() {
		this.rowsPerPage=10;
		this.currPage=1;
	}
	// 2) setCurrPage : 요청받은(출력할) PageNo set
	public void setCurrPage(int	currPage) {
		if (currPage>1) this.currPage=currPage;
		else this.currPage=1;
	}
	// 3) setRowsPerPage
	// => 1페이지당 보여줄 Row(Record,튜플) 갯수 확인
	// => 제한조건 점검 ( 50개 까지만 허용)
	// => 당장은 사용하지 않지만 사용가능하도록 작성
	public void setRowsPerPage(int rowsPerPage) {
		if ( rowsPerPage>10 && rowsPerPage<=50 )
			 this.rowsPerPage=rowsPerPage;
		else this.rowsPerPage=10;
	}
	// 4) setSnoEno : sno, eno 계산
	// => currPage, rowsPerPage 를 이용해 계산
	// => Oracle 검색조건 : between(sno, eno) -> sno 부터 eno 까지
	// => MySql 검색조건 :  limit sno, n -> sno 다음 부터 n개
	public void setSnoEno() {
		if ( this.sno<1 ) this.sno=1;
		this.sno=(this.currPage-1)*this.rowsPerPage; //MySql
		// ** Oracle
		//this.sno=(this.currPage-1)*this.rowsPerPage+1 ;
		//this.eno=this.sno+this.rowsPerPage-1 ; 
	}

} //class
