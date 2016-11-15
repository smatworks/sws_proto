package net.smartworks.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.smartworks.model.Task;
import net.smartworks.model.TaskHistory;

public class TaskHistoryMapper implements RowMapper<TaskHistory> {

	@Override
	public TaskHistory mapRow(ResultSet rs, int rowNum) throws SQLException {

		TaskHistory taskHistory = new TaskHistory();
		
		taskHistory.setObjId(rs.getString("objId"));
		taskHistory.setTskObjId(rs.getString("tskObjId"));
		taskHistory.setHistoryJson(rs.getString("historyjson"));

		return taskHistory;
	}
	
	
}
