package demo.store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBean {
	@SerializedName ("StatusCode")
	@Expose
	private int statusCode;
	@SerializedName ("ResponseBody")
	@Expose
	private String responseBodyJson;
	@SerializedName ("ResponseBodyFile")
	@Expose
	private String responseBodyFile;
	
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getResponseBodyJson() {
		return responseBodyJson;
	}

	public void setResponseBodyJson(String responseBodyJson) {
		this.responseBodyJson = responseBodyJson;
	}

	public String getResponseBodyFile() {
		return responseBodyFile;
	}

	public void setResponseBodyFile(String responseBodyFile) {
		this.responseBodyFile = responseBodyFile;
	}


}
