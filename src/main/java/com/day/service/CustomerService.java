package com.day.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.day.dao.CustomerDAO;
import com.day.dto.Customer;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;
@Service
public class CustomerService {
	@Autowired
	private CustomerDAO dao;
	
	/**
	 * 
	 * @param c
	 * @throws AddException
	 */
	public void signup(Customer c) throws AddException{
			dao.insert(c);
	}

	public Customer login(String id, String pwd) throws FindException{
		Customer c = dao.selectById(id);;
			if(!c.getPwd().equals(pwd)) {
				throw new FindException("로그인실패");
			}
			return c;
	}

	public Customer detail(String id) throws FindException{
			return dao.selectById(id);
	}
	/**
	 * 
	 * @param c
	 * @throws ModifyException
	 */
	public void modify(Customer c) throws ModifyException{
		if(c.getEnabled() == 0) { //
			throw new ModifyException("탈퇴작업은 할 수 없습니다");
		}
		c.setEnabled(-1); //강사님 정보수정시 Enabled변경 no
		dao.update(c);
	}
	
	/**
	 * 
	 * @param c
	 * @throws ModifyException
	 */
	public void leave(Customer c) throws ModifyException{
		c.setEnabled(0);
		dao.update(c);
	}

	/**
	 * 아이디에 해당하는 고객객체 찾는다.
	 * @param id 아이디
	 * @return 고객객체
	 * @throws FindException 아이디에 해당고객이 없거나 Network문제가 나면 예외가 발생된다.
	 */
	public Customer findById(String id) throws FindException{
		return dao.selectById(id);
	}
	
}
