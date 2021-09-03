package com.day.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.day.dto.Customer;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.service.CustomerService;

@Controller
@ResponseBody
public class CustomerController {

	private Logger log = Logger.getLogger(CustomerController.class);

	@Autowired
	public CustomerService service;

	@PostMapping("/login")
	public Map<String, Object> login(String id, String pwd, HttpSession session){
		session.removeAttribute("loginInfo");
		Map<String, Object>map = new HashMap<>();
		try {
			Customer loginInfo = service.login(id, pwd);
			//로그인정보를 세션에 추가			
			session.setAttribute("loginInfo", loginInfo);			
			map.put("status", 1);
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}
	@GetMapping("/logout")
	public void logout(HttpSession session){
		session.invalidate(); //세션제거

	}
	/*	
	@GetMapping("/iddupchk")
	public int idDupChk(String id){
		int status= 0;
		try {
			Customer c = service.findById(id);
			if(c==null) {
				status = 1;  //해당아이디 존재x : 아이디 사용가능한경우
			}//else {
			 //	status = 0;  //해당아이디 존재o : 아이디 사용불가
			//}
		}catch(FindException e){
			e.printStackTrace();
			status = -1;	//기타 예외
		}
		return status;
	}
	 */
	@GetMapping("/iddupchk")
	public Map<String,Integer> idDupChk(String id){
		Map<String, Integer>map = new HashMap<>();
		try {
			Customer c = service.findById(id);
			map.put("status", -1);
		}catch(FindException e){
			map.put("status",1);
		}
		return map;
	}


	@PostMapping("/signup")
	public Map<String,Object> signUp(Customer c, HttpSession session){
		Map<String, Object>map = new HashMap<>();

		try {
			service.signup(c);
			map.put("status",1);
		}catch(AddException e){
			e.printStackTrace();
			map.put("status",-1);
			map.put("msg",e.getStackTrace());
		}
		return map;
	}

}
