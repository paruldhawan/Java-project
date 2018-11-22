package com.snap.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.ConnectionHandle;

import com.snap.model.AlarmDefinition;
import com.snap.model.Datasource;

public class AlarmDAOImpl implements AlarmDao {

	private JdbcTemplate jdbcTemplate;
	private DataSource datasource;

	public AlarmDAOImpl(DataSource datasource) {
		jdbcTemplate = new JdbcTemplate(datasource);
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
		jdbcTemplate = new JdbcTemplate(datasource);

	}

	@Override
	public List<AlarmDefinition> listAlarmDefinitions() {
		String sql = "SELECT * from alarm_def";
		List<AlarmDefinition> alarmDefinitions = jdbcTemplate.query(sql, new RowMapper<AlarmDefinition>() {

			@Override
			public AlarmDefinition mapRow(ResultSet rs, int rowIndex) throws SQLException {
				return resultSet2AlarmDefinition(rs);
			}

		});

		return alarmDefinitions;
	}

	@Override
	public AlarmDefinition getAlarmDefinition(int id) {
		String sql = "SELECT * from alarm_def where id =  \"" + id + "\"";
		AlarmDefinition alarmDefinition = jdbcTemplate.query(sql, new ResultSetExtractor<AlarmDefinition>() {

			@Override
			public AlarmDefinition extractData(ResultSet rs) throws SQLException, DataAccessException {
				System.out.println("Got result");
				if (rs.next()) {
					return resultSet2AlarmDefinition(rs);
				}
				System.out.println("Not found");

				return null;
			}
		});

		return alarmDefinition;
	}

	@Override
	public AlarmDefinition getAlarmDefinition(String name) {
		String sql = "SELECT * from alarm_def where name =  \"" + name + "\"";
		AlarmDefinition alarmDefinition = jdbcTemplate.query(sql, new ResultSetExtractor<AlarmDefinition>() {

			@Override
			public AlarmDefinition extractData(ResultSet rs) throws SQLException, DataAccessException {
				System.out.println("Got result");
				if (rs.next()) {
					return resultSet2AlarmDefinition(rs);
				}
				System.out.println("Done");

				return null;
			}
		});

		return alarmDefinition;
	}

	
	public void addAlarmDefinition(AlarmDefinition alarmDefinition) {

		addAlarmDefinition(alarmDefinition.getName(), alarmDefinition.getDescription(), alarmDefinition.getQuery(),
				alarmDefinition.isEnabled(), alarmDefinition.getDatasourceId());


	}

	@Override
	public void modifyAlarmDefinition(AlarmDefinition alarmDefinition) {
		modifyAlarmDefinition(alarmDefinition.getName(), alarmDefinition.getDescription(), alarmDefinition.getQuery(), alarmDefinition.isEnabled(), alarmDefinition.getDatasourceId());
		
	}

	@Override
	public void deleteAlarmDefinition(int id) {
		String sql = "DELETE FROM alarm_def WHERE id=?";
	    jdbcTemplate.update(sql, id);
	}
	
	@Override
	public void deleteAlarmDefinition(String name) {
		String sql = "DELETE FROM alarm_def WHERE and name=?";
	    jdbcTemplate.update(sql, name);

	}


	@Override
	public void addAlarmDefinition(String name, String description, String query, boolean enabled, int datasourceId)  {
		
		
		
		String sql = "insert into alarm_def(name, description, query, enabled,datasource) values (?, ?,?,?,?)";

		jdbcTemplate.update(sql, name, description, query, enabled, datasourceId);
		return;

	}
	@Override
	public int getAlarmId(String name){
		String query="select id  from alarm_def where name='"+name+"' ";
		List<Integer> alarmIdList = jdbcTemplate.queryForList(query,Integer.class);
		int  aId=alarmIdList.get(0);
		return aId;
	}
	@Override
    public int getNotificationId(String name){
		String query="select id  from notification_method where name='"+name+"'";
		List<Integer> notiIdList = jdbcTemplate.queryForList(query,Integer.class);
		int notiId=notiIdList.get(0);
		return notiId;
	}
	@Override
	public void alarmNotification(int alarmId,int notiId){
		String sql = "insert into alarm_notification(alarm_id,notification_id) values (?, ?)";

		jdbcTemplate.update(sql, alarmId,notiId);
		return;
	}

	@Override
	public void modifyAlarmDefinition(String name, String description, String query, boolean enabled,
			int datasourceId) {
		
		String sql = "UPDATE alarm_def SET description=?, query=?, enabled=?, datasource=? WHERE name=?";
		jdbcTemplate.update(sql, name, description,query, enabled,datasourceId,name);

	}
	
	@Override
	public List<String> getNameList() {

		String sql = "SELECT name from alarm_def";
		List<String> names = jdbcTemplate.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowIndex) throws SQLException {
				return rs.getString("name");
			}

		});
		return names;
	}


	private AlarmDefinition resultSet2AlarmDefinition(ResultSet rs) throws SQLException {

		int id = rs.getInt("id");
		String name = rs.getString("name");
		String description = rs.getString("description");
		boolean enabled = rs.getBoolean("enabled");
		String query = rs.getString("query");
		int datasourceId = rs.getInt("datasource");

		return new AlarmDefinition(id, name, description, enabled, query, datasourceId);

	}

	@Override
	public void modifyAlarmDefinition(String name, int id) {
		// TODO Auto-generated method stub
		
	}


	

}
