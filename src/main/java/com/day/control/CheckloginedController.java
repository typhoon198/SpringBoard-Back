package com.day.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.day.dto.Customer;

@Controller
public class CheckloginedController {
	
	
	@GetMapping("/checklogined")
	@ResponseBody
	public Map<String, Integer> checkLogined(HttpSession session){
		Map<String, Integer>map = new HashMap<>();
		Customer c = (Customer)session.getAttribute("loginInfo");
		int status;
		if(c == null) {
			status = 0;
		}else {
			status = 1;
		}
		map.put("status",status);
		return map;
	}
}
