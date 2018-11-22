package com.snap.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.snap.dao.DatasourceDAO;
import com.snap.dao.InfluxDAO;
import com.snap.exception.InfluxQueryException;
import com.snap.model.Datasource;

@Controller
public class InfluxAPI {

	@Autowired
	DatasourceDAO datasourceDAO;

	@Autowired
	InfluxDAO influxDAO;

	public InfluxAPI() {

	}

	@RequestMapping("influx-api/get-datasources")
	public @ResponseBody List<String> getDatasources() {

		return datasourceDAO.getNameList();

	}

	@RequestMapping("influx-api/get-measurements")
	public @ResponseBody List<String> getMeasurements(@RequestParam String datasourceName) throws InfluxQueryException {

		Datasource ds = datasourceDAO.getDatasource(datasourceName);
		return influxDAO.getMeasurements(ds, ds.getDatabase());

	}

	@RequestMapping("influx-api/get-fields")
	public @ResponseBody List<String> getFields(@RequestParam String datasourceName, @RequestParam String retentionPolicy, @RequestParam String measurement)
			throws InfluxQueryException {

		Datasource ds = datasourceDAO.getDatasource(datasourceName);
		return influxDAO.getFields(ds, ds.getDatabase(),retentionPolicy, measurement);

	}

	@RequestMapping("influx-api/get-fieldsSchema")
	public @ResponseBody HashMap<String, String> getFieldsSchema(@RequestParam String datasourceName,
			@RequestParam String retentionPolicy,
			@RequestParam String measurement) throws InfluxQueryException {
		Datasource ds = datasourceDAO.getDatasource(datasourceName);
		return influxDAO.getFieldsSchema(ds, ds.getDatabase(),retentionPolicy, measurement);
	}

	@RequestMapping("influx-api/get-retentionPolicies")
	public @ResponseBody List<String> getRetentionPolicies(@RequestParam String datasourceName)
			throws InfluxQueryException {
		Datasource ds = datasourceDAO.getDatasource(datasourceName);
		return influxDAO.getRetentionPolicies(ds, ds.getDatabase());
	}

	@RequestMapping("influx-api/get-tags")
	public @ResponseBody List<String> getTags(@RequestParam String datasourceName,
			@RequestParam String retentionPolicy,
			@RequestParam String measurement)
			throws InfluxQueryException {

		Datasource ds = datasourceDAO.getDatasource(datasourceName);
		return influxDAO.getTags(ds, ds.getDatabase(), retentionPolicy, measurement);

	}

	@RequestMapping("influx-api/get-tag-values")
	public @ResponseBody List<String> getTagValues(@RequestParam String datasourceName,
			@RequestParam String retentionPolicy,
			@RequestParam String measurement, @RequestParam String tag) throws InfluxQueryException {

		Datasource ds = datasourceDAO.getDatasource(datasourceName);
		return influxDAO.getTagValues(ds, ds.getDatabase(), measurement, tag);

	}

	@ExceptionHandler({ InfluxQueryException.class })
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody Map<String, String> onKapacitorError(Exception e) {

		Map<String, String> m = new HashMap<String, String>();
		m.put("error", e.getMessage());
		return m;
	}
}