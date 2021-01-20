package demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class GSONUtil {
	public static Gson gson= new GsonBuilder().setPrettyPrinting().create();
	
	
	public static Object getObjectFromJSON(String jsonString, Object obj) {
		obj = gson.fromJson(jsonString, obj.getClass());
		return obj;
	}
	
	public static String prettyPrintJson(String uglyJSONString) {
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(uglyJSONString);
		String prettyJSONString= gson.toJson(je);
		return prettyJSONString;
	}
	

}
