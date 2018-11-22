package com.snap.kapacitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ResponseCache;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.snap.kapacitor.model.DbRp;

public class KapacitorClient {
	
	private String url;
	private List<DbRp> dbrps;
	
	
	public KapacitorClient(String url){
		this.url = url;
	}
	
	public boolean createTask(String id, TaskType taskType, List<DbRp> dbrps, String tickScript, boolean enabled) throws KapacitorException{
		
		Query query = new Query(id, taskType, dbrps, tickScript,enabled);
		Gson gson = new Gson();
		
		String queryJson = gson.toJson(query);
		String createPath = "/kapacitor/v1/tasks";
		Response response = doPost(createPath,queryJson);
		this.dbrps = dbrps;
		
		System.out.println("Query json is " + queryJson);
		
		if(response.responseCode!=200){
			throw new KapacitorException(response.response);
		}
		return true;
		
	}
	
	public boolean deleteTask(String id) throws KapacitorException{
		
		//#TODO delete non-existent task also returns 204
		String deletePath = "/kapacitor/v1/tasks/"+id;
		Response response = doDelete(deletePath);

		if(response.responseCode != 204){
			throw new KapacitorException(response.response);
		}
		
		return true;
	}
	
	private Response doDelete(String path){
		
		HttpClient client = HttpClientBuilder.create().build();
		path = url+path;
		System.out.println("Path is " + path);
        HttpDelete delete = new HttpDelete(path);
        String result = "";
        try {
                HttpResponse response = client.execute(delete);
                int respCode = response.getStatusLine().getStatusCode();
                System.out.println("Status code is "+  respCode);
                
                return new Response("", respCode);
        } catch (IOException e) {
        	return new Response(e.getMessage(), -1);
        }
        
		
	}
	
	private Response doPost(String path, String data){
		HttpClient client = HttpClientBuilder.create().build();
		path = url+path;
		System.out.println("Path is " + path);
        HttpPost post = new HttpPost(path);
        String result = "";
        try {
                post.setEntity(new StringEntity(data));
                post.addHeader("Content-Type", "application/json");
                HttpResponse response = client.execute(post);
                int respCode = response.getStatusLine().getStatusCode();
                System.out.println("Status code is "+  respCode);
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                                response.getEntity().getContent()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = rd.readLine()) != null) {
                        sb.append(line);
                }
                
                result = sb.toString();
                System.out.println("Response recieved "+ result);
                return new Response(result, respCode);
        } catch (IOException e) {
                e.printStackTrace();
                return new Response(e.getMessage(), -1);
        }
        
	}
	
	class Response{
		String response;
		int responseCode;
		
		public Response(String response, int responseCode) {
			this.response = response;
			this.responseCode = responseCode;
		}
	}

	class Query{
		String id;
		String type;
		List<DbRp> dbrps;
		String script;
		String status;
		
		public Query(String id, TaskType taskType, List<DbRp> dbrps, String tickScript, boolean enabled){
			this.id = id;
			this.type = "";
			if(taskType==TaskType.BATCH){
				this.type = "batch";
			}else{
				this.type = "stream";
			}
			this.script = tickScript;
			this.dbrps = dbrps;

			
			if(enabled){
				status = "enabled";
			}else{
				status = "disabled";
			}
		}
	}
	

	
}
