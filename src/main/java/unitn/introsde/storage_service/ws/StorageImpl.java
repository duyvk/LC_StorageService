package unitn.introsde.storage_service.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import unitn.introsde.storage_service.model.User;

@WebService (endpointInterface= "unitn.introsde.storage_service.ws.Storage", serviceName="StorageService")
public class StorageImpl implements Storage{
	
	// user service
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
	public int removeUser(@WebParam(name = "user_id") int user_id) {
		return User.removePerson(user_id);
	}

}
