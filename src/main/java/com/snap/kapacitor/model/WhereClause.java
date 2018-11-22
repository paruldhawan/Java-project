package com.snap.kapacitor.model;

public class WhereClause {
	private String tagKey;
	private String operator;
	private String value;

	public WhereClause(String tagKey, String operator, String value) {
		super();
		this.tagKey = tagKey;
		this.operator = operator;
		this.value = value;
	}

	@Override
	public String toString() {

		String quotedValue = "";
		if(operator.equals("!~") || operator.equals("=~")){
			quotedValue = "/" + value + "/";
		}else {
			quotedValue = "\'" + value + "\'";
		}
		return "\"" + tagKey + "\" " + operator + " " + quotedValue;
	}
	
	
}