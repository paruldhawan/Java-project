package com.snap.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.snap.model.Notification;

public class NotificationDaoImpl implements NotificationDao {

	private JdbcTemplate jdbctemplate;
	private DataSource datasource;

	public NotificationDaoImpl(DataSource datasource) {
		jdbctemplate = new JdbcTemplate(datasource);
	}

	public NotificationDaoImpl() {

	}

	public void setDatasource(DataSource datasource) {
		this.jdbctemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<Notification> getNotificationList() {
		// TODO Auto-generated method stub
		String sql = "select * from notification_method ";
		List<Notification> notifications = jdbctemplate.query(sql, new RowMapper<Notification>() {

			@Override
			public Notification mapRow(ResultSet rs, int rowIndex) throws SQLException {
				// TODO Auto-generated method stub
				return resultSet2Notification(rs);
			}

		});
		return notifications;
	}

	@Override
	public void addNotification(Notification notification) {
		// TODO Auto-generated method stub
		addNotification(notification.getId(), notification.getName(), notification.getType(), notification.getAddress(),
				notification.getUsername());

	}

	@Override
	public void addNotification(int id, String name, String type, List<String> address, String username) {
		// TODO Auto-generated method stub
		String addresses = "";
		for(int i=0;i<address.size()-1;i++){
			addresses = addresses  + address.get(i)+ ", ";
		}
		addresses = addresses+address.get(address.size()-1);
		
		String sql = "insert into notification_method( name, type, address, username) values(?,?,?,?)";
		jdbctemplate.update(sql, name, type, addresses, username);
		return;

	}

	private Notification resultSet2Notification(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String name = rs.getString("name");
		String type = rs.getString("type");
		
		String addresses = rs.getString("address");
		List<String> address = Arrays.asList(addresses.split(","));

		
		
		String username = rs.getString("username");
		return new Notification(id, name, type, address, username);

	}

	@Override
	public List<String> getNameList(String username) {
		String sql = "select name from notification_method where username= \"" + username + "\" ";
		List<String> notifications = jdbctemplate.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowIndex) throws SQLException {
				return rs.getString("name");
			}

		});
		return notifications;
	}

	@Override
	public Notification getNotification(String name, String username) {
		String sql = "SELECT * from notification_method where name = \"" + name + "\" and username= \"" + username
				+ "\" ";
		Notification notification = jdbctemplate.query(sql, new ResultSetExtractor<Notification>() {

			@Override
			public Notification extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return resultSet2Notification(rs);
				}

				return null;
			}
		});

		return notification;
	}

	public List<Notification> getNotification(List<String> notificationName, String username) {
		// TODO Auto-generated method stub
		StringBuilder commaSepNotificationNames = new StringBuilder();
		for (int i = 0; i < notificationName.size(); i++) {
			String str = "'" + notificationName.get(i) + "'";
			commaSepNotificationNames.append(str);
			if (i != notificationName.size() - 1) {
				commaSepNotificationNames.append(",");
			}
		}
		System.out.println(commaSepNotificationNames);
		String sql = "SELECT * from notification_method where name in (" + commaSepNotificationNames + ") and username= \""
				+ username + "\" ";

		List<Notification> notifications = jdbctemplate.query(sql, new RowMapper<Notification>() {

			@Override
			public Notification mapRow(ResultSet rs, int rowIndex) throws SQLException {
				// TODO Auto-generated method stub
				return resultSet2Notification(rs);
			}

		});
		return notifications;
	}

	@Override
	public void deleteNotification(int id) {
		// TODO Auto-generated method stub
		String sql="Delete from notification_method where id=?";
		jdbctemplate.update(sql, id);
		
	}

	@Override
	public void modifyNotication(Notification notification) {
		// TODO Auto-generated method stub
		modifyNotification(notification.getId(), notification.getName(), notification.getType(), notification.getAddress());;
		
	}

	@Override
	public void modifyNotification(int id, String name, String type, List<String> address) {
		// TODO Auto-generated method stub
		String addresses = "";
		for(int i=0;i<address.size()-1;i++){
			addresses = addresses  + address.get(i)+ ", ";
		}
		addresses = addresses+address.get(address.size()-1);
		String sql="update notification_method set name=?,type=?,address=? where id=?";
		jdbctemplate.update(sql, id,name, type,addresses);
		
	}

	@Override
	public Notification getNotification(int id) {
		// TODO Auto-generated method stub
		System.out.println("Getting notifications");
		String sql="select * from notification_method where id= "+id;
		Notification notification=jdbctemplate.query(sql, new ResultSetExtractor<Notification>() {
			
			public Notification extractData(ResultSet rs) throws SQLException, DataAccessException {
				System.out.println("Got result");
				if(rs.next()) {
					return resultSet2Notification(rs);
				}
				System.out.println("Done");
				return null;
			}
		});
				
		return notification;
		
	}

}
