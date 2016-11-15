/*	
 * file 		 : TagIndex.java
 * created by    : kmyu
 * creation-date : 2016. 11. 15.
 */

package net.smartworks.model;

import org.springframework.data.annotation.Id;

public class TagIndex {
	
	//mongodb vo
	@Id
	private String id;
	
	private String tagName;
	private String objIds;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getObjIds() {
		return objIds;
	}
	public void setObjIds(String objIds) {
		this.objIds = objIds;
	}
	
	public void addTagObjId(String objId) {
		
		if (this.objIds != null) {
			if (this.objIds.indexOf(objId) == -1) {
				this.objIds = this.objIds  + objId;
			}
		} else {
			this.objIds = objId;
		}
		
	}
	
}

