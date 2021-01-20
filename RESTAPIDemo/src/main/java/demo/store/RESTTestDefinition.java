package demo.store;

import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.core.UriBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import demo.FileUtil;
import demo.GSONUtil;

public class RESTTestDefinition {
	@SerializedName("EndPoint")
	@Expose
	private String endPoint;
	@SerializedName("request")
	@Expose
	private RequestBean request;

	@SerializedName("ExpectedResponse")
	@Expose
	private ResponseBean expectedResponse;
	private ResponseBean actualResponse;
	private String completeURL;
	private String inputFileName;

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public RequestBean getRequest() {
		return request;
	}

	public void setRequest(RequestBean request) {
		this.request = request;
	}

	public ResponseBean getExpectedResponse() {
		return expectedResponse;
	}

	public void setExpectedResponse(ResponseBean expectedResponse) {
		this.expectedResponse = expectedResponse;
	}

	public ResponseBean getActualResponse() {
		return actualResponse;
	}

	public void setActualResponse(ResponseBean actualResponse) {
		this.actualResponse = actualResponse;
	}

	public String getCompleteURL() {
		return completeURL;
	}

	public void setCompleteURL(String completeURL) {
		this.completeURL = completeURL;
	}

	public RESTTestDefinition initializeRESTObj(String testInfoJsonFile) throws IOException {
		this.inputFileName = testInfoJsonFile;
		String baseJsonContent = FileUtil.getFileContent(testInfoJsonFile);
		RESTTestDefinition rtBase = new RESTTestDefinition();
		rtBase = (RESTTestDefinition) GSONUtil.getObjectFromJSON(baseJsonContent, rtBase);
		String completeURL = rtBase.createCmpleteURL();
		rtBase.setCompleteURL(completeURL);
		processRequestFile(rtBase);
		processResponseFile(rtBase);
		return rtBase;

	}

	private String createCmpleteURL() {
		UriBuilder uriBuilder = UriBuilder.fromUri("https://petstore.swagger.io/v2");
		String endPoint = getEndPoint();
		uriBuilder.path(endPoint);
		Properties urlParameters = getRequest().getUrlParameters();
		if (urlParameters != null) {
			for (Object key : urlParameters.keySet()) {
				uriBuilder.queryParam((String) key, (String) urlParameters.get(key));

			}
		}
		String completeUrl = uriBuilder.build().toString();
		return completeUrl;
	}

	private void processResponseFile(RESTTestDefinition rtBase) throws IOException {
		String expectedResponseFileName = rtBase.getExpectedResponse().getResponseBodyFile();
		if (expectedResponseFileName != null) {
			String expAbsFileName = this.inputFileName.substring(0, this.inputFileName.indexOf("InputFolder"))
					+ "//BenchmarkFolder//" + expectedResponseFileName;
			rtBase.getExpectedResponse().setResponseBodyFile(expAbsFileName);
			String responseContent = FileUtil.getFileContent(expAbsFileName);
			rtBase.getExpectedResponse().setResponseBodyJson(responseContent);
		}
	}

	private void processRequestFile(RESTTestDefinition rtBase) throws IOException {
		String requestBodyFile = rtBase.getRequest().getRequestBodyFile();
		if (requestBodyFile != null) {
			String reqAbsFile = this.inputFileName.substring(0, this.inputFileName.lastIndexOf("/") + 1)
					+ requestBodyFile;
			rtBase.getRequest().setRequestBodyFile(reqAbsFile);
			String reqContent = FileUtil.getFileContent(reqAbsFile);
			rtBase.getRequest().setRequestBodyJSON(reqContent);
		}
	}
}
