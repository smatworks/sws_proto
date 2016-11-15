/*	
 * file 		 : DaoImpl.java
 * created by    : kmyu
 * creation-date : 2016. 11. 15.
 */

package net.smartworks.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import net.smartworks.dao.ISqlDao;
import net.smartworks.dao.mapper.TaskHistoryMapper;
import net.smartworks.dao.mapper.TaskMapper;
import net.smartworks.model.Task;
import net.smartworks.model.TaskCond;
import net.smartworks.model.TaskHistory;
import net.smartworks.util.CommonUtil;

public class SqlDaoImpl implements ISqlDao {
	
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplateObject;
   
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
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
	
	@Override
	public Task getTask(String userId, String objId) throws Exception {
		
		String SQL = "SELECT * FROM TASK WHERE OBJID = ?";
		Task task = jdbcTemplateObject.queryForObject(SQL, new Object[] { objId }, new TaskMapper());
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
}

