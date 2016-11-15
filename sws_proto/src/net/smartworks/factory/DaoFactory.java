/*	
 * file 		 : DaoFactory.java
 * created by    : kmyu
 * creation-date : 2016. 11. 15.
 */

package net.smartworks.factory;

import net.smartworks.dao.INoSqlDao;
import net.smartworks.dao.ISqlDao;

public class DaoFactory {
	
	private static DaoFactory factory;

	private ISqlDao sqlDao;
	private INoSqlDao noSqlDao;
	
	public synchronized static DaoFactory createInstance() {
		if(factory == null) 
			factory = new DaoFactory();
		return factory;
	}
	public static DaoFactory getInstance() {
		return factory;
	}
	public ISqlDao getSqlDao() {
		return sqlDao;
	}
	public void setSqlDao(ISqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}
	public INoSqlDao getNoSqlDao() {
		return noSqlDao;
	}
	public void setNoSqlDao(INoSqlDao noSqlDao) {
		this.noSqlDao = noSqlDao;
	}
	
}

