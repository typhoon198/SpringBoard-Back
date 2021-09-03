package com.day.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.day.dto.Customer;
import com.day.dto.PageBean;
import com.day.dto.RepBoard;
import com.day.dto.RepBoard2;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;
import com.day.exception.RemoveException;
import com.day.service.RepBoardService;

@RestController
@RequestMapping("/board/*")
public class RepBoardController {
	private Logger log = Logger.getLogger(RepBoardController.class);
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private RepBoardService service;
	
	@PostMapping("/write")
	public Map<String, Object> write(
			HttpSession session,
			@RequestPart MultipartFile imgFile,
			RepBoard repBoard) {
		Map<String, Object> result = new HashMap<>();
		Customer boardC = (Customer)session.getAttribute("loginInfo");
		if(boardC == null) {//로그인이 안되었거나 세션이 끊긴상태
			Map<String, Object> map = new HashMap<>();
			map.put("status", 0);
			result.put("msg","로그인 하세요");
		} else {
			try {
				repBoard.setBoardC(boardC);
				int boardNo = service.write(repBoard);
				result.put("status",1);
				String uploadPath = servletContext.getRealPath("upload");
				if ( ! new File(uploadPath).exists()) {
					log.info("업로드 실제경로생성");
					new File(uploadPath).mkdirs();
				}
				String imgFileName = imgFile.getOriginalFilename();
				if(!"".equals(imgFileName) && imgFile.getSize()!=0){

					String fileExtension = imgFileName.toLowerCase().substring(imgFileName.lastIndexOf(".")+1,imgFileName.length());
					String fileName = String.valueOf(boardNo)+"."+fileExtension;
					File file = new File(uploadPath,fileName); //파일생성
					try {
						FileCopyUtils.copy(imgFile.getBytes(), file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			} catch (AddException e) {
				e.printStackTrace();
				result.put("status",-1);	//실패
				result.put("msg",e.getMessage());
			}
		}
		return result;
	}

	@PostMapping("/reply/{no}")
	public Map<String, Object> reply(
			HttpSession session,
			@PathVariable(name="no") int paretNo,	//부모글번호
			RepBoard repBoard) {
		Map<String, Object> result = new HashMap<>();
		Customer boardC = (Customer)session.getAttribute("loginInfo");
		if(boardC == null) {//로그인이 안되었거나 세션이 끊긴상태
			Map<String, Object> map = new HashMap<>();
			map.put("status", 0);
			result.put("msg","로그인 하세요");
		} else {
			try {
				repBoard.setBoardC(boardC);
				repBoard.setParentNo(paretNo);//write와 다른점
				service.reply(repBoard);
				result.put("status",1);				
			} catch (AddException e) {
				e.printStackTrace();
				result.put("status",-1);	//실패
				result.put("msg",e.getMessage());
			}
		}
		return result;
	}

	
	@GetMapping(value= {"/list","/list/{word}"})
	public Map<String, Object> list(
			@PathVariable(name="word") Optional<String> optWord){
		Map<String, Object> result = new HashMap<String, Object>();
		List<RepBoard> list = new ArrayList<RepBoard>();
		try {
		if(optWord.isPresent()) {
			list = service.list(optWord.get());
		}else {
			list = service.list();
		}
		result.put("status",1);
		result.put("list",list);
		}catch(FindException e) {
			e.printStackTrace();
			result.put("status",0);
			result.put("msg",e.getMessage());
		}
		return result;
		
	}

	@GetMapping(value= {"/list2/{page}","/list2/{word}/{page}"})
	public Map<String, Object> list2(
			@PathVariable(name="word") Optional<String> optWord
			,@PathVariable(name="page") int currentPage){
		HashMap<String, Integer>map = new HashMap<>();
		map.put("currentPage", currentPage);
		map.put("cnt_per_page", PageBean.CNT_PER_PAGE);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int totalCnt = 0;
			List<RepBoard2> list = new ArrayList<RepBoard2>();
			PageBean<RepBoard2> pb =null;
			int totalPage=0; 
			String url=null;
			if(optWord.isPresent()) {
				url="/boardrestspring/list2/"+optWord.get()+"/";//MVC할때 필요할듯?
				list = service.list2(currentPage,optWord.get());
				totalCnt = service.totalCnt(optWord.get());
				totalPage = (int) Math.ceil(totalCnt/(double)PageBean.CNT_PER_PAGE);
				pb = new PageBean<RepBoard2>(currentPage,totalPage ,list, url);

			}else {
				url="/boardrestspring/list2/";
				list = service.list2(currentPage);
				totalCnt = service.totalCnt();
				totalPage = (int) Math.ceil(totalCnt/(double)PageBean.CNT_PER_PAGE);
				pb = new PageBean<RepBoard2>(currentPage,totalPage ,list, url);
			}
			result.put("status",1);
			result.put("pb",pb);
		}catch(FindException e) {
			e.printStackTrace();
			result.put("status",0);
			result.put("msg",e.getMessage());
		}
		return result;

	}
	
	
@GetMapping(value= {"/{no}"})
public Map<String, Object> info(@PathVariable(name="no") int boardNo) {
	Map<String, Object> result = new HashMap<String, Object>();
	try {
		List<RepBoard> list = new ArrayList<RepBoard>();
		RepBoard repBoard = service.findByNo(boardNo);
		
		String uploadPath = servletContext.getRealPath("upload");
		String[] extension = {"jpg","png","gif"};
		Boolean isExist = false;
		for(int i=0;i<extension.length ;i++) {
			File file = new File(uploadPath,boardNo+"."+extension[i]);
			if(file.exists()) {
				result.put("imgFileName",boardNo+"."+extension[i]);
				isExist=true;
			}
		}
		if(!isExist) {
			result.put("imgFileName","0");
		}
			list=service.findByParentNo(boardNo);
			
			if(list.size()==0) {
				result.put("status",1);
			}else {//자식글이 있는경우
				result.put("status",2);
				result.put("list",list);
			}
			result.put("repBoard",repBoard);

		}catch(FindException e){
			e.printStackTrace();
			result.put("status",0);
			result.put("msg",e.getMessage());
		}
		 return result;
	}
	
	@PutMapping("/{no}")	
	public Map<String, Object> modify(
			HttpSession session,
			@PathVariable(name="no") int boardNo,
			@RequestBody RepBoard repBoard){ 
		log.info("내용:"+repBoard.getBoardContent());
		Map<String, Object> result = new HashMap<String, Object>();
		Customer boardC = (Customer)session.getAttribute("loginInfo");
		log.info(boardNo);
		repBoard.setBoardNo(boardNo);
		repBoard.setBoardC(boardC);
		try {
			service.modify(repBoard);
			result.put("status",1);
		}catch(ModifyException e){
			e.printStackTrace();
			result.put("status",0);
			result.put("msg",e.getMessage());
		}
		 return result;
	}

	@DeleteMapping("/{no}")	
	public Map<String, Object> modify(
			HttpSession session,
			@PathVariable(name="no") int boardNo){
		Map<String, Object> result = new HashMap<String, Object>();
		Customer boardC = (Customer)session.getAttribute("loginInfo");
		RepBoard repBoard = new RepBoard();
		repBoard.setBoardC(boardC);
		repBoard.setBoardNo(boardNo);
		
		try {
			service.remove(repBoard);
			result.put("status",1);
		}catch(RemoveException e){
			e.printStackTrace();
			result.put("status",0);
			result.put("msg",e.getMessage());
		}
		 return result;
	}
	
	
}
