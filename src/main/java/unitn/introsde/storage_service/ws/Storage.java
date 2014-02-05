package unitn.introsde.storage_service.ws;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.persistence.GeneratedValue;

import unitn.introsde.storage_service.model.Caregiver;
import unitn.introsde.storage_service.model.Goal;
import unitn.introsde.storage_service.model.Lifestatus;
import unitn.introsde.storage_service.model.Measuredefinition;
import unitn.introsde.storage_service.model.Measurehistory;
import unitn.introsde.storage_service.model.User;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public interface Storage {
	
	/* -------------------------Calculator services ---------------------*/
	
	@WebMethod(operationName = "searchUserbyName")
	public List<User> searchUserbyName(@WebParam(name="searchString") String searchString, @WebParam(name ="max") int max);

	/*---------------------------User Services--------------------------*/
	@WebMethod(operationName = "readUser")
	public User getUserById(@WebParam(name = "user_id") int user_id);

	@WebMethod(operationName = "createUSer")
	public int addUser(@WebParam(name = "user") User user);

	@WebMethod(operationName = "updateUser")
	public int updateUser(@WebParam(name = "user") User user);

	@WebMethod(operationName = "removeUser")
	public boolean removeUser(@WebParam(name = "user_id") int user_id);
	
	@WebMethod(operationName = "getAllUser")
	public List<User> getAllUser();

	/*---------------------------Caregiver Services--------------------------*/
	@WebMethod(operationName = "readCaregiver")
	public Caregiver getCaregiverById(@WebParam(name = "caregiver_id") int cg_id);

	@WebMethod(operationName = "createCaregiver")
	public int addCaregiver(@WebParam(name = "caregiver") Caregiver caregiver);

	@WebMethod(operationName = "updateCaregiver")
	public int updateCaregiver(@WebParam(name = "caregiver") Caregiver caregiver);

	@WebMethod(operationName = "removeCaregiver")
	public boolean removeCaregiver(@WebParam(name = "caregiver_id") int cg_id);
	
	/*---------------------------MeasureDefinition Services--------------------------*/
	@WebMethod(operationName = "readMeaDef")
	public Measuredefinition getMeaDefById(@WebParam(name = "meaDef_id") int meaDef_id);

	@WebMethod(operationName = "createMeaDef")
	public int addMeaDef(@WebParam(name = "meaDef") Measuredefinition meaDef);

	@WebMethod(operationName = "updateMeaDef")
	public int updateMeaDef(@WebParam(name = "meaDef") Measuredefinition meaDef);

	@WebMethod(operationName = "removeMeaDef")
	public boolean removeMeaDef(@WebParam(name = "meaDef_id") int meaDef_id);

	/*---------------------------Goal Services--------------------------*/
	@WebMethod(operationName = "readGoal")
	public Goal getGoalById(@WebParam(name = "goal_id") int goal_id);

	@WebMethod(operationName = "createGoal")
	public int addGoal(@WebParam(name = "goal") Goal goal);

	@WebMethod(operationName = "updateGoal")
	public int updateGoal(@WebParam(name = "goal") Goal goal);

	@WebMethod(operationName = "removeGoal")
	public boolean removeGoal(@WebParam(name = "goal_id") int goal_id);
	
	@WebMethod(operationName = "getGoalsbyUserId")
	public List<Goal> getGoalsbyUserId(@WebParam(name = "user_id") int user_id);
	
	@WebMethod(operationName = "getGoalsbyCaregiverId")
	public List<Goal> getGoalsbyCaregiverId(@WebParam(name = "cg_id") int cg_id);
	
	@WebMethod(operationName = "updateGoalByUser")
	public int updateGoalByUser(@WebParam(name = "user_id") int user_id, @WebParam(name="goal") Goal goal);
	
	@WebMethod(operationName = "updateGoalByCaregiver")
	public int updateGoalByCaregiver(@WebParam(name = "cg_id") int cg_id, @WebParam(name="goal") Goal goal);
	
	@WebMethod(operationName="removeGoalByCaregiver")
	public boolean removeGoalByCaregiver(@WebParam(name="cg_id") int cg_id, @WebParam(name="goal_id") int goal_id);
	
	@WebMethod(operationName="removeGoalByUser")
	public boolean removeGoalByUser(@WebParam(name="user_id") int user_id, @WebParam(name="goal_id") int goal_id);
	
	
	/*---------------------------MeasureHistory Services--------------------------*/
	@WebMethod(operationName="getMeaHisByTimeRange")
	public List<Measurehistory> getMeaHisByTimeRange (@WebParam(name="user_id") int user_id,
			@WebParam (name="meaDef_id") int meaDef_id, @WebParam (name="fromDate") Date fromDate,
			@WebParam (name="toDate") Date toDate);
	/*---------------------------LifeStatus Services--------------------------*/
	
	@WebMethod (operationName="createLifeStatus")
	public String addHealthStatus(@WebParam(name="lifeStatus") Lifestatus lifestatus);
		
	@WebMethod (operationName="createActivity")
	public int addActvity(@WebParam(name="lifeStatus") Lifestatus lifestatus);
	
	@WebMethod (operationName="updateLifeStatus")
	public String updateLifeStatus(@WebParam(name="lifeStatus") int ls_id,@WebParam(name="value") double value);
	
	@WebMethod(operationName="readLifeStatus")
	public Lifestatus readLifeStatus(@WebParam(name="lifeStatus") int ls_id);
}
