package com.day.dto;

import java.util.List;

public class PageBean<T> {
	private int currentPage = 1;
	private int totalPage;
	/**
	 * 페이지별 보여줄 목록수
	 */
	public static final int CNT_PER_PAGE = 10;
	private List<T> list; //타입제네릭
	
	/**
	 * 페이지그룹의 페이지수
	 */
	public static final int CNT_PER_PAGE_GROUP = 5;
	private int startPage = 1;
	private int endPage;
	
	private String url;
	

	public PageBean() {}
	public PageBean(int currentPage, int totalPage, List<T> list,String url) {
		this.currentPage=currentPage;
		this.totalPage=totalPage;
		this.list=list;
		this.url = url;
		// 페이지에 따른 데이터의 startRow와 endRow, totalPage, startPage, endPage
		// 시작 줄번호와 끝 줄번호 계산
		// 현재 페이지의 이전 페이지 까지 데이터를 skip시키고 그 다음 번호 시작 번호로 한다.
		//startRow = (page-1)*perPageNum + 1;
		//endRow = startRow + perPageNum -1;	
		// 리스트 화면 하단 부분에 나타내는 페이지를 처리하기 위한 데이터들을 계산
		// 전체 페이지를 계산할 수 있다.
		//totalPage = (totalRow-1)/perPageNum + 1;
		int startPage = (currentPage - 1) / CNT_PER_PAGE_GROUP * CNT_PER_PAGE_GROUP + 1;
		int endPage = startPage + CNT_PER_PAGE_GROUP - 1;
		if(endPage > totalPage) endPage = totalPage;
		this.startPage=startPage;
		this.endPage=endPage;


		//int endPage = (int)(Math.ceil(currentPage/(double)CNT_PER_PAGE_GROUP))*CNT_PER_PAGE_GROUP;	
		//this.startPage = endPage-CNT_PER_PAGE_GROUP+1;
		//this.endPage = (totalPage-startPage<CNT_PER_PAGE_GROUP)? totalPage : endPage; 
		}


	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static int getCntPerPage() {
		return CNT_PER_PAGE;
	}

	public static int getCntPerGroup() {
		return CNT_PER_PAGE_GROUP;
	}

}
