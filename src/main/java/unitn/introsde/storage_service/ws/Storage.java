package unitn.introsde.storage_service.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import unitn.introsde.storage_service.model.User;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public interface Storage {
	@WebMethod(operationName = "readUser")
	public User getUserById(@WebParam(name="user_id") int user_id);
	
	@WebMethod(operationName= "createUSer")
	public int addUser(@WebParam(name="user") User user);
	
	@WebMethod(operationName = "updateUser")
	public int updateUser(@WebParam(name="user") User user);
	
	@WebMethod(operationName = "removeUser")
	public int removeUser (@WebParam(name="user_id") int user_id);

}
