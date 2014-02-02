package unitn.introsde.storage_service.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import unitn.introsde.storage_service.model.User;

@WebService (endpointInterface= "unitn.introsde.storage_service.ws.Storage", serviceName="StorageService")
public class StorageImpl implements Storage{
	
	// user service
	@Override
	@WebMethod(operationName = "getUserById")
	public User getUserById(@WebParam(name = "user_id") int user_id) {
		return User.getUserById(user_id);
	}

}
