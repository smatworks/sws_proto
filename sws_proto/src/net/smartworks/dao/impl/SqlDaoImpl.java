/*	
 * file 		 : DaoImpl.java
 * created by    : kmyu
 * creation-date : 2016. 11. 15.
 */

package net.smartworks.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import net.smartworks.dao.ISqlDao;
import net.smartworks.dao.mapper.TaskHistoryMapper;
import net.smartworks.dao.mapper.TaskMapper;
import net.smartworks.model.InsertKeyWordTask;
import net.smartworks.model.Task;
import net.smartworks.model.TaskCond;
import net.smartworks.model.TaskHistory;
import net.smartworks.util.CommonUtil;

/* Postgresql DAO */
public class SqlDaoImpl implements ISqlDao {
	
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplateObject;
   
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	/* MEMO, TODO, EVENT task 등록 */
	@Override
	public Task setTask(String userId, Task task) throws Exception {
		if (task == null)
			return null;
		
		StringBuffer attrSql = new StringBuffer().append("INSERT INTO task");
		attrSql.append("(OBJID, TITLE, CONTENTS, TAGS, CREATEDUSER, CREATEDDATE, TASKTYPE, COMPLETEYN, STARTDATE, ENDDATE) ");
		attrSql.append(" VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,?)");

	    jdbcTemplateObject.update(attrSql.toString()
					,task.getObjId()
					,task.getTitle()
					,task.getContents()
					,task.getTags()
					,task.getCreatedUser()
					,task.getCreatedDate()
					,task.getTaskType()
					,task.isCompleteYn()
					,task.getStartDate()
					,task.getEndDate()
	    		);
		
	    return task;
	}
	@Override
	public Task updateTask(String userId, Task task) throws Exception {
		if (task == null)
			return null;
		
		
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE TASK SET TITLE=?, CONTENTS=?, TAGS=?, CREATEDUSER=?, CREATEDDATE=?, TASKTYPE=?, COMPLETEYN=? , STARTDATE=? , ENDDATE=? ");
		sql.append(" WHERE OBJID=?");
		
		 jdbcTemplateObject.update(sql.toString()
					,task.getTitle()
					,task.getContents()
					,task.getTags()
					,task.getCreatedUser()
					,task.getCreatedDate()
					,task.getTaskType()
					,task.isCompleteYn()
					,task.getStartDate()
					,task.getEndDate()
					,task.getObjId()
	    		);
		
	    return task;
		
	}
	
	/* MEMO, TODO, EVENT 가져오기 */
	@Override
	public Task getTask(String userId, String objId) throws Exception {
		
		String SQL = "SELECT * FROM TASK WHERE OBJID = ?";
		Task task = jdbcTemplateObject.queryForObject(SQL, new Object[] { objId }, new TaskMapper());	// task에 쿼리문 결과값이 담겨있다.
	    return task;
	    
	}

	@Override
	public void removeTask(String userId, String objId) throws Exception {
		
		StringBuffer attrQuery = new StringBuffer().append("DELETE FROM TASK WHERE OBJID = ? ");
		jdbcTemplateObject.update(attrQuery.toString(), objId);
		
	}

	private List<Object> setTaskQuery(StringBuffer query, TaskCond cond) throws Exception {
		
		//List<Filter> filters = cond.getFilters();
		
		String title = cond.getTitle();
		String createdUser = cond.getCreatedUser();
		
		String taskType = cond.getTaskType();
		String completeYn = cond.getCompleteYn();
		
		Date startDateFrom = cond.getStartDateFrom();
		Date startDateTo = cond.getStartDateTo();
		Date endDateFrom = cond.getEndDateFrom();
		Date endDateTo = cond.getEndDateTo();
		
		
		List<Object> setParams = new ArrayList<Object>();
		
		query.append(" 	FROM  ");
		query.append(" 	TASK ");
		query.append(" 	WHERE 1=1 ");
		
		if (title != null) {
			query.append(" AND TITLE = ? ");
			setParams.add(title);
		}
		if (createdUser != null) {
			query.append(" AND CREATEDUSER = ? ");
			setParams.add(createdUser);
		}
		if (taskType != null) {
			query.append(" AND TASKTYPE = ? ");
			setParams.add(taskType);
		}
		if (completeYn != null) {
			query.append(" AND COMPLETEYN = ? ");
			setParams.add(CommonUtil.toBoolean(completeYn));
		}
		if (startDateFrom != null) {
			query.append(" AND STARTDATE > ? ");
			setParams.add(startDateFrom);
		}
		if (startDateTo != null) {
			query.append(" AND STARTDATE < ? ");
			setParams.add(startDateTo);
		}
		if (endDateFrom != null) {
			query.append(" AND ENDDATE > ? ");
			setParams.add(endDateFrom);
		}
		if (endDateTo != null) {
			query.append(" AND ENDDATE < ? ");
			setParams.add(endDateTo);
		}
		return setParams;
	}
	
	@Override
	public long getTaskSize(String userId, TaskCond cond) throws Exception {
		
		StringBuffer query = new StringBuffer();
		query.append("select count(*) ");
		
		List<Object> setParam = setTaskQuery(query, cond);
		
		Object[] setParamsArray = null;
		if (setParam.size() != 0) {
			setParamsArray = new Object[setParam.size()];
			setParam.toArray(setParamsArray);
		}
		
		Long totalSize = jdbcTemplateObject.queryForObject(query.toString(), setParamsArray ,Long.class);
		
		return totalSize;
	}
	
	@Override
	public List<Task> getTasks(String userId, TaskCond cond) throws Exception {
		
		final int pageNo = cond.getPageNo();
		final int pageSize = cond.getPageSize();
		final int offSet = pageNo == 0 ? 0 : pageNo * pageSize;
		
		StringBuffer query = new StringBuffer();
		query.append(" select * ");
		
		List<Object> setParam = setTaskQuery(query, cond);
		
		
		String orderColumn = cond.getOrderColumn();
		if (orderColumn != null) {
			query.append(" order by ").append(orderColumn);
			if (cond.isDescending())
				query.append(" desc ");
		}
		
		if (pageSize != -1 && pageNo != -1) {
			query.append(" limit ? ");
			query.append(" offset ? ");
			
			setParam.add(pageSize);
			setParam.add(offSet);
		}
		Object[] setParamsArray = null;
		if (setParam.size() != 0) {
			setParamsArray = new Object[setParam.size()];
			setParam.toArray(setParamsArray);
		}
		
		List<Task> attrList = jdbcTemplateObject.query(query.toString(), setParamsArray, new TaskMapper());
		
		return attrList;
	}

	@Override
	public TaskHistory getTaskHistory(String userId, String objId) throws Exception {

		String SQL = "SELECT * FROM TASK_HISTORY WHERE OBJID = ?";
		TaskHistory taskHistory = jdbcTemplateObject.queryForObject(SQL, new Object[] { objId }, new TaskHistoryMapper());
	    return taskHistory;
	}
	@Override
	public TaskHistory getTaskHistoryByTskObjId(String userId, String tskObjId) throws Exception {

		String SQL = "SELECT * FROM TASK_HISTORY WHERE TSKOBJID = ?";
		TaskHistory taskHistory = jdbcTemplateObject.queryForObject(SQL, new Object[] { tskObjId }, new TaskHistoryMapper());
	    return taskHistory;
	}

	@Override
	public TaskHistory setTaskHistory(String userId, TaskHistory taskHistory) throws Exception {

		if (taskHistory == null)
			return null;
		
		StringBuffer attrSql = new StringBuffer().append("INSERT INTO TASK_HISTORY");
		attrSql.append("(OBJID, TSKOBJID, HISTORYJSON) ");
		attrSql.append(" VALUES (? ,? ,?)");

	    jdbcTemplateObject.update(attrSql.toString()
					,taskHistory.getObjId()
					,taskHistory.getTskObjId()
					,taskHistory.getHistoryJson()
	    		);
		
	    return taskHistory;
	}

	@Override
	public TaskHistory updateTaskHistory(String userId, TaskHistory taskHistory) throws Exception {
		if (taskHistory == null)
			return null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE TASK_HISTORY SET TSKOBJID=?, HISTORYJSON=? ");
		sql.append(" WHERE OBJID=?");
		
		 jdbcTemplateObject.update(sql.toString()
					,taskHistory.getTskObjId()
					,taskHistory.getHistoryJson()
					,taskHistory.getObjId()
	    		);
		
	    return taskHistory;
	}

	@Override
	public void removeTaskHistory(String userId, String objId) throws Exception {

		StringBuffer attrQuery = new StringBuffer().append("DELETE FROM TASK_HISTORY WHERE OBJID = ? ");
		jdbcTemplateObject.update(attrQuery.toString(), objId);
		
	}

	@Override
	public void removeTaskHistoryByTskObjId(String userId, String tskObjId) throws Exception {

		StringBuffer attrQuery = new StringBuffer().append("DELETE FROM TASK_HISTORY WHERE TSKOBJID = ? ");
		jdbcTemplateObject.update(attrQuery.toString(), tskObjId);
		
	}

	@Override
	public List<Task> getTasksByObjId(String userId, List objIdList) throws Exception {

		if (objIdList == null || objIdList.size() == 0)
			return null;
		
		StringBuffer query = new StringBuffer();
		query.append(" SELECT * FROM TASK WHERE OBJID IN ( ");
		
		for (int i = 0; i < objIdList.size(); i++) {
			
			String objId = (String)objIdList.get(i);
			if (i == 0) {
				query.append("'");
			} else {
				query.append(",'");
			}
			query.append(objId).append("'");
		}
		query.append(" ) ");
		
		Object[] setParamsArray = null;
		List<Task> attrList = jdbcTemplateObject.query(query.toString(), setParamsArray, new TaskMapper());
		
		return attrList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/* MEMO, TODO, EVENT keyword 등록 (각 키워드 앞글자에 따라 다른 테이블에 insert) */
	@Transactional
	@Override
	public void setKeyword(String objId, List<String> keywords) {

		
	}
	
	
	
	/* 검색한 키워드를 가지고 있는 Task를 가져온다 */
	@Override
	public List<Task> getKeyword(String tags) {
		
		String[] keywords = StringUtils.tokenizeToStringArray(tags, " ");	
		List<Task> taskList = new ArrayList<Task>();																	// 검색한 결과값 (task 값)
		List<String> objIdLists = new ArrayList<String>();																// 결과값을 꺼내기위한(task를 꺼내기위한) objId를 담아둠
		List<Map<String, String>> tableNameAndKeywordList = new ArrayList<Map<String, String>>();
		
		for(int i=0; i<keywords.length; i++) {
			
			char firstChar = keywords[i].charAt(0);																		// 키워드의 첫글자 추출 
			String tableName = "";																						// 검색할 테이블이름 설정 
			
			if( (firstChar >= 'A' && firstChar <= 'Z') || (firstChar >= 'a' && firstChar <= 'z') ) {					// 키워드가 첫글자가 알파벳일 경우 
				tableName = "" + firstChar;
			} else if( (firstChar >= '가' && firstChar <= '힣') || (firstChar >='ㄱ' && firstChar <= 'ㅎ') ) {				// 키워드 앞글자가 한글(자음 + 모음) || 한글(자음) 일 경우
				if( (firstChar >= '가' && firstChar <= '힣') ) {															// 한글이 (자음 + 모음)일 경우 자음만 추출  
					firstChar = InsertKeyWordTask.getChoSung(firstChar);
				}
				tableName = InsertKeyWordTask.getHanguelTableName(firstChar);											// 한글 자음 별로 다른 테이블이름 가져오기 
			} else if( (firstChar >= 'ㅏ' && firstChar <= 'ㅣ') ) {														// 키워드 앞글자가 한글(모음)일 경우 '모음'에 해당되는 테이블 이름 가져오기 
				tableName = InsertKeyWordTask.getHanguelTableName(firstChar);											
			} else if( (firstChar >= '0' && firstChar <= '9') ) {														// 키워드 앞글자가 숫자일 경우 숫자에 해당되는 테이블 이름 가져오기 
				tableName = InsertKeyWordTask.getNumberTableName(firstChar);											
			} else {
				tableName = "specialletter";																			// 특수문자를 담을 테이블이름 
			}
			
			Map<String, String> tableNameAndKeyword = new HashMap<String, String>();									// Map<테이블이름, 키워드> 형식으로 List로 만들어, 나중에 쿼리문을 반복 실행하는것을 방지하기위함. 
			tableNameAndKeyword.put("tableName", tableName);
			tableNameAndKeyword.put("keyword", keywords[i]);
			tableNameAndKeywordList.add(tableNameAndKeyword);
		}
		
		StringBuffer query = new StringBuffer();
		for(int i=0; i<tableNameAndKeywordList.size(); i++) {																// 검색한 키워드를 가지고 있는 task들의 objId 조회 Query문 생성
			
//			테이블 이름 바뀌기 전 
/*			query.append("select objId from ").append(tableNameAndKeywordList.get(i).get("tableName")).append("keyword");
			query.append(" where keyword=").append("'");
			query.append(tableNameAndKeywordList.get(i).get("keyword")).append("'");
*/	
			//테이블 이름 바뀐 후 
			query.append("select objId from keyword");
			query.append(tableNameAndKeywordList.get(i).get("tableName"));
			query.append(" where keyword=").append("'");
			query.append(tableNameAndKeywordList.get(i).get("keyword")).append("'");

			try {
				String objIds = (String) jdbcTemplateObject.queryForObject(query.toString(), String.class);					// 검색한 키워드를 가지고있는 objId들을 가져온다
				String[] objIdList = StringUtils.tokenizeToStringArray(objIds, ";");								
				for(int j=0; j<objIdList.length; j++) {																		// 키워드를 가지고 있는 task들의 objId값을 중복값을 제거하여 담아 놓는다.
					if(!objIdLists.contains(objIdList[j])) {
						objIdLists.add(objIdList[j]);
					}
				}
			} catch(BadSqlGrammarException e) {																				// 테이블이 없을 경우 
				System.out.println(e.toString());
			} catch(EmptyResultDataAccessException empty) {																	// 테이블에 같은 keyword가 없을 경우
				System.out.println(empty.toString());
			} catch(Exception e) {
				System.out.println(e.toString());
			}
			query.setLength(0);
		}
		
		StringBuffer sql = new StringBuffer().append("select * from task where objId in ('");							// 중복 제거된 task들을 꺼내기위한 query문 생성
		for(int k=0; k<objIdLists.size(); k++) {
			sql.append(objIdLists.get(k)).append("'");
			if(k != objIdLists.size()-1) {
				sql.append(", ").append("'");
			} else {
				sql.append(")");
			}
		}
		
		Object[] setParamsArray = null;
		taskList = jdbcTemplateObject.query(sql.toString(), setParamsArray ,new TaskMapper());							// 중복 제거된 task들을 꺼낸다
		return taskList;
	}
}

