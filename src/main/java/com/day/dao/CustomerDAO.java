package com.day.dao;

import com.day.dto.Customer;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;

public interface CustomerDAO {

	/**
	 * 고객 가입
	 * @param  고객이 입력한 고객정보
	 * @throws AddException 고객 추가실패시 발생한다
	 */
	public void insert(Customer c) throws AddException;
	
	/**
	 * 로그인 및 조회
	 * @param  id 아이디
	 * @return id DB에서 고객정보를 불러온다.(로그인은 비번도확인)
	 * @throws FindException 입력한 아이디가 없을때 발생한다.
	 */
	public Customer selectById(String id) throws FindException;
	
	/**
	 * 고객정보 수정
	 * @param  수정할 고객정보
	 * @throws ModifyException 수정이 실패하면 발생한다.
	 */
	public void update(Customer c) throws ModifyException;

	
}
