package com.snap.kapacitor;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.snap.kapacitor.model.ComplexQuery;
import com.snap.kapacitor.model.Query;
import com.snap.kapacitor.model.SimpleQuery;

public class QueryDeserializer implements JsonDeserializer<Query> {

	@Override
	public Query deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();

		if (jsonObject.has("type")) {
			String type = jsonObject.get("type").getAsString();
			System.out.println("Type is " + type);
			if (type.equals("simple")) {
				return context.deserialize(json, SimpleQuery.class);
			} else if (type.equals("complex")) {
				return context.deserialize(json, ComplexQuery.class);

			}
			else {
				throw new JsonParseException("Unknown type of query " + type);
			}
		}
		else{
			throw new JsonParseException("Member type not founnd " );
		}

		
	}

}
