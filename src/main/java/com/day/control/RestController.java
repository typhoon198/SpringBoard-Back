package com.day.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@org.springframework.web.bind.annotation.RestController
//@RequestMapping("/board/*")
public class RestController {
	private Logger log = Logger.getLogger(RestController.class);
	
  //1-1.추가 : 글쓰기 JSON
	//@PostMapping("/board")
	@PostMapping("/write")
	public Map<String,Object> write(@RequestBody   //요청전달 데이터가 appllication/json타입으로 전달됨
						Map<String,String> map) {
		log.error("/board 추가 시작");
		log.error("map : " + map);
		Map<String,Object>result = new HashMap<>();
		result.put("status",1);
		result.put("msg","게시글 추가 성공");
		return result;
	}
/*	
  //1-2.추가  : 답글쓰기 @PathVariable + JSON
	@PostMapping(value = "/board/reply/{no}",
				produces = {MediaType.TEXT_PLAIN_VALUE})//응답형식 한글깨짐해결x
	public ResponseEntity<String> reply(@PathVariable int no,
									@RequestBody Map<String,String> map) {
		//service ~>dao
		//
		ResponseEntity<String> responseEntity = 
					//new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
					new ResponseEntity<>("답글 쓰기 실패",HttpStatus.INTERNAL_SERVER_ERROR);
			log.error(responseEntity.getBody());
		return responseEntity;
	}
	
*/
  //1-2.추가  : 답글쓰기 @PathVariable + JSON
	//@PostMapping(value = "/board/reply/{no}")
	//@PostMapping(value = "/reply/{no}")
	public ResponseEntity<Map<String,Object>> reply(@PathVariable int no,
									@RequestBody Map<String,String> map) {
		//service ~>dao
		//
		Map<String,Object>result = new HashMap<>();
		result.put("msg","답글쓰기 실패");

		ResponseEntity<Map<String,Object>> responseEntity = 
					//new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
					new ResponseEntity<>(result,HttpStatus.OK);
					//응답은 성공은 했으나 DB에 추가하지 못했음
		return responseEntity;
	}

/*	
  //1-2.추가  : 답글쓰기 @PathVariable + JSON
	@PostMapping(value = "/board/reply/{no}",
				produces = {MediaType.TEXT_PLAIN_VALUE})//응답형식 한글깨짐해결x
	public ResponseEntity<String> reply(@PathVariable int no,
									@RequestBody Map<String,String> map) {
		//service ~>dao
		//
		ResponseEntity<String> responseEntity = 
					//new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
					new ResponseEntity<>("답글 쓰기 실패",HttpStatus.INTERNAL_SERVER_ERROR);
			log.error(responseEntity.getBody());
		return responseEntity;
	}
	
*/
/*	
 
 //2.조회 전체 JSON
	@GetMapping("/board")
	public Map<String,Object> map(){
		log.error("/board 검색 시작");
		Map<String,Object> result = new HashMap<>();
		result.put("no",1);
		result.put("title","제목1");
		result.put("content","내용1");
		return result;
	}
*/
  //2.조회(전체,단어검색) @PathVariable, Optional

	//@GetMapping("/board/list")
	//public List<Map<String,Object>> map(){

	//@GetMapping(value={"/board/list","/board/list/{word}"})
	//@GetMapping(value={"/list","/list/{word}"})
	//public List<Map<String,Object>> list(@PathVariable(required= false) String word){
	public List<Map<String,Object>> list(
			//@PathVariable(required= false, name="word")
			@PathVariable(name="word") Optional<String> optWord){
		List<Map<String,Object>> resultlist = new ArrayList<>();
		Map<String,Object> result = new HashMap<>();

		//@PathVariable(required= false)로 했을때의 nullpointExecption 방지  
		//isPresent, get메서드 이용
		String word = null;
		if(optWord.isPresent()) {
			word = optWord.get();
			log.error("/board 단어검색 시작 : word ="+word);
			result.put("no",1);
			result.put("title","제목1");
			result.put("content","내용");
			resultlist.add(result);
			
			result = new HashMap<>();
			result.put("no",2);
			result.put("title","제목");
			result.put("content","내용1");
			resultlist.add(result);
			
		} else{
		log.error("/board 단어검색 전체 검색 시작 ");
		result.put("no",1);
		result.put("title","제목1");
		result.put("content","내용1");
		resultlist.add(result);
		
		result = new HashMap<>();
		result.put("no",2);
		result.put("title","제목2");
		result.put("content","내용2");
		resultlist.add(result);
		
		result = new HashMap<>();
		result.put("no",3);
		result.put("title","제목3");
		result.put("content","내용3");
		resultlist.add(result);
		}
		return resultlist;

	}
		//http://localhost:8888/restspring/board?no=123
		//public void info(@RequestParam(name="no") int board_no){   }
	
  //2.조회(글번호) @PathVariable
		//@GetMapping("/board/{no}")	//http://localhost:8888/restspring/board/123
									//http://localhost:8888/restspring/board/1
		//@GetMapping("/{no}")	//http://localhost:8888/restspring/board/123
		public Map<String, Object> info(@PathVariable int no) {
			Map<String,Object> result = new HashMap<>();
			result.put("no",no);
			result.put("title","제목1");
			result.put("content","내용1");
			return result;
			
		}
		
  //3.수정 @PathVariable + JSON 
		//@PutMapping("/board/{no}")	
		//@PutMapping("/{no}")	
		public ResponseEntity<String> modify(@PathVariable int no,
											@RequestBody Map<String, String> map) {
			//**service->dao
			log.error(map);
			//이제껏 ResponseBody로 map.put("msg","성공") or map.put("status",1")값으로 응답함
			//응답속도를 높이기 위해 ResponseEntity사용
			ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
												        //*이 실패인경우 500번  HttpStatus.INTERNAL_SERVER_ERROR
			return responseEntity;
		}
		
  //4.삭제 @PathVariable
		//@DeleteMapping("/board/{no}")	
		//@DeleteMapping("/{no}")	
		public ResponseEntity<String> modify(@PathVariable int no){			//**service->dao
			ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}		

	}
