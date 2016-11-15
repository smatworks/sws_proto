package net.smartworks.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.smartworks.model.Task;

public class TaskMapper implements RowMapper<Task> {

	@Override
	public Task mapRow(ResultSet rs, int rowNum) throws SQLException {

		Task task = new Task();
		
		task.setObjId(rs.getString("objId"));
		task.setTitle(rs.getString("title"));
		task.setContents(rs.getString("contents"));
		task.setTags(rs.getString("tags"));
		task.setCreatedUser(rs.getString("createdUser"));
		task.setCreatedDate(rs.getDate("createdDate"));
		
		task.setTaskType(rs.getString("taskType"));
		task.setCompleteYn(rs.getBoolean("taskType"));
		
		task.setStartDate(rs.getDate("startDate"));
		task.setEndDate(rs.getDate("endDate"));

		return task;
	}
	
	
}
