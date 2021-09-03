package com.day.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RepBoard2 {
	private int rn;
	private int lvl;
	private int boardNo;//자바는 camelCase
	private int parentNo;
	private Customer boardC;
	private String boardTitle;
	private String boardContent;
	private int boardViewcount;
	@JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
	private Date boardDt;
	//Lombok 라이브러리를 사용하면 생성자, setter/getter메서드 제공
	//양방향 has a관계 오류 발생 가능성 - 권장x
	public RepBoard2() {
		super();
	}
	@Override
	public String toString() {
		return "RepBoard2 [rn=" + rn + ", lvl=" + lvl + ", boardNo=" + boardNo + ", parentNo=" + parentNo + ", boardC="
				+ boardC + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent + ", boardViewcount="
				+ boardViewcount + ", boardDt=" + boardDt + "]";
	}
	
	
	public RepBoard2(int rn, int lvl, int boardNo, int parentNo, Customer boardC, String boardTitle,
			String boardContent, int boardViewcount, Date boardDt) {
		super();
		this.rn = rn;
		this.lvl = lvl;
		this.boardNo = boardNo;
		this.parentNo = parentNo;
		this.boardC = boardC;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardViewcount = boardViewcount;
		this.boardDt = boardDt;
	}
	public RepBoard2(int lvl, int boardNo, int parentNo, Customer boardC, String boardTitle, String boardContent,
			int boardViewcount, Date boardDt) {
		super();
		this.lvl = lvl;
		this.boardNo = boardNo;
		this.parentNo = parentNo;
		this.boardC = boardC;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardViewcount = boardViewcount;
		this.boardDt = boardDt;
	}
	//글쓰기용 생성자
	public RepBoard2(Customer boardC, String boardTitle, String boardContent) {
		super();
		this.boardC = boardC;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
	}
	
	//답글쓰기용 생성자
	public RepBoard2(int parentNo, Customer boardC, String boardTitle, String boardContent) {
		super();
		this.parentNo = parentNo;
		this.boardC = boardC;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
	}
	
	
	public RepBoard2(int boardNo, int parentNo, Customer boardC, String boardTitle, String boardContent,
			int boardViewcount, Date boardDt) {
		super();
		this.boardNo = boardNo;
		this.parentNo = parentNo;
		this.boardC = boardC;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardViewcount = boardViewcount;
		this.boardDt = boardDt;
	}
	public int getRn() {
		return rn;
	}
	public void setRn(int rn) {
		this.rn = rn;
	}
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public int getParentNo() {
		return parentNo;
	}
	public void setParentNo(int parentNo) {
		this.parentNo = parentNo;
	}
	public Customer getBoardC() {
		return boardC;
	}
	public void setBoardC(Customer boardC) {
		this.boardC = boardC;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public int getBoardViewcount() {
		return boardViewcount;
	}
	public void setBoardViewcount(int boardViewcount) {
		this.boardViewcount = boardViewcount;
	}
	public Date getBoardDt() {
		return boardDt;
	}
	public void setBoardDt(Date boardDt) {
		this.boardDt = boardDt;
	}
	


}
