package com.day.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.day.dto.PageBean;
import com.day.dto.Product;
import com.day.dto.RepBoard;
import com.day.dto.RepBoard2;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;
import com.day.exception.RemoveException;
@Repository
public class RepBoardDAOOracle implements RepBoardDAO {

	@Autowired
	@Qualifier("UnderscoreToCamelCase")
	private SqlSessionFactory sessionFactory;

	@Override
	public int insert(RepBoard repBoard) throws AddException {
		SqlSession session= null;
		try {
			session = sessionFactory.openSession();
			session.insert("com.day.dto.RepBoardMapper.insert",repBoard);
			return repBoard.getBoardNo();
		}catch (Exception e) {
			throw new AddException(e.getMessage());
		}finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public RepBoard selectByNo(int boardNo) throws FindException {
		SqlSession session= null;
		try {
			session = sessionFactory.openSession();
			
			RepBoard repBoard = session.selectOne("com.day.dto.RepBoardMapper.selectByNo" , boardNo);
		    if(repBoard == null) {
		    	throw new FindException("게시글이 없습니다.");
		    }
		    return repBoard;
		
		}catch (Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) session.close();
		}
	}		

	@Override
	public List<RepBoard> selectAll() throws FindException {
		SqlSession session= null;
		try {
			session = sessionFactory.openSession();
			return session.selectList("com.day.dto.RepBoardMapper.selectAll");

		}catch (Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public List<RepBoard> selectByWord(String word) throws FindException {
		SqlSession session= null;
		try {
			session = sessionFactory.openSession();
			return session.selectList("com.day.dto.RepBoardMapper.selectByWord",word);

		}catch (Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public void plusViewCount(int boardNo) throws ModifyException {
		SqlSession session= null;
		try {
			session = sessionFactory.openSession();
			int rowCnt = session.update("com.day.dto.RepBoardMapper.plusViewCount",boardNo);
			if(rowCnt==0) {
				throw new ModifyException("글번호가 없습니다.");
			}
		}catch (Exception e) {
			throw new ModifyException(e.getMessage());
		}finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public void update(RepBoard repBoard) throws ModifyException {
		SqlSession session= null;
	System.out.println(repBoard);
		try {
			session = sessionFactory.openSession();
			int rowCnt = session.update("com.day.dto.RepBoardMapper.update",repBoard);
			if(rowCnt==0) {
				throw new ModifyException("로그인하세요");
			}
		}catch (Exception e) {
			throw new ModifyException(e.getMessage());
		}finally {
			if(session!=null) session.close();
		}
	}		
	@Override
	@Transactional(rollbackFor =  RemoveException.class)
	public void delete(RepBoard repBoard) throws RemoveException {

		SqlSession session= null;
		try {
			session = sessionFactory.openSession();
			deleteReply(session, repBoard.getBoardNo());
			deleteWrite(session, repBoard);

		}catch (Exception e) {
			throw new RemoveException(e.getMessage());
		}finally {
			if(session!=null) session.close();
		}
	}
	@Override
	public void deleteReply(SqlSession session, int boardNo) throws RemoveException {
		try {
			session.delete("com.day.dto.RepBoardMapper.deleteReply",boardNo);
		}catch (Exception e) {
			throw new RemoveException("답글 삭제 실패");
		}
	}


	@Override
	public void deleteWrite(SqlSession session, RepBoard repBoard) throws RemoveException {
		try {
			int rowCnt =session.delete("com.day.dto.RepBoardMapper.deleteWrite",repBoard);
			if(rowCnt==0) {
				throw new RemoveException("글 삭제 실패 : 작성자 아이디가 다르거나 글번호가 없습니다.");
			}
		}catch (Exception e) {
			throw new RemoveException(e.getMessage());
		}
	}

	@Override
	public List<RepBoard> selectByParentNo(int boardNo) throws FindException {
		SqlSession session= null;
		try {
			session = sessionFactory.openSession();
			return session.selectList("com.day.dto.RepBoardMapper.selectByParentNo",boardNo);
		}catch (Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public int totalCnt() throws FindException {//동적SQL사용할것!
		SqlSession session= null;
		try {
			session = sessionFactory.openSession();
			int total = session.selectOne("com.day.dto.RepBoardMapper.total");
		    if(total == 0) {
		    	throw new FindException("게시글이 없습니다.");
		    }
		    return total;
		
		}catch (Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) session.close();
		}
	}
	
	@Override
	public int totalCnt(String word) throws FindException {//동적SQL사용할것!
		SqlSession session= null;
		try {
			session = sessionFactory.openSession();
			int total = session.selectOne("com.day.dto.RepBoardMapper.totalWord",word);
		    if(total == 0) {
		    	throw new FindException("게시글이 없습니다.");
		    }
		    return total;
		
		}catch (Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public List<RepBoard2> selectAllPage(int currentPage) throws FindException {
		SqlSession session= null;
		try {
			session = sessionFactory.openSession();
			HashMap<String, Integer>map = new HashMap<>();
			map.put("currentPage", currentPage);
			map.put("cnt_per_page", PageBean.getCntPerPage());
			List<RepBoard2> list = session.selectList("com.day.dto.RepBoardMapper.selectAllPage",map);
			if(list.size() == 0) {
				throw new FindException("게시글이 없습니다.");
			}
			return list;
		}catch (Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) session.close();
		}	
	}

	@Override
	public List<RepBoard2> selectByWordPage(int currentPage, String word) throws FindException {
		SqlSession session= null;
		try {
			session = sessionFactory.openSession();
			HashMap<String, Object>map = new HashMap<>();
			map.put("word", word);
			map.put("currentPage", currentPage);
			map.put("cnt_per_page", PageBean.getCntPerPage());
			List<RepBoard2> list = session.selectList("com.day.dto.RepBoardMapper.selectByWordPage",map);
			if(list.size() == 0) {
				throw new FindException("게시글이 없습니다.");
			}
			return list;
		}catch (Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) session.close();
		}	
	}	
}
