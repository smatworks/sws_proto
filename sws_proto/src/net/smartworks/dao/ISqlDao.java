/*	
 * file 		 : IDao.java
 * created by    : kmyu
 * creation-date : 2016. 11. 15.
 */

package net.smartworks.dao;

import java.util.List;

import net.smartworks.model.Task;
import net.smartworks.model.TaskCond;
import net.smartworks.model.TaskHistory;

public interface ISqlDao {

	public Task setTask(String userId, Task task) throws Exception;
	public Task updateTask(String userId, Task task) throws Exception;
	public Task getTask(String userId, String objId) throws Exception;
	public void removeTask(String userId, String objId) throws Exception;
	public long getTaskSize(String userId, TaskCond cond) throws Exception;
	public List<Task> getTasks(String userId, TaskCond cond) throws Exception;
	
	public List<Task> getTasksByObjId(String userId, List objIdList) throws Exception;
	
	
	public TaskHistory getTaskHistory(String userId, String objId) throws Exception;
	public TaskHistory getTaskHistoryByTskObjId(String userId, String tskObjId) throws Exception;
	public TaskHistory setTaskHistory(String userId, TaskHistory taskHistory) throws Exception;
	public TaskHistory updateTaskHistory(String userId, TaskHistory taskHistory) throws Exception;
	public void removeTaskHistory(String userId, String objId) throws Exception;
	public void removeTaskHistoryByTskObjId(String userId, String tskObjId) throws Exception;
}

