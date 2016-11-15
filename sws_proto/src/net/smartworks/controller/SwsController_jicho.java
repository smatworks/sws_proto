/*	
 * file 		 : SwsController.java
 * created by    : kmyu
 * creation-date : 2016. 11. 14.
 */

package net.smartworks.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SwsController_jicho {
//
//	@RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
//    public @ResponseBody Map getCurrentUser(HttpSession session) {
//         
//		Map resultMap = new HashMap();
//		resultMap.put("id", "kimworks@smartworks.net");
//		resultMap.put("picture", "pic");
//		resultMap.put("username", "김웍스");
//		return resultMap;
//    }
//
//	@RequestMapping(value="/getList", method=RequestMethod.POST)
//	public @ResponseBody Map getList(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
//	
//		List<Map<String, String>> filtersList = (List<Map<String, String>>)requestBody.get("filters");
//		
//		Map result = new HashMap();
//		result.put("hello","world");
//		result.put("hi","world");
//		
//		return result;
//		
//	}
//	
//	/* 메모 등록 */
//	@RequestMapping(value="/memoInsert", method=RequestMethod.POST)
//	public @ResponseBody Map memoInsert(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		Map<String, Object> result = (Map<String, Object>) requestBody;
//		return result;
//		
//	}
//	
//	/* todo 등록 */
//	@RequestMapping(value="/todoInsert", method=RequestMethod.POST)
//	public @ResponseBody Map todoInsert(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		Map<String, Object> result = (Map<String, Object>) requestBody;
//		return result;
//		
//	}
//
//	/* 할일 등록 */
//	@RequestMapping(value="/dayworkInsert", method=RequestMethod.POST)
//	public @ResponseBody Map dayworkInsert(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		Map<String, Object> result = (Map<String, Object>) requestBody;
//		return result;
//		
//	}
//
//	/* Memo 가져오기  (ResponseBody 사용) 
//	@RequestMapping(value="/getMemoList", method=RequestMethod.POST)
//	public @ResponseBody List<Map<String, Object>> getMemoList(HttpServletRequest request, HttpServletResponse response) {
//		
//		List<Map<String,Object>> resultData = new ArrayList<Map<String,Object>>();
//		for(int i=0; i<5; i++) {
//			Map<String, Object> data = new HashMap<String,Object>();
//			data.put("title", "this is memo title!! num : " + i);
//			data.put("content", "this is memo content!!! num : " + i);
//			data.put("tag", "#tag #this #is #tag #baby #num : " + i);
//			data.put("date", "2016.11.15 num : " + i);
//			resultData.add(data);
//		}
//		return resultData;
//	} */
//	
//	
//	/* MemoList 가져오기 (ResponseEntity 사용) */
//	@RequestMapping(value="/getMemoList", method=RequestMethod.POST)
//	public ResponseEntity <List<Map<String, Object>>> getMemoList() {
//		
//		ResponseEntity <List<Map<String, Object>>> data = null;
//		
//		try {
//			List<Map<String,Object>> resultData = new ArrayList<Map<String,Object>>();
//			for(int i=0; i<5; i++) {
//				Map<String, Object> result = new HashMap<String,Object>();
//				result.put("title", "this is memo!! num : " + i);
//				result.put("content", "this is content!!! num : " + i);
//				result.put("tag", "#tag #this #is #tag #baby #num : " + i);
//				result.put("date", "2016.11.15 num : " + i);
//				resultData.add(result);
//			}
//			data = new ResponseEntity<>(resultData, HttpStatus.OK);
//		} catch( Exception e ) {
//			data = new ResponseEntity<>(HttpStatus.BAD_REQUEST );
//			System.out.println(e.toString());
//		}
//		return data;
//	}
//	
//	/* TodoList 가져오기 */
//	@RequestMapping(value="/getTodoList", method=RequestMethod.POST)
//	public @ResponseBody List<Map<String, Object>> getTodoList(HttpServletRequest request, HttpServletResponse response) {
//		
//		List<Map<String,Object>> resultData = new ArrayList<Map<String,Object>>();
//		for(int i=0; i<5; i++) {
//			Map<String, Object> data = new HashMap<String,Object>();
//			data.put("title", "this is todoList title !! num : " + i);
//			data.put("content", "this is todoList content!!! num : " + i);
//			data.put("tag", "#tag #this #is #tag #baby #num : " + i);
//			if(i%2 == 0) {
//				data.put("done", "true" + i);
//			} else { 
//				data.put("done", "false" + i); 
//			}
//			resultData.add(data);
//		}
//		return resultData;
//	}
//	
//	/* 할 일 List 가져오기 */
//	@RequestMapping(value="/getDayWorkList", method=RequestMethod.POST)
//	public @ResponseBody List<Map<String, Object>> getDayWorkList(HttpServletRequest request, HttpServletResponse response) {
//		
//		List<Map<String,Object>> resultData = new ArrayList<Map<String,Object>>();
//		for(int i=0; i<5; i++) {
//			Map<String, Object> data = new HashMap<String,Object>();
//			data.put("title", "this is daywork title!! num : " + i);
//			data.put("content", "this is daywork content!!! num : " + i);
//			data.put("tag", "#tag #this #is #tag #baby #num : " + i);
//			data.put("start", "2016.11.0" + i);
//			data.put("finish", "2016.11.2" + i);
//			resultData.add(data);
//		}
//		return resultData;
//	}
//	
//	/* 검색 (ResponseBody 사용) */ 
//	@RequestMapping(value="/search", method=RequestMethod.POST)
//	public @ResponseBody List<String> search(@RequestBody Map<String, String> requestBody) {
//		
//		String keyword = requestBody.get("keyword");
//
//		List<String> list = new ArrayList<String>();
//		if(keyword.equals("smartworks")) {
//			list.add("#smartworks");
//			list.add("#smartworks.net");
//			list.add("#smartworks.net +@ note");
//			list.add("#끄적이");
//		} else {
//			list.add("#hello");
//			list.add("#test");
//			list.add("#God Bless You");
//			list.add("#Good Luck");
//		}
//		return list;
//	} 
//	
//	
//	/* memo 가져오기 */
//	@RequestMapping(value="/getMemo", method=RequestMethod.POST)
//	public @ResponseBody Map<String, Object> getMemoList(HttpServletRequest request, HttpServletResponse response) {
//		
//		Map<String, Object> data = new HashMap<String,Object>();
//		data.put("title", "this is memo title!!");
//		data.put("content", "this is memo content!!!");
//		data.put("tag", "#tag #this #is #tag #baby #num");
//		data.put("date", "2016.11.15");
//	
//		return data;
//	} 
	
	
	
	/* 검색 (ResponseEntity 사용) 미완성...
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public ResponseEntity <List<String>> search(@RequestParam Map<String, String> fromKeyword, HttpServletRequest request,
            HttpServletResponse response) {
		
		ResponseEntity<List<String>> entity = null;
		
		String keyword = fromKeyword.get("key");

		List<String> list = new ArrayList<String>();
		
		try{
			if(keyword.equals("smartworks")) {
				list.add("#smartworks");
				list.add("#smartworks.net");
				list.add("#smartworks.net +@ note");
				list.add("#끄적이");
			} else {
				list.add("#hello");
				list.add("#test");
				list.add("#God Bless You");
				list.add("#Good Luck");
			}
			entity = new ResponseEntity<>(list, HttpStatus.OK);
		} catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			e.toString();
		}
		return entity;
	}*/
	
	/* Memo 등록 
	@RequestMapping(value="/memoInsert", method=RequestMethod.POST)
	public ResponseEntity <Map<String, Object>> memoInsert (@RequestParam Map<String, Object> param) {
		ResponseEntity<Map<String, Object>> entity = null;
		
		entity = (ResponseEntity<Map<String, Object>>) param;

		try {
			entity = new ResponseEntity<>(null, HttpStatus.OK);
		} catch( Exception e ) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST );
			System.out.println(e.toString());
		}
		return entity;
	}
	*/
}

