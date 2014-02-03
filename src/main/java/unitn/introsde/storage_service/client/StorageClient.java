package unitn.introsde.storage_service.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import unitn.introsde.storage_service.model.User;
import unitn.introsde.storage_service.ws.Storage;

public class StorageClient {

	static URL url;

	public static void main(String[] args) {
		try {
			url = new URL ("http://localhost:5900/ws/storage?wsdl");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		QName qname = new QName("http://ws.storage_service.introsde.unitn/", "StorageService");

		Service service = Service.create(url,qname);

		Storage storage = service.getPort(Storage.class);
		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// test your method here
		
		// --- test get user by id
		User u = storage.getUserById(1);
		System.out.println(u.getUserEmail());
		
		// --- test createUser
		User newUser = new User();
		newUser.setUserFirstName("testnewuserwithonlyfirstname");
		try {
			newUser.setUserBirthDate(format.parse("1989-01-01"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newUser.setUserEmail("abc@anc");
		newUser.setUserGender("male");
		newUser.setUserLastName("lastname");
		//System.out.println(storage.addUser(newUser));
		
		// -- test update user
		User updatedUser = new User();
		updatedUser.setUserId(52);
		updatedUser.setUserEmail("duyvk142@gmail.com");
		System.out.println(updatedUser.getUserEmail());
		System.out.println(storage.updateUser(updatedUser));
		System.out.println(storage.getUserById(updatedUser.getUserId()).getUserEmail());
		
	}
}
