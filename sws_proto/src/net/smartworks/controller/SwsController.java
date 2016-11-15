/*	
 * file 		 : SwsController.java
 * created by    : kmyu
 * creation-date : 2016. 11. 14.
 */

package net.smartworks.controller;

import java.util.ArrayList;
import java.util.Date;
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

import net.smartworks.factory.DaoFactory;
import net.smartworks.factory.ManagerFactory;
import net.smartworks.model.TagIndex;
import net.smartworks.model.Task;
import net.smartworks.model.TaskCond;
import net.smartworks.util.id.IDCreator;

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

	@RequestMapping(value="/setTask", method=RequestMethod.POST)
	public @ResponseBody Map setTask(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		Task task = new Task();

		task.setObjId(IDCreator.createId("TSK"));
		task.setCompleteYn(false);
		task.setContents("Contents");
		task.setCreatedDate(new Date());
		task.setCreatedUser("kimworks");
		task.setEndDate(new Date());
		task.setStartDate(new Date());
		task.setTags("#tag #hello #world #km");
		task.setTitle("title");
		
		Task resultTask = ManagerFactory.getInstance().getManager().setTask("kimworks", task);
		
		Map result = new HashMap();
		result.put("result", resultTask);
		
		return result;
		
	}
	@RequestMapping(value="/getTask", method=RequestMethod.POST)
	public @ResponseBody Map getTask(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		String objId = (String)requestBody.get("objId");
		
		Task resultTask = ManagerFactory.getInstance().getManager().getTask("kimworks", objId);
		
		Map result = new HashMap();
		result.put("result", resultTask);
		
		return result;
		
	}
	@RequestMapping(value="/getTaskSize", method=RequestMethod.POST)
	public @ResponseBody Map getTaskSize(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		TaskCond cond = new TaskCond();
		long size = ManagerFactory.getInstance().getManager().getTaskSize("kimworks", cond);
		
		Map result = new HashMap();
		result.put("size", size);
		
		return result;
		
	}

	@RequestMapping(value="/removeTask", method=RequestMethod.POST)
	public @ResponseBody Map removeTask(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		String objId = (String)requestBody.get("objId");
		
		ManagerFactory.getInstance().getManager().removeTask("kimworks", objId);
		
		Map result = new HashMap();
		result.put("result", true);
		
		return result;
		
	}

	@RequestMapping(value="/updateTask", method=RequestMethod.POST)
	public @ResponseBody Map updateTask(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		String objId = (String)requestBody.get("objId");
		
		Task task = ManagerFactory.getInstance().getManager().getTask("", objId);
		
		String tags = task.getTags();
		
		String newTags = tags.replace("#tag", "");
		newTags = newTags.replace("#world", "");
		
		task.setTags(newTags);
		
		ManagerFactory.getInstance().getManager().updateTask("kimworks", task);
		
		Map result = new HashMap();
		result.put("result", true);
		
		return result;
		
	}
	
	@RequestMapping(value="/findTaskByTag", method=RequestMethod.POST)
	public @ResponseBody Map findTaskByTag(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		String tags = (String)requestBody.get("tags");
		
		List<Task> tasks = ManagerFactory.getInstance().getManager().findTaskByTag("", tags);
		
		Map result = new HashMap();
		result.put("result", tasks);
		
		return result;
		
	}
	

	@RequestMapping(value="/setTagIndexs", method=RequestMethod.POST)
	public @ResponseBody Map setTagIndexs(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
    	TagIndex tagIndex1 = new TagIndex();
    	tagIndex1.setTagName("tagName1!!!");
    	tagIndex1.setObjIds("objIds11!!!");
    	
    	TagIndex tagIndex2 = new TagIndex();
    	tagIndex2.setTagName("tagName2!!!");
    	tagIndex2.setObjIds("objIds22!!!");
    	
    	TagIndex tagIndex3 = new TagIndex();
    	tagIndex3.setTagName("tagName3!!!");
    	tagIndex3.setObjIds("objIds33!!!");
    	
    	TagIndex tagIndex4 = new TagIndex();
    	tagIndex4.setTagName("tagName4!!!");
    	tagIndex4.setObjIds("objIds44!!!");
    	
    	TagIndex tagIndex5 = new TagIndex();
    	tagIndex5.setTagName("tagName5!!!");
    	tagIndex5.setObjIds("objIds55!!!");
    	

    	List<TagIndex> tagList = new ArrayList<TagIndex>();
    	tagList.add(tagIndex1);
    	tagList.add(tagIndex2);
    	tagList.add(tagIndex3);
    	tagList.add(tagIndex4);
    	tagList.add(tagIndex5);
    	
		DaoFactory.getInstance().getNoSqlDao().setTagIndexes("", tagList);

		System.out.println("setTagIndexs DONE!!");
		
		Map result = new HashMap();
		result.put("result", true);
		return result;
		
	}
	
	
}

