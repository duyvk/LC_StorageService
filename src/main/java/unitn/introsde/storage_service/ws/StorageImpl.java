package unitn.introsde.storage_service.ws;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.transform.Source;

import unitn.introsde.storage_service.API.fatsecretAPI.FatsecretAPI;
import unitn.introsde.storage_service.model.APIbasedFood;
import unitn.introsde.storage_service.model.Caregiver;
import unitn.introsde.storage_service.model.Externalsource;
import unitn.introsde.storage_service.model.Food;
import unitn.introsde.storage_service.model.Foodtrack;
import unitn.introsde.storage_service.model.Goal;
import unitn.introsde.storage_service.model.Lifestatus;
import unitn.introsde.storage_service.model.Measuredefinition;
import unitn.introsde.storage_service.model.Measurehistory;
import unitn.introsde.storage_service.model.User;
import unitn.introsde.storage_service.utils.CaregiverValueComparator;
import unitn.introsde.storage_service.utils.LetterPairSimilarity;
import unitn.introsde.storage_service.utils.UserValueComparator;
import unitn.introsde.storage_service.utils.Utils;

@WebService (endpointInterface= "unitn.introsde.storage_service.ws.Storage", serviceName="StorageService")
public class StorageImpl implements Storage{
	

	/* -------------------------GoalTracking Service --------------------*/
	@Override
	@WebMethod(operationName = "trackGoalbyUser")
	public List<Measurehistory> trackGoalbyUser(@WebParam(name = "user_id") int user_id,
			@WebParam(name = "goal_id") int goal_id) {
		
		Goal trackedGoal = Goal.getGoalById(goal_id);
		if (trackedGoal == null)
			return new ArrayList<Measurehistory>();
		// check goal belong to user or not
		if (trackedGoal.getUser().getUserId()!= user_id)
			return new ArrayList<Measurehistory>();
		// get list of activity
		List<Measurehistory> hisOfMeas = 
				Measurehistory.getMeaHisByTimeRange(user_id, trackedGoal.getMeasuredefinition().getMeaDef_id(),
						trackedGoal.getGoal_from_date(), trackedGoal.getGoal_to_date());

/*		
		String unit = (trackedGoal.getMeasuredefinition().getMeaDef_unit()!=null)? trackedGoal.getMeasuredefinition().getMeaDef_unit() : " unit ";
		String goalDes = 	"\nGoalID:\t"+trackedGoal.getGoalId() +
						 	"\n  about:\t"+trackedGoal.getMeasuredefinition().getMeaDef_name()+
						 	"\n  expect:\t"+trackedGoal.getGoal_expected_value()+" "+unit+
						 	"\n  type:\t\t"+trackedGoal.getGoal_type()+
						 	"\n  from:\t\t"+Utils.dateToString(trackedGoal.getGoal_from_date())+
						 	"\n  to:\t\t"+Utils.dateToString(trackedGoal.getGoal_to_date())+
						 	"\n\t* * *";
		// check goal type and do statistics
		System.out.println(goalDes);
		System.out.println("Your Progress:");
		
		for (Measurehistory mh : hisOfMeas){
			System.out.println(Utils.timestampToString(mh.getMeaHis_updated_time())
					+ "\t"+ mh.getMeaHis_value()+" "+ unit);
		}*/
		
		return hisOfMeas;
	}
	
	// -------------------------calculator service----------------------------------------
	@Override
	@WebMethod(operationName = "searchUserbyName")
	public List<User> searchUserbyName(
			@WebParam(name = "searchString") String searchString, @WebParam(name ="max") int max) {
		
		List<User> allUsers = User.getAllUser();
		if (allUsers.size()==0)
			return null;

		Map<User,Double> map = new HashMap<User, Double>();
		for (User u : allUsers){
			String nameOfUser = u.getUserFirstName() + " "+ u.getUserLastName();
			System.out.println(nameOfUser);
			map.put(u, new Double(LetterPairSimilarity.compareStrings(searchString.trim(), nameOfUser.trim())));
		}
		
		UserValueComparator vcm = new UserValueComparator(map);
		TreeMap<User, Double> sortedMap = new TreeMap<User, Double>(vcm);
		
		sortedMap.putAll(map);
		
		for ( Entry<User, Double> e : sortedMap.entrySet()){
			System.out.println(e.getKey().getUserFirstName()+" "+e.getKey().getUserLastName());
			System.out.println(e.getValue()+ "\n--------------");
		}
		
		return new ArrayList<User>(sortedMap.keySet()).subList(0, max) ;
				
	}
	
	@Override
	@WebMethod(operationName = "searchCareGiverbyName")
	public List<Caregiver> searchCaregiverbyName(
			@WebParam(name = "searchString") String searchString,
			@WebParam(name = "max") int max) {

		List<Caregiver> allCaregiver = Caregiver.getAll();
		if (allCaregiver.size()==0)
			return null;

		Map<Caregiver,Double> map = new HashMap<Caregiver, Double>();
		for (Caregiver cg : allCaregiver){
			String nameOfCaregiver = cg.getCgFirstName() + " "+ cg.getCgLastName();
			map.put(cg, new Double(LetterPairSimilarity.compareStrings(searchString.trim(), nameOfCaregiver.trim())));
		}
		
		CaregiverValueComparator vcm = new CaregiverValueComparator(map);
		TreeMap<Caregiver, Double> sortedMap = new TreeMap<Caregiver, Double>(vcm);
		
		sortedMap.putAll(map);
		
		return new ArrayList<Caregiver>(sortedMap.keySet()).subList(0, max) ;
	}

	@Override
	@WebMethod(operationName = "searchFatSecretFood")
	public String searchFatSecretFood(
			@WebParam(name = "searchText") String searchText) {
		return FatsecretAPI.searchFatSecretFood(searchText);
	}
	
	// ---------------------user service-----------------------------------------
	@Override
	@WebMethod(operationName = "readUser")
	public User getUserById(@WebParam(name = "user_id") int user_id) {
		return User.getUserById(user_id);
	}

	@Override
	@WebMethod(operationName = "createUSer")
	public int addUser(@WebParam(name = "user") User user) {
		User u = User.addUser(user);
		
		if (u == null)
			return -1;
		
		return u.getUserId();
	}

	@Override
	@WebMethod(operationName = "updateUser")
	public int updateUser(@WebParam(name = "usre") User user) {
		User updatedUser = User.updateUser(user);
		if(updatedUser == null)
			return -1;
		return updatedUser.getUserId();
	}

	@Override
	@WebMethod(operationName = "removeUser")
	public boolean removeUser(@WebParam(name = "user_id") int user_id) {
		return User.removePerson(user_id);
	}
	
	@Override
	@WebMethod(operationName = "getAllUser")
	public List<User> getAllUser() {
		return User.getAllUser();
	}


	// ------------------ goal service ------------------------------------------------
	@Override
	@WebMethod(operationName = "readGoal")
	public Goal getGoalById(@WebParam(name = "goal_id") int goal_id) {
		return Goal.getGoalById(goal_id);
	}

	@Override
	@WebMethod(operationName = "createGoal")
	public int addGoal(@WebParam(name = "goal") Goal goal) {
		Goal g = Goal.addGoal(goal);
		
		if (g == null)
			return -1;
		
		return g.getGoalId();
	}

	@Override
	@WebMethod(operationName = "updateGoal")
	public int updateGoal(@WebParam(name = "goal") Goal goal) {
		Goal g = Goal.updateGoal(goal);
		
		if (g == null)
			return -1;
		
		return g.getGoalId();
	}

	@Override
	@WebMethod(operationName = "removeGoal")
	public boolean removeGoal(@WebParam(name = "goal_id") int goal_id) {
		return Goal.removeGoal(goal_id);
	}
	

	@Override
	@WebMethod(operationName = "removeGoalByCaregiver")
	public boolean removeGoalByCaregiver(@WebParam(name = "cg_id") int cg_id,
			@WebParam(name = "goal_id") int goal_id) {
		return Goal.removeGoalByCaregiver(cg_id, goal_id);
	}


	@Override
	@WebMethod(operationName = "removeGoalByUser")
	public boolean removeGoalByUser(@WebParam(name = "user_id") int user_id,
			@WebParam(name = "goal_id") int goal_id) {
		return Goal.removeGoalByUser(user_id, goal_id);
	}

	@Override
	@WebMethod(operationName = "updateGoalByUser")
	public int updateGoalByUser(@WebParam(name = "user_id") int user_id,
			@WebParam(name = "goal") Goal goal) {
		Goal savedGoal =Goal.getGoalById(goal.getGoalId());
		if (savedGoal == null)
			return -1;
		if (savedGoal.getUser().getUserId()!= user_id)
			return -1;
		
		return Goal.updateGoal(goal).getGoalId();
	}

	@Override
	@WebMethod(operationName = "updateGoalByCaregiver")
	public int updateGoalByCaregiver(@WebParam(name = "cg_id") int cg_id,
			@WebParam(name = "goal") Goal goal) {
		Goal savedGoal =Goal.getGoalById(goal.getGoalId());
		if (savedGoal == null)
			return -1;
		if (savedGoal.getCaregiver().getCgId()!= cg_id)
			return -1;
		
		return Goal.updateGoal(goal).getGoalId();
	}

	
	
	// ------------------ caregiver service ------------------------------------------------
	@Override
	@WebMethod(operationName = "readCaregiver")
	public Caregiver getCaregiverById(
			@WebParam(name = "cg_id") int cg_id) {
		return Caregiver.getCaregiverById(cg_id);
	}

	@Override
	@WebMethod(operationName = "createCaregiver")
	public int addCaregiver(@WebParam(name = "caregiver") Caregiver caregiver) {
		Caregiver cg = Caregiver.addCaregiver(caregiver);
		if(cg == null)
			return -1;
		return cg.getCgId();
	}

	@Override
	@WebMethod(operationName = "updateCaregiver")
	public int updateCaregiver(@WebParam(name = "caregiver") Caregiver caregiver) {
		Caregiver cg = Caregiver.updatecaregiver(caregiver);
		if(cg == null)
			return -1;
		return cg.getCgId();
	}

	@Override
	@WebMethod(operationName = "removeCaregiver")
	public boolean removeCaregiver(
			@WebParam(name = "cg_id") int cg_id) {
		return Caregiver.removeCaregiver(cg_id);
	}
	
	@Override
	@WebMethod(operationName = "getGoalsbyUserId")
	public List<Goal> getGoalsbyUserId(@WebParam(name = "user_id") int user_id) {
		return Goal.getGoalsByUserId(user_id);
	}

	@Override
	@WebMethod(operationName = "getGoalsbyCaregiverId")
	public List<Goal> getGoalsbyCaregiverId(@WebParam(name = "cg_id") int cg_id) {
		return Goal.getGoalsByCaregiverId(cg_id);
	}

	
	// ------------------ Measure definition service ------------------------------------------
	@Override
	@WebMethod(operationName = "readMeaDef")
	public Measuredefinition getMeaDefById(
			@WebParam(name = "meaDef_id") int meaDef_id) {
		return Measuredefinition.getMeasureDefById(meaDef_id);
	}

	@Override
	@WebMethod(operationName = "createMeaDef")
	public int addMeaDef(@WebParam(name = "meaDef") Measuredefinition meaDef) {
		Measuredefinition mdf = Measuredefinition.addmeasuredefinition(meaDef);
		if(mdf == null)
			return -1;
		return mdf.getMeaDef_id();
	}

	@Override
	@WebMethod(operationName = "updateMeaDef")
	public int updateMeaDef(@WebParam(name = "meaDef") Measuredefinition meaDef) {
		Measuredefinition mdf = Measuredefinition.updatemeasuredefinition(meaDef);
		if(mdf == null)
			return -1;
		return mdf.getMeaDef_id();
	}

	@Override
	@WebMethod(operationName = "removeMeaDef")
	public boolean removeMeaDef(@WebParam(name = "meaDef_id") int meaDef_id) {
		return Measuredefinition.removemeasuredefinition(meaDef_id);
	}

	/*---------------------------MeasureHistory Services--------------------------*/
	
	@Override
	@WebMethod(operationName = "getMeaHisByTimeRange")
	public List<Measurehistory> getMeaHisByTimeRange(
			@WebParam(name = "user_id") int user_id,
			@WebParam(name = "meaDef_id") int meaDef_id,
			@WebParam(name = "fromDate") Date fromDate,
			@WebParam(name = "toDate") Date toDate) {
		
		return Measurehistory.getMeaHisByTimeRange(user_id, meaDef_id, fromDate, toDate);
	}

	/*---------------------------LifeStatus Services--------------------------*/
	@Override
	public String addHealthStatus(@WebParam(name="lifestatus")Lifestatus lifestatus) {
		// TODO Auto-generated method stub
		
		Lifestatus lf=null;
		 Measurehistory mss=null;
		if(lifestatus.getMeasuredefinition().getMeaDef_type().equalsIgnoreCase("LifeStatus"))
		
		{   lf=lifestatus.addLifestatus(lifestatus);
		
		  
			   
			   Measurehistory ms=new Measurehistory();
			   
			   ms.setUser(lifestatus.getUser());
			   ms.setMeasuredefinition(lifestatus.getMeasuredefinition());
			   ms.setMeaHis_updated_time(lifestatus.getLifeStatus_update_time());
			   ms.setMeaHis_value(lifestatus.getLifeStatus_value());
			   ms.setMeaHis_calories(0.0);
			   
			    mss=Measurehistory.addmeasurehistory(ms);
			   
		}
		
		if(lf==null)
			return "error !";
		else
		
		return ""+lf.getLifeStatus_id()+" :"+mss.getMeaHis_id();
	}


	@Override
	public int addActvity(Lifestatus lifestatus) {
		// TODO Auto-generated method stub
		
		
		 Measurehistory mss=null;
		if(lifestatus.getMeasuredefinition().getMeaDef_type().equalsIgnoreCase("Activity"))
		
		{
			 Measurehistory ms=new Measurehistory();
			   
			   ms.setUser(lifestatus.getUser());
			   ms.setMeasuredefinition(lifestatus.getMeasuredefinition());
			   ms.setMeaHis_updated_time(lifestatus.getLifeStatus_update_time());
			   ms.setMeaHis_value(lifestatus.getLifeStatus_value());
			   ms.setMeaHis_calories(0.0);
			   
			    mss=Measurehistory.addmeasurehistory(ms);
			
		}
		return mss.getMeaHis_id();
	}


	@Override
	public String updateLifeStatus(int ls_id,double value) {
		// TODO Auto-generated method stub

		Lifestatus ls=
		Lifestatus.getLifeStatusById(1201);
		
		if (ls == null)
			return null;
		
		Measurehistory ms=new Measurehistory();
		   
		ms.setUser(ls.getUser());
		ms.setMeasuredefinition(ls.getMeasuredefinition());
		ms.setMeaHis_updated_time(ls.getLifeStatus_update_time());
		ms.setMeaHis_value(ls.getLifeStatus_value());
		ms.setMeaHis_calories(0.0);
		Measurehistory.addmeasurehistory(ms);

		ls.setUser(ls.getUser());
		ls.setMeasuredefinition(ls.getMeasuredefinition());
		ls.setLifeStatus_update_time(ls.getLifeStatus_update_time());
		ls.setLifeStatus_value(10023);
		Lifestatus lfs=Lifestatus.updateLifestatus(ls);
		   
		if(lfs == null)  return "error!";
		
		return ""+lfs.getLifeStatus_id();
	}
	
	
	

	/* -------------------------External (Food) Source Service --------------------*/
	@Override
	@WebMethod(operationName = "readFoodSource")
	public Externalsource getFoodSourceById(
			@WebParam(name = "foodSource_id") int foodSource_id) {
		return Externalsource.getFoodSourceById(foodSource_id);
	}

	@Override
	@WebMethod(operationName = "createFoodSource")
	public int addFoodSource(
			@WebParam(name = "foodSource") Externalsource foodSource) {
		Externalsource source = Externalsource.addFoodSource(foodSource);
		if(source == null)
			return -1;
		return source.getExSource_id();
	}

	@Override
	@WebMethod(operationName = "updateFoodSource")
	public int updateFoodSource(
			@WebParam(name = "foodSource") Externalsource foodSource) {
		Externalsource source = Externalsource.updateFoodSource(foodSource);
		if(source == null)
			return -1;
		return source.getExSource_id();
	}

	@Override
	@WebMethod(operationName = "removeFoodSource")
	public boolean removeFoodSource(
			@WebParam(name = "foodSource_id") int foodSource_id) {
		return Externalsource.removeFoodSource(foodSource_id);
	}
/* -------------------------Local Food Service (Food stored in local database)--------------------*/
	@Override
	@WebMethod(operationName = "readLocalFood")
	public Food getLocalFoodById(@WebParam(name = "localFood_id") int food_id) {
		return Food.getFoodById(food_id);
	}

	@Override
	@WebMethod(operationName = "createLocalFood")
	public int addLocalFood(@WebParam(name = "localFood") Food food) {
		Food f = Food.addFood(food);
		if (f == null)
			return -1;
		return f.getFood_id();
	}

	@Override
	@WebMethod(operationName = "updateLocalFood")
	public int updateLocalFood(@WebParam(name = "localFood") Food food) {
		Food f = Food.updateFood(food);
		if (f==null)
			return -1;
		return f.getFood_id();
	}

	@Override
	@WebMethod(operationName = "removeLocalFood")
	public boolean removeLocalFood(@WebParam(name = "localFood_id") int food_id) {
		return Food.removeFood(food_id);
	}

	@Override
	@WebMethod(operationName = "getLocalFoodsbyUserId")
	public List<Food> getLocalFoodsByUserId(
			@WebParam(name = "user_id") int user_id) {
		return Food.getLocalFoodsByUserId(user_id);
	}

	/* -------------------------Local Foodtrack Service--------------------*/

	@Override
	@WebMethod(operationName = "readFoodTrack")
	public Foodtrack readFoodTrack(
			@WebParam(name = "foodTrack_id") int foodTrack_id) {
		return Foodtrack.getfoodtrackbyid(foodTrack_id);
	}

	@Override
	@WebMethod(operationName = "createFoodTrack")
	public int addFoodTrack(@WebParam(name = "foodTrack") Foodtrack foodTrack) {
		Foodtrack ft = Foodtrack.addfooFoodtrack(foodTrack);
		if (ft==null)
			return -1;
		return ft.getFoodtrackId();
	}

	@Override
	@WebMethod(operationName = "updateFoodTrack")
	public int updateFoodTrack(@WebParam(name = "foodTrack") Foodtrack foodTrack) {
		Foodtrack ft = Foodtrack.updateFoodTrack(foodTrack);
		if(ft==null)
			return -1;
		return ft.getFoodtrackId();
	}

	@Override
	@WebMethod(operationName = "removeFoodTrack")
	public boolean removeFoodTrack(
			@WebParam(name = "foodTrack_id") int foodTrack_id) {
		return Foodtrack.removefoodtrack(foodTrack_id);
	}

	@Override
	@WebMethod(operationName = "getFoodTracksByUserId")
	public List<Foodtrack> getFoodTracksByUserId(
			@WebParam(name = "user_id") int user_id) {
		return Foodtrack.getFoodTracksByUserId(user_id);
	}

	@Override
	@WebMethod(operationName = "getFoodTrackOfUserByTimeRange")
	public List<Foodtrack> getFoodTrackOfUserByTimeRange(
			@WebParam(name = "user_id") int user_id,
			@WebParam(name = "fromDate") Date fromDate,
			@WebParam(name = "toDate") Date toDate) {
		return Foodtrack.getFoodTrackOfUserByTimeRange(user_id, fromDate, toDate);
	}

	@Override
	@WebMethod(operationName = "getFoodInforOfFoodTrack")
	public String getFoodInforOfFoodTrack(
			@WebParam(name = "foodTrack_id") int foodTrack_id) {
		Foodtrack ft = Foodtrack.getfoodtrackbyid(foodTrack_id);
		if (ft == null)
			return "";
		
		String result = "";
		Externalsource foodSource = ft.getExternalsource();
		if(foodSource==null)
			return "";
		String source = foodSource.getExsourceName();
		int food_id = ft.getFoodtrackFoodId();
		if(source.equalsIgnoreCase("local")){
			Food food = Food.getFoodById(ft.getFoodtrackFoodId());
			
			if(food == null) return "";
			result+= "1.Food_id"+":\t"+food.getFood_id()+"\n";
			result+= "2.Food_name"+":\t"+food.getFood_name()+"\n";
			result+= "3.Food_calories"+":\t"+food.getFoodCalories()+"\n";
			if(food.getFoodFat()!=0)
				result+= "4.Food_fat"+":\t"+food.getFoodFat()+"\n";
			if(food.getFoodFat()!=0)
				result+= "5.Food_protein"+":\t"+food.getFoodProtein()+"\n";
			
		}else if (source.equalsIgnoreCase("fatsecret food")){
			Map<String, String> foodInfoMap = FatsecretAPI.getFoodInfo(String.valueOf(food_id));
			for(String key: foodInfoMap.keySet())
				result+= key+":\t"+foodInfoMap.get(key)+"\n";
		}
		return result;
	}

	@Override
	@WebMethod(operationName = "getFoodCaloriesOfFoodTrack")
	public int getFoodCaloriesOfFoodTrack(
			@WebParam(name = "foodTrack_id") int foodTrack_id) {
		Foodtrack ft = Foodtrack.getfoodtrackbyid(foodTrack_id);
		if (ft == null)
			return 0;
		
		Externalsource foodSource = ft.getExternalsource();
		if(foodSource==null)
			return 0;
		String source = foodSource.getExsourceName();
		int food_id = ft.getFoodtrackFoodId();
		if(source.equalsIgnoreCase("local")){
			Food food = Food.getFoodById(ft.getFoodtrackFoodId());
			if(food==null) return 0;
			return food.getFoodCalories();
			
		}else if (source.equalsIgnoreCase("fatsecret food")){
			Map<String, String> foodInfoMap = FatsecretAPI.getFoodInfo(String.valueOf(food_id));
			if(foodInfoMap.containsKey("4.Food_calories"))
				return(Integer.parseInt((foodInfoMap.get("4.Food_calories").toString().trim())));
		}
		return 0;
	}
	public static void main(String[] args) {
		StorageImpl i = new StorageImpl();
		System.out.println(i.getFoodCaloriesOfFoodTrack(1));
	}
}
