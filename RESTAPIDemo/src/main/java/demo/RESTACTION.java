package demo;

import java.util.Properties;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import demo.store.RESTTestDefinition;
import demo.store.ResponseBean;

public class RESTACTION {

	public String HTTP_GET = "GET";
	public String HTTP_DELETE = "DELETE";
	public String HTTP_POST = "POST";
	public static final String CONTENT_TYPE = "Content-Type";

	private RESTTestDefinition testInfoObj = null;
	private ResponseBean actualResponseObj = new ResponseBean();
	static Client client;

	public RESTACTION(RESTTestDefinition rtDef) {
		if (client == null) {
			client = Client.create();
		}
		this.testInfoObj = rtDef;
	}

	private Builder jerseyClientBuilder() {
		WebResource webResource = client.resource(testInfoObj.getCompleteURL());
		Properties requestHTTPHeaders = testInfoObj.getRequest().getHeaders();
		Builder builder = webResource.getRequestBuilder();
		for (Object key : requestHTTPHeaders.keySet()) {
			builder.header((String) key, requestHTTPHeaders.get(key));
		}
		return builder;
	}
	
	private void setContentTypeHeader(Builder builder) {
		String contentType = testInfoObj.getRequest().getHeaders().getProperty(CONTENT_TYPE);
		builder.type(contentType);
		
	}
	
	public void post() {
		ClientResponse response = null;
		Builder builder = jerseyClientBuilder();
		String requestBodyContent = testInfoObj.getRequest().getRequestBodyJSON();
		if(requestBodyContent!=null) {
			setContentTypeHeader(builder);
		}
		
		response =  builder.post(ClientResponse.class, requestBodyContent);
		LoggerUtil.logInfo("POST Operation performed on "+testInfoObj.getCompleteURL());
		
		storeActualResponse(response);
		
	}
	
	public void get() {
		ClientResponse response = null;
		Builder builder = jerseyClientBuilder();
		response =  builder.get(ClientResponse.class);
		LoggerUtil.logInfo("GET Operation performed on "+testInfoObj.getCompleteURL());
		storeActualResponse(response);
		
	}
	
	public void delete() {
		ClientResponse response = null;
		Builder builder = jerseyClientBuilder();
		response =  builder.delete(ClientResponse.class);
		LoggerUtil.logInfo("DELETE Operation performed on "+testInfoObj.getCompleteURL());
		storeActualResponse(response);
		
	}
	
	private void storeActualResponse(ClientResponse response) {
		int responseCode = response.getStatus();
		actualResponseObj.setStatusCode(responseCode);
		
		String responseBody = response.getEntity(String.class);
		actualResponseObj.setResponseBodyJson(responseBody);
		
		testInfoObj.setActualResponse(actualResponseObj);
	}
}
