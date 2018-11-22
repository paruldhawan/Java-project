package com.snap.dao;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snap.controller.AlarmContoller;
import com.snap.exception.InfluxQueryException;
import com.snap.model.Datasource;

@Component
public class InfluxDAOImpl implements InfluxDAO {

	public InfluxDAOImpl() {
	}


	private  Logger logger=LoggerFactory.getLogger(InfluxDAOImpl.class);
	
	@Override
	public List<String> getMeasurements(Datasource datasource, String database) throws InfluxQueryException {
		String command = "show measurements";

		QueryResult result = doQuery(datasource, database, command);

		return selectColumns(result, "measurements", "name");
	}

	@Override
	public List<String> getFields(Datasource datasource, String database,String retentionPolicy, String measurement)
			throws InfluxQueryException {
		String command = "show field keys from " + retentionPolicy+ "." + measurement;
		if(retentionPolicy.equalsIgnoreCase("default")){
			command = "show field keys from "  + measurement;
		}
		QueryResult result = doQuery(datasource, database, command);

		return selectColumns(result, measurement, "fieldKey");
	}

	@Override
	public HashMap<String, String> getFieldsSchema(Datasource datasource, String database, String retentionPolicy, String measurement)
			throws InfluxQueryException {
		String command = "show field keys from " + retentionPolicy + "."+ measurement;
		if(retentionPolicy.equalsIgnoreCase("default")){
			command = "show field keys from "  + measurement;
		}
		QueryResult result = doQuery(datasource, database, command);
		return selectSchema(result, measurement, "fieldKey", "fieldType");
	}

	@Override
	public List<String> getTags(Datasource datasource, String database, String retentionPolicy, String measurement)
			throws InfluxQueryException {
		String command = "show tag keys from " + retentionPolicy + "."+ measurement;
		if(retentionPolicy.equalsIgnoreCase("default")){
			command = "show tag  keys from "  + measurement;
		}
		QueryResult result = doQuery(datasource, database, command);

		return selectColumns(result, measurement, "tagKey");

	}

	@Override
	public List<String> getTagValues(Datasource datasource, String database, String measurement, String tag)
			throws InfluxQueryException {
		String command = "show tag values from " + measurement + " with key = " + tag;
		QueryResult result = doQuery(datasource, database, command);

		return selectColumns(result, measurement, "value");
	}

	@Override
	public List<String> getRetentionPolicies(Datasource datasource, String database) throws InfluxQueryException {
		String command = "SHOW RETENTION POLICIES ON  " + database;
		QueryResult result = doQuery(datasource, database, command);
		return selectName(result, "name");
	}

	private QueryResult doQuery(Datasource datasource, String database, String command) throws InfluxQueryException {
		String username = " ";
		String password = " ";
		if (datasource.getUsername() != null && !datasource.getUsername().equals("")) {
			username = datasource.getUsername();
		}

		if (datasource.getPassword() != null && !datasource.getPassword().equals("")) {
			password = datasource.getPassword();
		}

		InfluxDB influxDB = InfluxDBFactory.connect(datasource.getURL(), username, password);
		Query query = new Query(command, database);
		QueryResult result = null;
		try {
			logger.debug("Doing query " + query.toString());
			result = influxDB.query(query);
			logger.debug("Result is " + result.toString());

		} catch (RuntimeException e) {
			throw new InfluxQueryException(e.getMessage());
		}

		if (result.getError() != null) {
			throw new InfluxQueryException(result.getError());
		}

		return result;

	}

	private List<String> selectName(QueryResult queryResult, String columnName) {
		List<String> toReturn = new LinkedList<String>();
		System.out.println("Got result \n" + queryResult);
		for (Result result : queryResult.getResults()) {
			for (Series series : result.getSeries()) {
				int columnIndex = 0;
				List<String> columns = series.getColumns();
				while (columnIndex < columns.size()) {
					if (columns.get(columnIndex).equals(columnName)) {
						break;
					}
					columnIndex++;
				}

				if (columnIndex == columns.size()) {
					continue;
				}
				for (List<Object> values : series.getValues()) {
					toReturn.add((String) values.get(columnIndex));
				}

			}
		}
		System.out.println("Returning " + toReturn);
		return toReturn;
	}

	private List<String> selectColumns(QueryResult queryResult, String seriesName, String columnName) {
		List<String> toReturn = new LinkedList<String>();
		System.out.println("Got result \n" + queryResult);
		for (Result result : queryResult.getResults()) {
			for (Series series : result.getSeries()) {
				if (series.getName().equals(seriesName)) {
					int columnIndex = 0;
					List<String> columns = series.getColumns();
					while (columnIndex < columns.size()) {
						if (columns.get(columnIndex).equals(columnName)) {
							break;
						}
						columnIndex++;
					}

					if (columnIndex == columns.size()) {
						continue;
					}
					for (List<Object> values : series.getValues()) {
						toReturn.add((String) values.get(columnIndex));
					}

				}
			}
		}
		System.out.println("Returning " + toReturn);
		return toReturn;
	}

	private HashMap<String, String> selectSchema(QueryResult queryResult, String seriesName, String columnName1,
			String columnName2) {
		HashMap<String, String> toReturn = new HashMap<String, String>();
		System.out.println("Got result \n" + queryResult);
		for (Result result : queryResult.getResults()) {
			for (Series series : result.getSeries()) {
				if (series.getName().equals(seriesName)) {
					int colIndex1 = 0;
					int colIndex2 = 0;
					List<String> columns = series.getColumns();
					while (colIndex1 < columns.size()) {
						if (columns.get(colIndex1).equals(columnName1)) {
							break;
						}
						colIndex1++;
					}
					while (colIndex2 < columns.size()) {
						if (columns.get(colIndex2).equals(columnName2)) {
							break;
						}
						colIndex2++;
					}
					System.out.println("colIndex1" + colIndex1);
					System.out.println("colIndex2" + colIndex2);

					if (colIndex1 == columns.size() || colIndex2 == columns.size()) {
						continue;
					}

					for (List<Object> values : series.getValues()) {
						toReturn.put((String) values.get(colIndex1), (String) values.get(colIndex2));
					}

				}
			}
		}
		System.out.println("Returning " + toReturn.size());
		return toReturn;
	}

}
