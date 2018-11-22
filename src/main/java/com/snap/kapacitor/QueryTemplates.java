package com.snap.kapacitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryTemplates {

	private static Logger logger = LoggerFactory.getLogger(QueryTemplates.class);
	
	public static String batchTemplate = "var {id} = batch | query(''' SELECT {field_with_ops} as {field}  from {measurement}  {where} ''' ) \n  "
			+ "\t .period({period}) \n" + "\t .groupBy({groupby}) \n" + "\t .every({every}) \n";

	public static String alertTemplate = "root | alert() \n" + "\t .id('{alertId}') \n" + "\t .message('{message}') .detail('{detail}') \n"
			+ "\t .warn(lambda: {lambdaExpr}) \n \t .stateChangesOnly(2h) \n" + "\t .log('/tmp/kapacitor.log') \n";

	public static String joinTemplate = "var {id} = {first-node} | join({nodes})" + "\n\t .as({as}) .tolerance(30s) \n";

	public static String alertMessageTemplate = "{{ .Level }}:  {values} for {{ .Tags }}";
	public static String fieldMessageTemplate = "Value of {field-name} is  {{ index .Fields \"{complete-field-name}\" }} ";

	public static String alertDetailTemplate = "{{ .json }}";

	//Static method
	{
		loadTemplates();
	}
	
	public static void loadTemplates() {
		logger.debug("Loading templates from files");
		alertDetailTemplate = readFileFromResource("alert_detail.html");

		loadTemplateFile("templates.prop");
	}

	private static void loadTemplateFile(String fileName) {
		ClassLoader classLoader = QueryTemplates.class.getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		Properties properties = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			properties = new Properties();
			properties.load(fis);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.toString());
		}

		if (properties == null) {
			logger.warn("Failed to load template file");
			return;

		}
		if (properties.containsKey("batchTemplate")) {
			batchTemplate = properties.getProperty("batchTemplate");
		}
		if (properties.containsKey("alertTemplate")) {
			alertTemplate = properties.getProperty("alertTemplate");
		}

		if (properties.containsKey("alertMessageTemplate")) {
			alertMessageTemplate = properties.getProperty("alertMessageTemplate");
		}
		if (properties.containsKey("fieldMessageTemplate")) {
			fieldMessageTemplate = properties.getProperty("fieldMessageTemplate");
		}
		if (properties.containsKey("joinTemplate")) {
			joinTemplate = properties.getProperty("joinTemplate");
		}

	}

	private static String readFileFromResource(String fileName) {

		StringBuilder result = new StringBuilder("");
		ClassLoader classLoader = QueryTemplates.class.getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();
	}
}
