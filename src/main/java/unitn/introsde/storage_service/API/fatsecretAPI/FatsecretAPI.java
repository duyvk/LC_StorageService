package unitn.introsde.storage_service.API.fatsecretAPI;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import unitn.introsde.storage_service.API.fatsecretAPI.platform.FatSecretAPI;
import unitn.introsde.storage_service.API.fatsecretAPI.platform.FatSecretException;

import com.json.parsers.*;

public class FatsecretAPI {
	public static Map<String, String> getFoodInfo(String food_id) {
		Map<String, String> foodInfo = new TreeMap<String, String>();
		try {
			FatSecretAPI api = new FatSecretAPI("b9d0e5229f0b482f91c68d86c509b560","04dbf50b9bc547729d8d85938dd37d12");
	
			String result=api.getFoodById(food_id); //"3591"
		
			JsonParserFactory factory=JsonParserFactory.getInstance();
			JSONParser parser=factory.newJsonParser();
			Map jsonData=parser.parseJson(result);
	
			Map rootJson=(Map) jsonData.get("food");
			Map addressMap=(Map) rootJson.get("servings");
			List al=(List) addressMap.get("serving");
		 
			String Food_Id=(String) rootJson.get("food_id");
			String Food_Name=(String) rootJson.get("food_name");
			String Food_Type=(String) rootJson.get("food_type");
			String Food_calories=(String) ((Map)al.get(0)).get("calories");
			String Food_protiens=(String) ((Map)al.get(0)).get("protein");
			String Food_fat=(String) ((Map)al.get(0)).get("fat");
			String Food_Measur_Desc=(String) ((Map)al.get(0)).get("measurement_description");
		
			 foodInfo.put("1.Food_id", Food_Id);
			 foodInfo.put("2.Food_name",Food_Name);
			 foodInfo.put("3.Food_type",Food_Type);
			 foodInfo.put("4.Food_calories",Food_calories);
			 foodInfo.put("5.Food_proteins",Food_protiens);
			 foodInfo.put("6.Food_fat",Food_fat);
			 foodInfo.put("7.Food_unit",Food_Measur_Desc);
			 
		} 
		catch (FatSecretException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return foodInfo;
	}

	public static String searchFatSecretFood( String searchString){
		FatSecretAPI api = new FatSecretAPI("b9d0e5229f0b482f91c68d86c509b560","04dbf50b9bc547729d8d85938dd37d12");
		
		try {
			String result = (api.foodsearch(searchString));
			JsonParserFactory factory=JsonParserFactory.getInstance();
			JSONParser parser=factory.newJsonParser();
			Map jsonData=parser.parseJson(result);

			Map rootJson=(Map) jsonData.get("foods");
			List al=(List) rootJson.get("food");
			
			if (al==null)
				return "Not found";
			Iterator<Map> listOfFoods = al.iterator();
			String formattedResult = "No found";
			while (listOfFoods.hasNext()){
				Map food = listOfFoods.next();
				formattedResult+=("\nFoodId:\t"+food.get("food_id"));
				formattedResult+=("\n   Name:\t"+food.get("food_name"));
				formattedResult+=("\n   Description:\t"+food.get("food_description"));
				formattedResult+=("\n-----------------------------");
			}
			return formattedResult;
		} catch (FatSecretException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Not Found";
	}
	public static void main(String[] args) {
		//System.out.println(FatsecretAPI.searchFatSecretFood("orange"));
		Map<String, String> orangeFood = FatsecretAPI.getFoodInfo("35878");
		for (Entry< String, String> e : orangeFood.entrySet()){
			System.out.println(e.getKey()+":\t"+e.getValue());
		}
	}
}
