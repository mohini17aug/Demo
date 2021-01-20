package demo;

import java.io.IOException;

import org.json.JSONObject;

import demo.store.RESTTestDefinition;

public class Demo {

	public static void main(String[] args) {
		LoggerUtil.initialize();
		String inputFolderPath = System.getProperty("user.dir")+"/src/test/java/demo/InputFolder/";
		try {
			
			LoggerUtil.startTestCase("GETPetByStatus","Getting all the available pets");
			RESTTestDefinition restTestDef = new RESTTestDefinition();
				restTestDef = restTestDef.initializeRESTObj(inputFolderPath+ "GetPetByStatus.json");
			RESTACTION restAction = new RESTACTION(restTestDef);
			restAction.get();
			RESTValidation restValidation = new RESTValidation(restTestDef);
			restValidation.validateResponseCode();
			restValidation.validateResponseBodyByIgnoringField("id");
			LoggerUtil.endTestCase();
			
			
			LoggerUtil.startTestCase("POSTAPet","Posting a pet");
			restTestDef = restTestDef.initializeRESTObj(inputFolderPath+ "CreatePet.json");
			restAction = new RESTACTION(restTestDef);
			restAction.post();
			 restValidation = new RESTValidation(restTestDef);
			restValidation.validateResponseCode();
			restValidation.validateResponseBodyByIgnoringField("id");
			LoggerUtil.endTestCase();
			
			LoggerUtil.startTestCase("UpdateAPet","Updating the posted");
			String createdPetRespnse = restTestDef.getActualResponse().getResponseBodyJson();
			JSONObject json = new JSONObject(createdPetRespnse);
			Object id = json.get("id");
			restTestDef = restTestDef.initializeRESTObj(inputFolderPath+ "UpdatePet.json");
			restTestDef.setCompleteURL(restTestDef.getCompleteURL()+"/"+id);
			restAction = new RESTACTION(restTestDef);
			restAction.post();
			 restValidation = new RESTValidation(restTestDef);
			restValidation.validateResponseCode();
			restValidation.validateResponseBodyByIgnoringField("message");
			LoggerUtil.endTestCase();
			
		
			LoggerUtil.startTestCase("DeleteAPet","Deleting the created PET");
			restAction.delete();
			 restValidation = new RESTValidation(restTestDef);
			restValidation.validateResponseCode();
			LoggerUtil.endTestCase();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			LoggerUtil.close();
		}
		
	}

}
