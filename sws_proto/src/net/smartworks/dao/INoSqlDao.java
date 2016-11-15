/*	
 * file 		 : INoSqlDao.java
 * created by    : kmyu
 * creation-date : 2016. 11. 15.
 */

package net.smartworks.dao;

import java.util.List;

import net.smartworks.model.TagIndex;

public interface INoSqlDao {
	
	public List<TagIndex> getTagIndex(String userId, String[] tags) throws Exception;
	
	public void setTagIndexes(String userId, List<TagIndex> tags) throws Exception;
	public void removeTagIndexes(String userId, List<TagIndex> tags) throws Exception;
	public void updateTagIndexes(String userId, List<TagIndex> oldTags, List<TagIndex> newTags) throws Exception;
	
}

