/*	
 * file 		 : Task.java
 * created by    : kmyu
 * creation-date : 2016. 11. 15.
 */

package net.smartworks.model;

import java.util.Date;

public class BaseObjectCond extends Condition {
	
	private String objId;
	private String title;
	private String contents;
	private String tags;
	private String createdUser;
	private Date createdDate;
	
	
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}

