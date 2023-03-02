package com.bbang.shuttle.criTest;

import lombok.Data;

@Data
public class SearchCriteria extends Criteria {
	
	private String searchType;
	private String keyword;
	private String[] check;

} //class
