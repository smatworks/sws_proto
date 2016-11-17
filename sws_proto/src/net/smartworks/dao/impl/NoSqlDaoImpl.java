/*	
 * file 		 : NoSqlDaoImpl.java
 * created by    : kmyu
 * creation-date : 2016. 11. 15.
 */

package net.smartworks.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import net.smartworks.dao.INoSqlDao;
import net.smartworks.model.TagIndex;

/* MongoDB DAO */
public class NoSqlDaoImpl implements INoSqlDao {

	public static final String TAG_METHOD_SET = "set";
	public static final String TAG_METHOD_REMOVE = "remove";
	public static final String TAG_METHOD_UPDATE = "update";
	
	
    private MongoTemplate mongoTemplate;

    public void setTagIndexes(String UserId, List<TagIndex> tags) throws Exception {
    	
    	if (tags == null) {
    		return;
    	}
		TagExecutor tagWriter = new TagExecutor(TAG_METHOD_SET, mongoTemplate, tags, null);
		tagWriter.start();
    }
    public void removeTagIndexes(String userId, List<TagIndex> tags) throws Exception {
    	
    	if (tags == null) {
    		return;
    	}
		TagExecutor tagWriter = new TagExecutor(TAG_METHOD_REMOVE, mongoTemplate, tags, null);
		tagWriter.start();
    }
    public void updateTagIndexes(String userId, List<TagIndex> oldTags, List<TagIndex> newTags) throws Exception {
    	
    	if (newTags == null) {
    		return;
    	}
		TagExecutor tagWriter = new TagExecutor(TAG_METHOD_UPDATE, mongoTemplate, newTags, oldTags);
		tagWriter.start();
    }
    
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	@Override
	public List<TagIndex> getTagIndex(String userId, String[] tags) throws Exception {

		List result = new ArrayList();
		for (int i = 0; i < tags.length; i++) {
	        Criteria criteria = new Criteria("tagName");
	        criteria.is(tags[i]);
	        Query query = new Query(criteria);
	        TagIndex findTagIndex = mongoTemplate.findOne(query, TagIndex.class, "tagIndex");
			if (findTagIndex != null) {
				result.add(findTagIndex);
			}
		}
		return result;
	}
    
}

class TagExecutor extends Thread {
	
	private String method;
	private List<TagIndex> tagIndexs;
	private MongoTemplate mongoTemplate;
	private List<TagIndex> oldTagIndexes;
	
	public TagExecutor(String method , MongoTemplate mongoTemplate , List<TagIndex> tagIndexes, List<TagIndex> oldTagIndexes) {
		this.method = method;
		this.mongoTemplate = mongoTemplate;
		this.tagIndexs = tagIndexes;
		this.oldTagIndexes = oldTagIndexes;
	}
	
	public void run() {
		
		System.out.println("TagWriter Start! : " + this.hashCode());
		
		if (this.method.equals(NoSqlDaoImpl.TAG_METHOD_SET)) {
			
			for (int i = 0; i < this.tagIndexs.size(); i++) {
				
				TagIndex tagIndex = tagIndexs.get(i);
				
		        Criteria criteria = new Criteria("tagName");
		        criteria.is(tagIndex.getTagName());
		        Query query = new Query(criteria);
		        TagIndex findTagIndex = mongoTemplate.findOne(query, TagIndex.class, "tagIndex");

				System.out.println("	TagWriter Input ! : " + this.hashCode() + " : " + tagIndex.getTagName());
		        if (findTagIndex == null) {
		        	mongoTemplate.insert(tagIndex, "tagIndex");
		        } else {
		        	findTagIndex.addTagObjId(tagIndex.getObjIds());
		        	Update update = new Update();
		        	update.set("objIds", findTagIndex.getObjIds());
		        	mongoTemplate.updateFirst(query, update, "tagIndex");
		        }
			}
			
		} else if (this.method.equals(NoSqlDaoImpl.TAG_METHOD_REMOVE)) {
			
			for (int i = 0; i < this.tagIndexs.size(); i++) {
				TagIndex tagIndex = tagIndexs.get(i);
		        Criteria criteria = new Criteria("tagName");
		        criteria.is(tagIndex.getTagName());
		        Query query = new Query(criteria);
		        TagIndex findTagIndex = mongoTemplate.findOne(query, TagIndex.class, "tagIndex");

		        if (findTagIndex == null) {
		        	continue;
		        } else {
		        	
		        	if (findTagIndex.getObjIds() == null) {
		        		mongoTemplate.remove(query, "tagIndex");
		        	}
		        	
		        	findTagIndex.setObjIds(findTagIndex.getObjIds().replace(tagIndex.getObjIds(), ""));
		        	
		        	//가비지가(;;) 남을수 있기때문에 length < 3 조건을 준다 
		        	if (findTagIndex.getObjIds() == null || findTagIndex.getObjIds().length() < 3) {
		        		mongoTemplate.remove(query, "tagIndex");
		        	}
		        	
		        	Update update = new Update();
		        	update.set("objIds", findTagIndex.getObjIds());
		        	mongoTemplate.updateFirst(query, update, "tagIndex");
		        }
			}
			
		} else if (this.method.equals(NoSqlDaoImpl.TAG_METHOD_UPDATE)) {
			
			//업데이트 경우에는 기존 데이터를 모두 삭제하고 다시 입력을 진행한다.
			if (this.oldTagIndexes != null) {
				for (int i = 0; i < this.oldTagIndexes.size(); i++) {
					TagIndex oldTagIndex = this.oldTagIndexes.get(i);
			        Criteria criteria = new Criteria("tagName");
			        criteria.is(oldTagIndex.getTagName());
			        Query query = new Query(criteria);
			        TagIndex findOldTagIndex = mongoTemplate.findOne(query, TagIndex.class, "tagIndex");

			        if (findOldTagIndex == null) {
			        	continue;
			        } else {
			        	
			        	if (findOldTagIndex.getObjIds() == null) {
			        		mongoTemplate.remove(query, "tagIndex");
			        	}
			        	
			        	findOldTagIndex.setObjIds(findOldTagIndex.getObjIds().replace(oldTagIndex.getObjIds(), ""));
			        	
			        	//가비지가(;;) 남을수 있기때문에 length < 3 조건을 준다 
			        	if (findOldTagIndex.getObjIds() == null || findOldTagIndex.getObjIds().length() < 3) {
			        		mongoTemplate.remove(query, "tagIndex");
			        	}
			        	
			        	Update update = new Update();
			        	update.set("objIds", findOldTagIndex.getObjIds());
			        	mongoTemplate.updateFirst(query, update, "tagIndex");
			        }
				}
			}
			for (int i = 0; i < this.tagIndexs.size(); i++) {
				
				TagIndex tagIndex = tagIndexs.get(i);
				
		        Criteria criteria = new Criteria("tagName");
		        criteria.is(tagIndex.getTagName());
		        Query query = new Query(criteria);
		        TagIndex findTagIndex = mongoTemplate.findOne(query, TagIndex.class, "tagIndex");

				System.out.println("	TagWriter Input ! : " + this.hashCode() + " : " + tagIndex.getTagName());
		        if (findTagIndex == null) {
		        	mongoTemplate.insert(tagIndex, "tagIndex");
		        } else {
		        	findTagIndex.addTagObjId(tagIndex.getObjIds());
		        	Update update = new Update();
		        	update.set("objIds", findTagIndex.getObjIds());
		        	mongoTemplate.updateFirst(query, update, "tagIndex");
		        }
			}
		}
	}
}

