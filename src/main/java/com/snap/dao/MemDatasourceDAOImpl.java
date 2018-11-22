package com.snap.dao;

import java.util.ArrayList;
import java.util.List;

import com.snap.model.Datasource;

public class MemDatasourceDAOImpl implements DatasourceDAO {
	
	List<Datasource> datasources;
	private int id = 3;
	
	public MemDatasourceDAOImpl() {
		datasources = new ArrayList<>(4);
		Datasource ds  = new Datasource(1, "test1", "http://10.41.80.205:8086", "telegraf", "", "");
		datasources.add(ds);
		ds  = new Datasource(2, "test1", "http://10.41.80.191:8086", "telegraf", "", "");
		datasources.add(ds);
	}

	@Override
	public List<Datasource> listDatasources() {
		return datasources;
	}

	@Override
	public Datasource getDatasource(int id) {
		
		for(Datasource ds : datasources){
			if(ds.getID()==id){
				return ds;
			}
		}
		
		return null;
	}

	@Override
	public Datasource getDatasource(String name) {
		
		for(Datasource ds : datasources){
			if(ds.getName().equals(name)){
				return ds;
			}
		}
		
		return null;
	}

	@Override
	public void addDatasource(Datasource datasource) {
		datasources.add(datasource);
		
	}

	@Override
	public void addDatasource(String name, String url, String database, String username, String password) {
		
		datasources.add(new Datasource(id, name, url, database, username, password));
		id++;
	}

	@Override
	public void modifyDatasource(int id, String name, String url, String database, String username, String password) {
		Datasource ds = getDatasource(id);
		ds.setName(name);
		ds.setURL(url);
		ds.setUsername(username);
		ds.setPassword(password);
		
	}

	@Override
	public void modifyDatasource(Datasource datasource) {
		throw new UnsupportedOperationException("N");
	}

	@Override
	public void deleteDatasource(int id) {
		throw new UnsupportedOperationException("N");
		
	}

	@Override
	public void deleteDatasource(String name) {
		throw new UnsupportedOperationException("N");
	}

	@Override
	public List<String> getNameList() {

		throw new UnsupportedOperationException("N");
	}

}
