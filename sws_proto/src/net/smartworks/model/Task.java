/*	
 * file 		 : Task.java
 * created by    : kmyu
 * creation-date : 2016. 11. 15.
 */

package net.smartworks.model;

import java.util.Date;

public class Task extends BaseObject {

	private String taskType;
	
	//TODO
	private boolean completeYn;
	
	//EVENT
	private Date startDate;	
	private Date endDate;
	
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public boolean isCompleteYn() {
		return completeYn;
	}
	public void setCompleteYn(boolean completeYn) {
		this.completeYn = completeYn;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}

