/*	
 * file 		 : TaskHistory.java
 * created by    : kmyu
 * creation-date : 2016. 11. 15.
 */

package net.smartworks.model;

public class TaskHistory {
	
	private String objId;
	private String tskObjId;
	private String historyJson;
	
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getTskObjId() {
		return tskObjId;
	}
	public void setTskObjId(String tskObjId) {
		this.tskObjId = tskObjId;
	}
	public String getHistoryJson() {
		return historyJson;
	}
	public void setHistoryJson(String historyJson) {
		this.historyJson = historyJson;
	}
	
}

