/*	
 * file 		 : SwsController.java
 * created by    : kmyu
 * creation-date : 2016. 11. 14.
 */

package net.smartworks.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SwsController {

	@RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    public @ResponseBody Map getCurrentUser(HttpSession session) {
         
		Map resultMap = new HashMap();
		resultMap.put("id", "kimworks@smartworks.net");
		resultMap.put("picture", "pic");
		resultMap.put("username", "김웍스");
		return resultMap;
    }

	@RequestMapping(value="/getList", method=RequestMethod.POST)
	public @ResponseBody Map getList(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		List<Map<String, String>> filtersList = (List<Map<String, String>>)requestBody.get("filters");
		
		Map result = new HashMap();
		result.put("hello","world");
		result.put("hi","world");
		
		return result;
		
	}
}

