package com.snap.kapacitor.model;

import java.util.List;
import java.util.Map;

import com.snap.dao.DatasourceDAO;

public abstract class Query {

	public abstract String getBatchQuery(String batchId, DatasourceDAO datasourceDAO);
	
	public abstract String getCondition(String batchId, String prefix);

	public abstract void getAlertMessage(String batchId, String prefix, List<String> list);

	public abstract void getDsRp(Map<String, List<String>> mapping) ;
}
