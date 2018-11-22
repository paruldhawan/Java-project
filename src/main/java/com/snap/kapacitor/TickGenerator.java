package com.snap.kapacitor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.snap.dao.DatasourceDAO;
import com.snap.kapacitor.model.ComplexQuery;
import com.snap.kapacitor.model.DbRp;
import com.snap.kapacitor.model.Query;
import com.snap.kapacitor.model.SimpleQuery;
import com.snap.model.Datasource;
import com.snap.model.Notification;
import com.snap.properties.KapacitorConfig;

public class TickGenerator {

	public List<Notification> slackNoti;
	public List<Notification> emailNoti;
	Notification notification;

	Map<String, List<String>> fieldsOfMeasurement;
	String query;
	SimpleQuery subQuery;

	Query queryNode;

	DatasourceDAO datasourceDao;
	private String alertName;

	{
		QueryTemplates.loadTemplates();
	}

	public TickGenerator(String alertName, String query, DatasourceDAO datasourceDAO,
			List<Notification> notifications) {
		super();
		this.query = query;
		System.out.println("Notifications are " + notifications);
		this.emailNoti = filterNotification(notifications, "email");
		this.slackNoti = filterNotification(notifications, "slack");
		this.datasourceDao = datasourceDAO;
		this.alertName = alertName;

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Query.class, new QueryDeserializer());

		Gson gson = gsonBuilder.create();
		queryNode = gson.fromJson(query, ComplexQuery.class);

	}

	// #FIXME make small
	private String generateAlertString(String condition) {

		String alertString = QueryTemplates.alertTemplate;
		alertString = alertString.replace("{alertId}", alertName);
		alertString = alertString.replace("{detail}", QueryTemplates.alertDetailTemplate);
		StringBuilder messageBuilder = new StringBuilder();
		List<String> fieldValues = new LinkedList<>();
		queryNode.getAlertMessage("root", "", fieldValues);
		for (int i = 0; i < fieldValues.size(); i++) {

			messageBuilder.append(fieldValues.get(i) + "\n");

		}

		String message = QueryTemplates.alertMessageTemplate;
		message = message.replace("{values}", messageBuilder.toString());

		alertString = alertString.replace("{message}", message);
		alertString = alertString.replace("{lambdaExpr}", condition);

		String alertChannelString = "";
		String emailTo = "";
		String slackTo = "";
		if (emailNoti.size() > 0) {

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < emailNoti.size(); i++) {
				Notification notif = emailNoti.get(i);
				List<String> emailAddresses = notif.getAddress();
				for (int j = 0; j < emailAddresses.size(); j++) {

					sb.append("'" + emailAddresses.get(j) + "'");
					if (i < emailNoti.size() - 1 || j < emailAddresses.size() - 1) {
						sb.append(", ");
					}
				}
			}

			emailTo = sb.toString();
		}

		
		
		if (slackNoti.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < slackNoti.size(); i++) {
				List<String> alertAddresses = slackNoti.get(i).getAddress();
				for(int j=0;j<alertAddresses.size();j++){
					String alertAddress = alertAddresses.get(i);
					sb.append( "\t.slack() \n \t .channel('" + alertAddress + "')");
				}
				
			}
			slackTo = sb.toString();
		} 
		if (emailNoti.size() > 0) {
			alertChannelString += "\n\t.email(" + emailTo + ")";
		}
		
		
		if(slackNoti.size() > 0 ){
			alertChannelString += "\n\t" + slackTo;
		}
		

		return alertString + alertChannelString;

	}

	public String generateTick() {
		String batchQuery = queryNode.getBatchQuery("root", this.datasourceDao);
		String condition = queryNode.getCondition("root", "");
		String alertQuery = generateAlertString(condition);

		return batchQuery + "\n" + alertQuery;
	}

	private List<Notification> filterNotification(List<Notification> notifications, String type) {
		List<Notification> filtered = new LinkedList<Notification>();
		for (Notification notification : notifications) {
			if (notification.getType().toLowerCase().equals(type)) {
				filtered.add(notification);
			}
		}
		return filtered;

	}

	public List<DbRp> getDBRP() {

		Map<String, List<String>> mapping = new HashMap<String, List<String>>();

		// call function
		queryNode.getDsRp(mapping);

		List<DbRp> toReturn = new LinkedList<>();
		for (String ds : mapping.keySet()) {
			String db = datasourceDao.getDatasource(ds).getDatabase();

			for (String rp : mapping.get(ds)) {
				DbRp dbrp = new DbRp(db, rp);
				toReturn.add(dbrp);
			}
		}

		return toReturn;
	}

}
