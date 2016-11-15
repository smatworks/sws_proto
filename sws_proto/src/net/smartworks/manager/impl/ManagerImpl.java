/*	
 * file 		 : ManagerImpl.java
 * created by    : kmyu
 * creation-date : 2016. 11. 15.
 */

package net.smartworks.manager.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.mongodb.util.JSON;

import net.sf.json.JSONObject;
import net.smartworks.dao.mapper.TaskMapper;
import net.smartworks.factory.DaoFactory;
import net.smartworks.manager.IManager;
import net.smartworks.model.TagIndex;
import net.smartworks.model.Task;
import net.smartworks.model.TaskCond;
import net.smartworks.model.TaskHistory;
import net.smartworks.util.id.IDCreator;

public class ManagerImpl implements IManager{

	
	private List<TagIndex> makeTagIndexListByTag(String objId, String tagString) throws Exception {
		
		String[] tags = StringUtils.tokenizeToStringArray(tagString, " ");

		if (tags == null || tags.length == 0)
			return null;
		
		List result = new ArrayList();
		
		for (int i = 0; i < tags.length; i++) {
			
			String tag = tags[i];
			if (tag.indexOf("#") == 0) {
				
				TagIndex tagIndex = new TagIndex();
				tagIndex.setTagName(tag.replace("#", ""));
				tagIndex.setObjIds(objId+";");
				
				result.add(tagIndex);
		
			} else {
				continue;
			}
		}
		return result;
	}
	
	
	@Override
	public Task setTask(String userId, Task task) throws Exception {

		Task returnTask = DaoFactory.getInstance().getSqlDao().setTask(userId, task);
		
		//tag 테그인덱스를 만든다 
		try {
			DaoFactory.getInstance().getNoSqlDao().setTagIndexes(userId, makeTagIndexListByTag(returnTask.getObjId(), returnTask.getTags()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnTask;
	}

	@Override
	public Task getTask(String userId, String objId) throws Exception {

		Task task = DaoFactory.getInstance().getSqlDao().getTask(userId, objId);
		return task;
	}

	@Override
	public Task updateTask(String userId, Task task) throws Exception {
		
		//tag 테그인덱스를 업데이트. 
		Task oldTask = null;
		try {
			oldTask = DaoFactory.getInstance().getSqlDao().getTask(userId, task.getObjId());
			DaoFactory.getInstance().getNoSqlDao().updateTagIndexes(userId, makeTagIndexListByTag(oldTask.getObjId(), oldTask.getTags()) , makeTagIndexListByTag(task.getObjId(), task.getTags()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Task resultTask = DaoFactory.getInstance().getSqlDao().updateTask(userId, task);
		
		try {
			//이력을 저장한다. 
			setTaskUpdateHistory(userId, oldTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultTask;
	}
	
	@Override
	public void removeTask(String userId, String objId) throws Exception {

		Task task = getTask(userId, objId);
		
		//tag 테그인덱스를 삭제한다. 
		try {
			DaoFactory.getInstance().getNoSqlDao().removeTagIndexes(userId, makeTagIndexListByTag(task.getObjId(), task.getTags()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DaoFactory.getInstance().getSqlDao().removeTask(userId, objId);
		
	}

	private void setTaskUpdateHistory(String userId, Task oldTask) throws Exception {
		
		if (oldTask == null)
			return;
		
		String objId = oldTask.getObjId();
		
		TaskHistory history = null;
		try {
			history = DaoFactory.getInstance().getSqlDao().getTaskHistoryByTskObjId(userId, objId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (history == null) {
			
			//TODO 지저분한소스 
			Map oldTaskMap = new HashMap();
			oldTaskMap.put("objId", oldTask.getObjId());
			oldTaskMap.put("title", oldTask.getTitle());
			oldTaskMap.put("contents", oldTask.getContents());
			oldTaskMap.put("tags", oldTask.getTags());
			oldTaskMap.put("createdUser", oldTask.getCreatedUser());
			//oldTaskMap.put("createdDate", oldTask.getCreatedDate());
			oldTaskMap.put("taskType", oldTask.getTaskType());
			//oldTaskMap.put("completeYn", oldTask.isCompleteYn());
			//oldTaskMap.put("startDate", oldTask.getStartDate());
			//oldTaskMap.put("endDate", oldTask.getEndDate());
			
			JSONObject oldTaskJsonO = JSONObject.fromObject(oldTaskMap);
			String oldTaskJson = oldTaskJsonO.toString();

			List newHistoryJsonList = new ArrayList();
			newHistoryJsonList.add(oldTaskJson);
			
			Map resultMap = new HashMap();
			resultMap.put("taskHistory", newHistoryJsonList);
			
			JSONObject jsonO = JSONObject.fromObject(resultMap);
			String result = jsonO.toString();
			
			TaskHistory newHistory = new TaskHistory();
			newHistory.setObjId(IDCreator.createId("TH"));
			newHistory.setTskObjId(objId);
			newHistory.setHistoryJson(result);
			
			DaoFactory.getInstance().getSqlDao().setTaskHistory(userId, newHistory);
			
		} else {
			
			String historyJson = history.getHistoryJson();
			
			Map jsonMap = (Map)JSON.parse(historyJson);
			
			List historyList = (List)jsonMap.get("taskHistory");
			
			//TODO 지저분한소스 
			Map oldTaskMap = new HashMap();
			oldTaskMap.put("objId", oldTask.getObjId());
			oldTaskMap.put("title", oldTask.getTitle());
			oldTaskMap.put("contents", oldTask.getContents());
			oldTaskMap.put("tags", oldTask.getTags());
			oldTaskMap.put("createdUser", oldTask.getCreatedUser());
			//oldTaskMap.put("createdDate", oldTask.getCreatedDate());
			oldTaskMap.put("taskType", oldTask.getTaskType());
			//oldTaskMap.put("completeYn", oldTask.isCompleteYn());
			//oldTaskMap.put("startDate", oldTask.getStartDate());
			//oldTaskMap.put("endDate", oldTask.getEndDate());
			
			JSONObject oldTaskJsonO = JSONObject.fromObject(oldTaskMap);
			String oldTaskJson = oldTaskJsonO.toString();
			
			historyList.add(oldTaskJson);
			jsonMap.put("taskHistory", historyList);
			
			JSONObject newHistoryListJsonO = JSONObject.fromObject(jsonMap);
			String newHistoryJson = newHistoryListJsonO.toString();
			
			history.setHistoryJson(newHistoryJson);
			
			DaoFactory.getInstance().getSqlDao().updateTaskHistory(userId, history);
		}
	}
	
	@Override
	public long getTaskSize(String userId, TaskCond cond) throws Exception {
		long size = DaoFactory.getInstance().getSqlDao().getTaskSize(userId, cond);
		return size;
	}

	@Override
	public List<Task> getTasks(String userId, TaskCond cond) throws Exception {
		List<Task> tasks = DaoFactory.getInstance().getSqlDao().getTasks(userId, cond);
		return tasks;
	}

	@Override
	public List<Task> findTaskByTag(String userId, String tagStr) throws Exception {

		String[] tagArray = StringUtils.tokenizeToStringArray(tagStr, " ");
		
		for (int i = 0; i < tagArray.length; i++) {
			String tag = tagArray[i];
			tag = tag.replace("#", "");
			tagArray[i] = tag;
		}
		
		List<TagIndex> result = DaoFactory.getInstance().getNoSqlDao().getTagIndex(userId, tagArray);

		if (result == null || result.size() == 0)
			return null;
		
		if (result.size() != tagArray.length)
			return null;
		
		
		List resultObjIdList = null;
		
		for (int i = 0; i < result.size(); i++) {
			
			TagIndex tagIndex = result.get(i);
			String tags = tagIndex.getObjIds();
			String[] objIds = StringUtils.tokenizeToStringArray(tags, ";");
			
			List objIdList = Arrays.asList(objIds);
			
			if (resultObjIdList == null) {
				resultObjIdList = objIdList;
			} else {
				List matchList = new ArrayList();
				for (int j = 0; j < objIdList.size(); j++) {
					
					String objId = (String)objIdList.get(j);
					
					if (resultObjIdList.contains(objId)) {
						matchList.add(objId);
					} else {
					}
				}
				resultObjIdList = matchList;
			}
		}
		
		if (resultObjIdList == null || resultObjIdList.size() == 0)
			return null;
		
		List<Task> resultList = DaoFactory.getInstance().getSqlDao().getTasksByObjId(userId, resultObjIdList);
		
		return resultList;
	}
	
//	
}

