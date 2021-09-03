package com.day.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.day.dto.OrderInfo;
import com.day.dto.OrderLine;
import com.day.exception.AddException;
import com.day.exception.FindException;
@Repository("orderDAO")
public class OrderDAOOracle implements OrderDAO {

	@Autowired
	@Qualifier("Underscore")
	private SqlSessionFactory sessionFactory;


	@Override
	@Transactional(rollbackFor = AddException.class)
	public void insert(OrderInfo info) throws AddException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			insertInfo(session ,info);	//ex주문수량이 아주큰값인경우, 주문기본테이블  데이터 추가성공
			insertLines(session,info.getLines());//		   , 주문상세테이블 데이터 추가실패 ->AddException발생
			//session.commit();								//모두 롤백됨

		}catch(Exception e){
			//try {
			//	session.rollback();
			//} catch(Exception e1) {	
			//}
			throw new AddException(e.getMessage());
		}finally {
			if(session !=null) {
				session.close();
			}
		}
	}
	public void insertInfo(SqlSession session, OrderInfo info) throws AddException {
		try {
			session.insert("com.day.dto.OrderMapper.insertInfo",info);
		}catch(Exception e){
			e.printStackTrace();
			throw new AddException("주문기본 추가실패:" + e.getMessage());		}

	}
	
	public void insertLines(SqlSession session,List<OrderLine> lines) throws AddException {
		try {
			for(OrderLine line:lines) {
				session.insert("com.day.dto.OrderMapper.insertLine",line);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new AddException("주문상세 추가실패:" + e.getMessage());
		}
	}
		
	@Override
	public List<OrderInfo> selectById(String id) throws FindException {
		SqlSession session = null;

		try {
			session = sessionFactory.openSession();
			 List<OrderInfo> list = session.selectList("com.day.dto.OrderMapper.selectById",id);
			 if(list.size()==0) {
				 throw new FindException("주문내역이 없습니다");
			 }
			 return list;
		} catch (Exception e) {
			e.printStackTrace();	
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			}
		}	
	}
//	public static void main(String[] args) {
//		OrderDAOOracle dao = new OrderDAOOracle();		
//		OrderInfo info = new OrderInfo();
//		Customer c = new Customer();  c.setId("id1");
//		info.setOrder_c(c);
//		
//		List<OrderLine> lines = new ArrayList<>();	
//		for(int i=1; i<=3; i++) {
//			OrderLine line = new OrderLine();
//			Product p = new Product();  p.setProd_no("C000" + i);
//			line.setOrder_p(p);
//			line.setOrder_quantity(i*1000);
//			lines.add(line);
//		}
//		info.setLines(lines);
//		
//		try {
//			dao.insert(info);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
//	}
}
