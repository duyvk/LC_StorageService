package unitn.introsde.storage_service.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.persistence.GeneratedValue;

import unitn.introsde.storage_service.model.Caregiver;
import unitn.introsde.storage_service.model.Goal;
import unitn.introsde.storage_service.model.Measuredefinition;
import unitn.introsde.storage_service.model.User;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public interface Storage {

	/*---------------------------User Services--------------------------*/
	@WebMethod(operationName = "readUser")
	public User getUserById(@WebParam(name = "user_id") int user_id);

	@WebMethod(operationName = "createUSer")
	public int addUser(@WebParam(name = "user") User user);

	@WebMethod(operationName = "updateUser")
	public int updateUser(@WebParam(name = "user") User user);

	@WebMethod(operationName = "removeUser")
	public boolean removeUser(@WebParam(name = "user_id") int user_id);

	/*---------------------------Caregiver Services--------------------------*/
	@WebMethod(operationName = "readCaregiver")
	public Caregiver getCaregiverById(@WebParam(name = "caregiver_id") int caregiver_id);

	@WebMethod(operationName = "createCaregiver")
	public int addCaregiver(@WebParam(name = "caregiver") Caregiver caregiver);

	@WebMethod(operationName = "updateCaregiver")
	public int updateCaregiver(@WebParam(name = "caregiver") Caregiver caregiver);

	@WebMethod(operationName = "removeCaregiver")
	public boolean removeCaregiver(@WebParam(name = "caregiver_id") int caregiver_id);
	
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
	
	
}
