/*	
 * file 		 : ManagerFactory.java
 * created by    : kmyu
 * creation-date : 2016. 11. 15.
 */

package net.smartworks.factory;

import net.smartworks.manager.IManager;

public class ManagerFactory {

	private static ManagerFactory factory;

	private IManager manager;
	
	public synchronized static ManagerFactory createInstance() {
		if(factory == null) 
			factory = new ManagerFactory();
		return factory;
	}
	public static ManagerFactory getInstance() {
		return factory;
	}
	public IManager getManager() {
		return manager;
	}
	public void setManager(IManager manager) {
		this.manager = manager;
	}
}

