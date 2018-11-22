package com.snap.kapacitor.model;

import java.util.List;
import java.util.Map;

import com.snap.dao.DatasourceDAO;
import com.snap.kapacitor.QueryTemplates;
import com.snap.properties.KapacitorConfig;

public class ComplexQuery extends Query {
	private String operation;

	private List<Query> subQueries;
	private List<String> joinByTags;

	public ComplexQuery(String operation, List<Query> subQueries, List<String> joinByTags) {
		super();
		this.operation = operation;
		this.subQueries = subQueries;
		this.joinByTags = joinByTags;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public List<Query> getSubQueries() {
		return subQueries;
	}

	public void setSubQueries(List<Query> subQueries) {
		this.subQueries = subQueries;
	}

	public List<String> getJoinByTags() {
		return joinByTags;
	}

	public void setJoinByTags(List<String> joinByTags) {
		this.joinByTags = joinByTags;
	}

	@Override
	public String toString() {
		return operation + "{" + joinString(subQueries, ", ") + "}";
	}

	private String joinString(List<Query> list, String delim) {

		if (list.size() == 0)
			return "";

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size() - 1; i++) {
			sb.append(list.get(i) + delim);
		}
		sb.append(list.get(list.size() - 1));
		return sb.toString();

	}

	public String getBatchQuery(String batchId, DatasourceDAO datasourceDAO) {

		String batchQuery = QueryTemplates.joinTemplate;

		String batchPrefix = batchId + "_";
		StringBuilder sb = new StringBuilder();
		// #FIXME when there is single sub query
		/*
		 * if(subQueries.size() == 1){ String q =
		 * subQueries.get(0).getBatchQuery(batchPrefix+0, datasourceDAO);
		 * sb.append(q); sb.append("\n"); sb.append("var " + batchId + " = " +
		 * batchPrefix+0); return sb.toString();
		 * 
		 * }
		 */

		for (int i = 0; i < subQueries.size(); i++) {
			String q = subQueries.get(i).getBatchQuery(batchPrefix + i, datasourceDAO);
			sb.append(q);
		}

		batchQuery = batchQuery.replace("{id}", batchId);

		String firstId = batchPrefix + 0;
		batchQuery = batchQuery.replace("{first-node}", firstId);
		StringBuilder nodes = new StringBuilder();

		for (int i = 1; i < subQueries.size() - 1; i++) {
			nodes.append("'" + batchPrefix + i + "',");
		}
		nodes.append(batchPrefix + (subQueries.size() - 1));
		batchQuery = batchQuery.replace("{nodes}", nodes.toString());

		StringBuilder as = new StringBuilder();

		as.append("'" + (batchPrefix + 0) + "', ");
		for (int i = 1; i < subQueries.size() - 1; i++) {
			as.append("'" + batchPrefix + i + "', ");
		}
		if (subQueries.size() == 1) {
			as.append("'" + batchPrefix + "1" + "' ");
			
		} else {

			as.append("'" + batchPrefix + (subQueries.size() - 1) + "' ");
		}
		batchQuery = batchQuery.replace("{as}", as);

		sb.append(System.lineSeparator());
		sb.append(batchQuery);
		sb.append("\n");

		return sb.toString();
	}

	@Override
	public String getCondition(String batchId, String prefix) {

		StringBuilder sb = new StringBuilder();
		String sign = operation.equals("and") ? " AND " : " OR ";
		String batchPrefix = batchId + "_";

		if (batchId.equals("root")) {
			prefix = "";
		} else if (prefix.equals("")) {
			prefix = batchId;
		} else {
			System.out.println("Prefix is '" + prefix + "'" + prefix.equals(""));
			prefix = prefix + "." + batchId;
		}
		System.out.println("Prefix for '" + batchId + "' is '" + prefix + "'");

		for (int i = 0; i < subQueries.size(); i++) {
			String q = subQueries.get(i).getCondition(batchPrefix + i, prefix);
			sb.append(q);
			if (i < subQueries.size() - 1) {
				sb.append(sign);
			}

		}

		return "( " + sb.toString() + " ) ";
	}

	@Override
	public void getAlertMessage(String batchId, String prefix, List<String> list) {

		StringBuilder sb = new StringBuilder();
		String sign = operation.equals("and") ? " AND " : " OR ";
		String batchPrefix = batchId + "_";

		if (batchId.equals("root")) {
			prefix = "";
		} else if (prefix.equals("")) {
			prefix = batchId;
		} else {
			System.out.println("Prefix is '" + prefix + "'" + prefix.equals(""));
			prefix = prefix + "." + batchId;
		}
		System.out.println("Prefix for '" + batchId + "' is '" + prefix + "'");

		for (int i = 0; i < subQueries.size(); i++) {
			subQueries.get(i).getAlertMessage(batchPrefix + i, prefix, list);

		}

	}

	@Override
	public void getDsRp(Map<String, List<String>> mapping) {
		for (Query query : subQueries) {
			query.getDsRp(mapping);
		}

	}

}
