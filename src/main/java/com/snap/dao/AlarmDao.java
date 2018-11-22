package com.snap.dao;

import java.sql.SQLException;
import java.util.List;

import com.snap.model.AlarmDefinition;

public interface AlarmDao {

	List<AlarmDefinition> listAlarmDefinitions();

	AlarmDefinition getAlarmDefinition(int id);
	AlarmDefinition getAlarmDefinition(String name);

	void addAlarmDefinition(AlarmDefinition AlarmDefinition);

	void addAlarmDefinition(String name, String description, String query, boolean enabled, int datasourcdId) ;

	void modifyAlarmDefinition(AlarmDefinition AlarmDefinition);
	
	void modifyAlarmDefinition(String name, int id);

	void modifyAlarmDefinition(String name, String description, String query, boolean enabled, int datasourcdId);

	void deleteAlarmDefinition(int id);
	void deleteAlarmDefinition(String name);

	List<String> getNameList();
	int getAlarmId(String name);
	int getNotificationId(String name);
	void alarmNotification(int alarmId,int notiId);
}
