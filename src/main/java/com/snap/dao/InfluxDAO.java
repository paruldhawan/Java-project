package com.snap.dao;

import java.util.HashMap;
import java.util.List;

import com.snap.exception.InfluxQueryException;
import com.snap.model.Datasource;

public interface InfluxDAO {

	public List<String>  getMeasurements(Datasource datasource, String database) throws InfluxQueryException;
	public List<String> getFields(Datasource datasource, String database, String measurement, String retentionPolicy) throws InfluxQueryException;
	public List<String> getTags(Datasource datasource, String database, String measurement, String retentionPolicy) throws InfluxQueryException;
	public List<String> getTagValues(Datasource datasource, String database, String measurement, String tag) throws InfluxQueryException;
	public  HashMap<String, String> getFieldsSchema(Datasource datasource, String database, String measurement, String retentionPolicy) throws InfluxQueryException;
	public List<String> getRetentionPolicies(Datasource datasource, String database) throws InfluxQueryException;
}

