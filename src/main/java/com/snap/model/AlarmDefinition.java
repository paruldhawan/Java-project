package com.snap.model;

import org.springframework.stereotype.Component;

/**
 * @author ruchi
 *
 */
@Component
public class AlarmDefinition {

	private int id;
	private String name;
	private String description;
	private boolean enabled;
	private String query;
	private int datasourceId;
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public String getQuery() {
		return query;
	}


	public void setQuery(String query) {
		this.query = query;
	}

	public AlarmDefinition() {
	}

	public AlarmDefinition(int id, String name, String description, boolean enabled, String query, int datasourceId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.enabled = enabled;
		this.query = query;
		this.datasourceId = datasourceId;
	}


	@Override
	public String toString() {
		return "AlarmDefinition [id=" + id + ", name=" + name + ", description=" + description + ", enabled=" + enabled
				+ ", query=" + query + "]";
	}


	public int getDatasourceId() {
		return datasourceId;
	}


	public void setDatasourceId(int datasourceId) {
		this.datasourceId = datasourceId;
	}

	

}
