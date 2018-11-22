package com.snap.kapacitor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.snap.dao.DatasourceDAO;
import com.snap.kapacitor.QueryTemplates;
import com.snap.model.Datasource;
import com.snap.properties.KapacitorConfig;

import ch.qos.logback.core.util.SystemInfo;

public class SimpleQuery extends Query {

	private String retentionPolicy;
	private String dataSource;
	private String measurement;
	private String fieldName;
	private String fieldOperator;
	private float fieldThreshold;
	private WhereClause[] where;
	private String[] groupBy;
	private String groupByDuration;
	private String groupByOperation;
	private String derivativeOperation;

	public String getRetentionPolicy() {
		return retentionPolicy;
	}

	public void setRetentionPolicy(String retentionPolicy) {
		this.retentionPolicy = retentionPolicy;
	}

	public String getGroupByOperaion() {
		return groupByOperation;
	}

	public void setGroupByOperaion(String groupByOperaion) {
		this.groupByOperation = groupByOperaion;
	}

	public SimpleQuery(String rp, String dataSource, String measurement, String fieldName, String fieldOperator,
			float fieldThreshold, WhereClause[] where, String[] groupBy, String groupbyDuration,
			String groupByOperation) {
		super();
		this.retentionPolicy = rp;
		this.dataSource = dataSource;
		this.measurement = measurement;
		this.fieldName = fieldName;
		this.fieldOperator = fieldOperator;
		this.fieldThreshold = fieldThreshold;
		this.where = where;
		this.groupBy = groupBy;
		this.groupByDuration = groupbyDuration;
		this.groupByOperation = groupByOperation;
	}

	public String getRp() {
		return retentionPolicy;
	}

	public void setRp(String rp) {
		this.retentionPolicy = rp;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getMeasurement() {
		return measurement;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldOperator() {
		return fieldOperator;
	}

	public void setFieldOperator(String fieldOperator) {
		this.fieldOperator = fieldOperator;
	}

	public float getFieldThreshold() {
		return fieldThreshold;
	}

	public void setFieldThreshold(float fieldThreshold) {
		this.fieldThreshold = fieldThreshold;
	}

	public WhereClause[] getWhere() {
		return where;
	}

	public void setWhere(WhereClause[] where) {
		this.where = where;
	}

	public String[] getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String[] groupBy) {
		this.groupBy = groupBy;
	}

	public String getGroupbyDuration() {
		return groupByDuration;
	}

	public void setGroupbyDuration(String groupbyDuration) {
		this.groupByDuration = groupbyDuration;
	}

	public String getGroupByOperation() {
		return groupByOperation;
	}

	public void setGroupByOperation(String groupbyOperation) {
		this.groupByOperation = groupbyOperation;
	}

	@Override
	public String toString() {
		return measurement + "." + fieldName + " " + fieldOperator + " " + fieldThreshold + " where ("
				+ Arrays.toString(where) + " ) group by " + Arrays.toString(groupBy);

	}

	void fillFieldsOfMeasurement(Map<String, List<String>> hashMap) {

		List<String> fieldList = hashMap.get(measurement);
		if (fieldList == null) {
			fieldList = new LinkedList<String>();
			hashMap.put(measurement, fieldList);
		}
		fieldList.add(fieldName);

	}

	String joinString(Object[] list, String delimiter) {

		if (list.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.length - 1; i++) {
			sb.append(list[i].toString() + delimiter);
		}
		sb.append(list[list.length - 1]);
		return sb.toString();
	}

	String quoteString(String val, String quote) {
		return quote + val + quote;
	}

	private String joinWithQuote(String[] list, String quote, String delim) {
		if (list.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.length - 1; i++) {
			sb.append(quote + list[i].toString() + quote + delim);
		}
		sb.append(quote + list[list.length - 1] + quote);
		return sb.toString();
	}

	public String getBatchQuery(String batchId, DatasourceDAO datasourceDAO) {

		String batchQuery = QueryTemplates.batchTemplate;
		batchQuery = batchQuery.replace("{id}", batchId);
		String database = datasourceDAO.getDatasource(dataSource).getDatabase();
		String field = quoteString(fieldName, "\"");
		String fieldWithOps = "";
		if (derivativeOperation != null && !derivativeOperation.equals("none")) {
			fieldWithOps = derivativeOperation + "(";
		}
		fieldWithOps = fieldWithOps + groupByOperation + "(" + field + ")";
		if (derivativeOperation != null && !derivativeOperation.equals("none")) {
			fieldWithOps = fieldWithOps + ")";
		}
		batchQuery = batchQuery.replace("{field_with_ops}", fieldWithOps);

		batchQuery = batchQuery.replace("{field}", field);
		
		
		batchQuery = batchQuery.replace("{groupby-operation}", groupByOperation);
		String measurementPath = quoteString(database, "\"") + "." + quoteString(retentionPolicy, "\"") + "."
				+ measurement;

		batchQuery = batchQuery.replace("{measurement}", measurementPath);

		String whereClause = joinString(where, " and ");
		if (where.length != 0) {
			whereClause = " where " + whereClause;
		}
		batchQuery = batchQuery.replace("{where}", whereClause);
		batchQuery = batchQuery.replace("{period}", groupByDuration);
		String groupByString = "time("+groupByDuration+")";
		if(groupBy.length>0){
			groupByString = groupByString + ", " + joinWithQuote(groupBy, "\'", ", ");
		}
		batchQuery = batchQuery.replace("{groupby}", groupByString );
		batchQuery = batchQuery.replace("{every}",  KapacitorConfig.getString("every"));

		return batchQuery;
	}

	@Override
	public String getCondition(String batchId, String prefix) {
		if (!prefix.equals("")) {
			prefix = prefix + ".";
		}
		return "( \"" + prefix + batchId + "." + fieldName + "\" " + toSign(fieldOperator) + " " + fieldThreshold + ")";

	}

	private static String toSign(String val) {
		String sign = "";
		if (val.equals("less than")) {
			sign = "<";
		} else if (val.equals("greater than")) {
			sign = ">";
		} else if (val.equals("not equals")) {
			sign = "!=";
		} else if (val.equals("equals")) {
			sign = "==";
		}
		return sign;
	}

	@Override
	public void getAlertMessage(String batchId, String prefix, List<String> list) {

		String msg = QueryTemplates.fieldMessageTemplate;
		msg = msg.replace("{complete-field-name}", prefix + batchId + "." + fieldName);
		msg = msg.replace("{field-name}", fieldName);

		list.add(msg);

	}

	@Override
	public void getDsRp(Map<String, List<String>> mapping) {

		List<String> rps = mapping.get(dataSource);
		if (rps == null) {
			rps = new ArrayList<>();
			mapping.put(dataSource, rps);
		}
		if (!rps.contains(retentionPolicy)) {
			rps.add(retentionPolicy);
		}
		mapping.put(dataSource, rps);

		System.out.println("Mapping is " + mapping);
	}

	public String getDerivativeOperation() {
		return derivativeOperation;
	}

	public void setDerivativeOperation(String derivativeOperation) {
		this.derivativeOperation = derivativeOperation;
	}

}