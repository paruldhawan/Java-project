package com.snap.dao;

import java.util.List;

import javax.sql.DataSource;

import com.snap.model.Notification;

public interface NotificationDao {
	
	void addNotification(Notification notification);
	Notification getNotification(int id);
	List<Notification> getNotificationList();
	List<String> getNameList(String username);
	Notification getNotification(String notificationName, String username);
	//Notification getNotification(String name, String username);
	List<Notification> getNotification(List<String> notificationName, String username);
	void addNotification(int id, String name, String string, List<String> address, String username);
	void deleteNotification(int id);
	void modifyNotication(Notification notification);
	void modifyNotification(int id, String name, String type, List<String> address);

}
 
