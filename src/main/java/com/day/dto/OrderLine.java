package com.day.dto;
public class OrderLine {
	//DTO의 속성을 어떻게 할지 고민
	//미리 작성한 SQL문의 JOIN문을이용해 빈번히 관계를 보고 
	//N has a 1, 1 has a N 모두가능
	private int order_no;
	//고객,관리자 관점에서만(물류관리자 관점 X)
	//주문상세(N) has a 주문목록(1)   부자연스러움
	//주문목록(1) has a 주문상세(N)   주문목록에서 주문상세를 보는 경우가 많음
	
	private Product order_p;  
	//String order_prod_no 대신 상품 객체로
	//자식 : 주문상세 / 부모 : 상품(주문상세가 상품을 참조하고 있음)
	//주문상세(N) has a 상품(1)  주문상세에서 상품테이블의 상품명, 가격, 제조일자를 조회하는 경우
	//상품(1) has a 주문상세(N)  물류시스템에선 필요, 해당 상품이 어떤주문에서 주문됬는지
	
	private int order_quantity;

	public OrderLine() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderLine(int order_no, Product order_p, int order_quantity) {
		super();
		this.order_no = order_no;
		this.order_p = order_p;
		this.order_quantity = order_quantity;
	}

	public OrderLine(Product order_p, int order_quantity) {
		super();
		this.order_p = order_p;
		this.order_quantity = order_quantity;
	}

	public int getOrder_no() {
		return order_no;
	}

	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}

	public Product getOrder_p() {
		return order_p;
	}

	public void setOrder_p(Product order_p) {
		this.order_p = order_p;
	}

	public int getOrder_quantity() {
		return order_quantity;
	}

	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}


	@Override
	public String toString() {
		return "주문상품번호" + order_p.getProd_no() + "주문상품=" + order_p.getProd_name() + "/상품가격=" + order_p.getProd_price() + 
				"/주문수량=" + order_quantity + "/소계=" + order_p.getProd_price()*order_quantity +"\n";
	}

	
}

		
