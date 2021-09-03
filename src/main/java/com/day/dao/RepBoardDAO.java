package com.day.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.day.dto.RepBoard;
import com.day.dto.RepBoard2;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;
import com.day.exception.RemoveException;

public interface RepBoardDAO {
	/**
	 * 게시글 추가
	 * @param repBoard
	 * @return 추가한 게시글번호
	 * @throws AddException
	 */
	int insert(RepBoard repBoard) throws AddException;
	/**
	 * 글번호로 게시글 검색
	 * @param boardNo
	 * @return
	 * @throws FindException
	 */
	RepBoard selectByNo(int boardNo) throws FindException;
	
	/**
	 * 부모글번호로 자식게시글 검색
	 * @param boardNo
	 * @return
	 * @throws FindException
	 */
	List<RepBoard> selectByParentNo(int boardNo) throws FindException;

	/**
	 * 게시글 조회수 1증가
	 * @param boardNo
	 * @throws ModifyException
	 */
	void plusViewCount(int boardNo) throws ModifyException;

	/**
	 * 게시글 전체 검색
	 * @return
	 * @throws FindException
	 */
	List<RepBoard> selectAll() throws FindException;
	/**
	 * 제목이나 글내용에 단어를 포함한 게시글 검색
	 * @param word
	 * @return
	 * @throws FindException
	 */
	List<RepBoard> selectByWord(String word) throws FindException;
	
	/**
	 * 글내용 수정,글번호는 수정안됨
	 * @param repBoard
	 * @throws ModifyException
	 */
	void update(RepBoard repBoard) throws ModifyException;
	/**
	 * 글삭제
	 * @param repBoard
	 * @throws RemoveException
	 */
	void delete(RepBoard repBoard) throws RemoveException;

	/**
	 * 답글 모두 삭제
	 * @param boardNo 글번호
	 * @throws RemoveException
	 */
	void deleteReply(SqlSession session, int boardNo) throws RemoveException;
	
	/**
	 * 글 삭제
	 * @param 게시글 객체, 게시글 번호와 게시글 작성자를 포함한다.
	 * @throws RemoveException
	 */
	void deleteWrite(SqlSession session, RepBoard repBoard) throws RemoveException;
	
	/**
	 * 페이징 처리를 위한 전체 게시글번호
	 * @return
	 * @throws FindException
	 */
	int totalCnt() throws FindException;
	
	/**
	 * 페이징 처리를 위한 전체 게시글번호
	 * @return
	 * @throws FindException
	 */
	int totalCnt(String word) throws FindException;
	/**
	 * 
	 * @return
	 * @throws FindException
	 */
	List<RepBoard2> selectAllPage(int currentPage) throws FindException;
	
	/**
	 * 
	 * @return
	 * @throws FindException
	 */
	List<RepBoard2> selectByWordPage(int currentPage,String word) throws FindException;


}
