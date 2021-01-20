package demo.store;

import java.util.Properties;

public class RequestBean {
	private Properties headers;
	private Properties resources;
	private Properties urlParameters;
	
	private String requestBodyFile;
	private String requestBodyJSON;
	public Properties getHeaders() {
		return headers;
	}
	public void setHeaders(Properties headers) {
		this.headers = headers;
	}
	public Properties getResources() {
		return resources;
	}
	public void setResources(Properties resources) {
		this.resources = resources;
	}
	public Properties getUrlParameters() {
		return urlParameters;
	}
	public void setUrlParameters(Properties urlParameters) {
		this.urlParameters = urlParameters;
	}
	public String getRequestBodyFile() {
		return requestBodyFile;
	}
	public void setRequestBodyFile(String requestBodyFile) {
		this.requestBodyFile = requestBodyFile;
	}
	public String getRequestBodyJSON() {
		return requestBodyJSON;
	}
	public void setRequestBodyJSON(String requestBodyJSON) {
		this.requestBodyJSON = requestBodyJSON;
	}
	
	
	

}
