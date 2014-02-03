package unitn.introsde.storage_service.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import unitn.introsde.storage_service.model.Caregiver;
import unitn.introsde.storage_service.model.Goal;
import unitn.introsde.storage_service.model.Measuredefinition;
import unitn.introsde.storage_service.model.User;

@WebService (endpointInterface= "unitn.introsde.storage_service.ws.Storage", serviceName="StorageService")
public class StorageImpl implements Storage{
	
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

	// ------------------ caregiver service ------------------------------------------------
	@Override
	@WebMethod(operationName = "readCaregiver")
	public Caregiver getCaregiverById(
			@WebParam(name = "caregiver_id") int caregiver_id) {
		return Caregiver.getCaregiverById(caregiver_id);
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
			@WebParam(name = "caregiver_id") int caregiver_id) {
		return Caregiver.removeCaregiver(caregiver_id);
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
	
	
	
	

}
