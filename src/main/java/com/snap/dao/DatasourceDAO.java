package com.snap.dao;

import java.util.List;

import com.snap.model.*;

public interface DatasourceDAO {

	List<Datasource> listDatasources();
	Datasource getDatasource(int id);
	Datasource getDatasource(String name);
	void addDatasource(Datasource datasource);
	void addDatasource(String name, String url, String database, String username, String password);
	void modifyDatasource(int id,String name, String url, String database, String username, String password);
	void modifyDatasource(Datasource datasource);
	void deleteDatasource(int id);
	void deleteDatasource(String name);
	List<String> getNameList();
	
}
