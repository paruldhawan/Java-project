package com.snap.properties;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigProperties {

	public InputStream inputStream;
	public Properties properties;
	public String url;
	public String keyPath;
	public String jssecacertPath;
	public String ldapurl;
	public String ldapjsseCacertpath;
	private Logger logger = LoggerFactory.getLogger(ConfigProperties.class);

	public ConfigProperties() {
		try {
			properties = new Properties();
			String propertyFileName = "config.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName);
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("Property file '" + propertyFileName + "' not found in the classpath");
			}

		} catch (Exception e) {
			logger.error("Property values error", e);
		}

	}

	public String getLdapUrl() {
		return (properties.getProperty("ldap.url"));
	}

	public String getJsscecacertpath() {
		return (properties.getProperty("ldap.jssecacertpath"));
	}
}
