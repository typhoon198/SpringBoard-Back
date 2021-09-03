package com.day.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class DownloadController{
	private Logger log = Logger.getLogger(DownloadController.class);
	@Autowired
	private ServletContext servletContext;

	@GetMapping("/download")
	@ResponseBody
	public ResponseEntity<Resource> down(String name) throws UnsupportedEncodingException, FileNotFoundException{
		log.info("다운로드요청");
		log.info(name);
		//HttpHeaders : 요청/응답헤더용 API
		HttpHeaders headers = new HttpHeaders();		
		//응답형식 : application/octet-stream(무조건다운로드)
		headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream;charset=UTF-8");
		//다운로드시 파일이름 결정
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
		
		//Resource : 자원(파일, URL)용 API
		FileInputStream fis = null;
		//다운로드할 파일의 실제 경로 얻기
		String path = servletContext.getRealPath("upload");	
		log.info(path);
		log.info(name);
		File f = new File(path, name);		
		//서블릿에서는 파일을 읽고 쓰고(FileInputStream,ServletOutputStream sos = response.getOutputStream())
		Resource resource = new FileSystemResource(f);
		ResponseEntity<Resource> responseEntity  =  
				new ResponseEntity<>(resource, headers, HttpStatus.OK);
		return responseEntity;
	}
}
