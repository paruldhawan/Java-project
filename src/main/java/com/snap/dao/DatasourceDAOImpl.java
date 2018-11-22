package com.snap.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.snap.model.Datasource;

public class DatasourceDAOImpl implements DatasourceDAO {

	private JdbcTemplate jdbcTemplate;

	public DatasourceDAOImpl(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	public DatasourceDAOImpl() {

	}

	public void setDatasource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<Datasource> listDatasources() {

		String sql = "SELECT * from datasource";
		List<Datasource> datasources = jdbcTemplate.query(sql, new RowMapper<Datasource>() {

			@Override
			public Datasource mapRow(ResultSet rs, int rowIndex) throws SQLException {
				return resultSet2Datasource(rs);
			}

		});

		return datasources;
	}

	@Override
	public Datasource getDatasource(int id) {
		System.out.println("Getting datasource");
		String sql = "SELECT * from datasource where id = " + id;
		Datasource datasource = jdbcTemplate.query(sql, new ResultSetExtractor<Datasource>() {

			@Override
			public Datasource extractData(ResultSet rs) throws SQLException, DataAccessException {
				System.out.println("Got result");
				if (rs.next()) {
					return resultSet2Datasource(rs);
				}
				System.out.println("Done");

				return null;
			}
		});

		return datasource;
	}

	@Override
	public Datasource getDatasource(String name) {
		System.out.println("Getting datasource");
		String sql = "SELECT * from datasource where name =  \"" + name + "\"";
		Datasource datasource = jdbcTemplate.query(sql, new ResultSetExtractor<Datasource>() {

			@Override
			public Datasource extractData(ResultSet rs) throws SQLException, DataAccessException {
				System.out.println("Got result");
				if (rs.next()) {
					return resultSet2Datasource(rs);
				}
				System.out.println("Done");

				return null;
			}
		});

		return datasource;
	}

	@Override
	public void addDatasource(Datasource datasource) {

		addDatasource(datasource.getName(), datasource.getURL(), datasource.getDatabase(), datasource.getUsername(),
				datasource.getPassword());
	}

	@Override
	public void modifyDatasource(Datasource datasource) {

		modifyDatasource(datasource.getID(),
				datasource.getName(),
				datasource.getURL(),
				datasource.getDatabase(),
				datasource.getUsername(),
				datasource.getPassword());
	}
	
	@Override
	public void modifyDatasource(int id,String name, String url, String database, String username, String password) {
		String sql = "UPDATE datasource SET name=?, url=?, dbname=?, username=?, pwd=? WHERE id=?";
		jdbcTemplate.update(sql, name, url,database, username,password,id);
	}


	@Override
	public void deleteDatasource(int id) {

		String sql = "DELETE FROM datasource WHERE id=?";
	    jdbcTemplate.update(sql, id);
	}
	
	@Override
	public void deleteDatasource(String name) {
		String sql = "DELETE FROM datasource WHERE name=?";
	    jdbcTemplate.update(sql, name);
		
	}

	@Override
	public void addDatasource(String name, String url, String database, String username, String password) {

		String sql = "insert into datasource (name, url, dbname, username,pwd) values (?, ?,?,?,?)";

		jdbcTemplate.update(sql, name, url, database, username, password);
		return;

	}

	private Datasource resultSet2Datasource(ResultSet rs) throws SQLException {

		int id = rs.getInt("id");
		String name = rs.getString("name");
		String url = rs.getString("url");
		String database = rs.getString("dbname");
		String username = rs.getString("username");
		String password = rs.getString("pwd");

		return new Datasource(id, name, url, database, username, password);

	}

	@Override
	public List<String> getNameList() {

		String sql = "SELECT name from datasource";
		List<String> names = jdbcTemplate.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowIndex) throws SQLException {
				return rs.getString("name");
			}

		});
		return names;
	}



	
}
