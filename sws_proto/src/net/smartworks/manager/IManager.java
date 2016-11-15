/*	
 * file 		 : IManager.java
 * created by    : kmyu
 * creation-date : 2016. 11. 15.
 */

package net.smartworks.manager;

import java.util.List;

import net.smartworks.model.Task;
import net.smartworks.model.TaskCond;

public interface IManager {

	//public Task getTasks(String taskType) throws Exception ;
	public Task setTask(String userId, Task task) throws Exception;
	public Task updateTask(String userId, Task task) throws Exception;
	public Task getTask(String userId, String objId) throws Exception;
	public void removeTask(String userId, String objId) throws Exception;
	public long getTaskSize(String userId, TaskCond cond) throws Exception;
	public List<Task> getTasks(String userId, TaskCond cond) throws Exception;
	
	public List<Task> findTaskByTag(String userId, String tagStr) throws Exception;
}

