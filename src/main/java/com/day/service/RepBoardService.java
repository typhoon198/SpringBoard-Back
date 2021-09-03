package com.day.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.day.dao.RepBoardDAO;
import com.day.dto.RepBoard;
import com.day.dto.RepBoard2;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;
import com.day.exception.RemoveException;

@Service
public class RepBoardService {
	@Autowired
	private RepBoardDAO dao;
	/**
	 * 글쓰기
	 * @param repBoard
	 * @throws AddException
	 */
	public int write(RepBoard repBoard) throws AddException{
		repBoard.setParentNo(0);
		return dao.insert(repBoard);
	}
	/**
	 * 답글쓰기
	 * @param repBoard
	 * @throws AddException
	 */
	public int reply(RepBoard repBoard) throws AddException{
		if(repBoard.getParentNo() == 0) {
			throw new AddException("부모글 번호가 필요합니다.");
		}
		return dao.insert(repBoard);
	}


	public List<RepBoard> list(String word) throws FindException{
		return dao.selectByWord(word);
	}

	public List<RepBoard> list() throws FindException{
		return dao.selectAll();
	}

	public RepBoard findByNo(int boardNo) throws FindException{
		try {
			dao.plusViewCount(boardNo);
		} catch(ModifyException e){
			e.printStackTrace();
			throw new FindException("조회 수 증가 실패" + e.getMessage());
		}
		return dao.selectByNo(boardNo);
	}
	
	public List<RepBoard> findByParentNo(int boardNo) throws FindException{
		return dao.selectByParentNo(boardNo);
	}
	

	public void modify(RepBoard repBoard) throws ModifyException {
		dao.update(repBoard);
	}
	
	public void remove(RepBoard repBoard) throws RemoveException {
		dao.delete(repBoard);
	}
	
	public int totalCnt() throws FindException {
		return dao.totalCnt();
	}
	
	public int totalCnt(String word) throws FindException {
		return dao.totalCnt(word);
	}
	public List<RepBoard2> list2(int currentPage) throws FindException {
		return dao.selectAllPage(currentPage);
	}
	
	public List<RepBoard2> list2(int currentPage, String word) throws FindException {
		return dao.selectByWordPage(currentPage,word);
	}
	
	
}
