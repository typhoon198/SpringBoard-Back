package com.day.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
//java.sql.Date 대신 java.util.Date 
//부모타입 좀더 일반화된 타입을써야 재사용성이 높아짐
public class OrderInfo {
	//DTO의 속성을 어떻게 할지 고민
	//미리 작성한 SQL문의 JOIN문을이용해 빈번히 관계를 보고 
	//N has a 1, 1 has a N 모두가능
	private int order_no;
	private Customer order_c;
	//String order_id 대신 고객객체를 필드값으로
	//주문목록(N) has a 고객(1)
	//주문목록은(N) 고객(1)을 포함한다.(O) 
	//주문목록에서 고객테이블 고객 아이디, 고객 이름,주소를 조회하는 경우
	@JsonFormat(pattern = "yy/MM/dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date order_dt;
	private List<OrderLine> lines;
	//주문목록(1) has a 주문상세(N)

	public OrderInfo() {
		super();
	}

	public OrderInfo(int order_no, Customer order_c, Date order_dt, List<OrderLine> lines) {
		super();
		this.order_no = order_no;
		this.order_c = order_c;
		this.order_dt = order_dt;
		this.lines = lines;
	}

	public OrderInfo(Customer order_c, List<OrderLine> lines) {
		super();
		this.order_c = order_c;
		this.lines = lines;
	}

	public OrderInfo(Customer order_c) {
		super();
		this.order_c = order_c;
	}

	public int getOrder_no() {
		return order_no;
	}

	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}

	public Customer getOrder_c() {
		return order_c;
	}

	public void setOrder_c(Customer order_c) {
		this.order_c = order_c;
	}

	public Date getOrder_dt() {
		return order_dt;
	}

	public void setOrder_dt(Date order_dt) {
		this.order_dt = order_dt;
	}

	public List<OrderLine> getLines() {
		return lines;
	}

	public void setLines(List<OrderLine> lines) {
		this.lines = lines;
	}
	@Override
	public String toString() {
		return "[주문번호=" + order_no + " 주문일시:" + order_dt +
				"\n ===============주문상세=============\n"+lines; 
	}

}

