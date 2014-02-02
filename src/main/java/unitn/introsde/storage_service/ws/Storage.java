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
	@WebMethod(operationName = "getUserById")
	public User getUserById(@WebParam(name="user_id") int user_id);

}
