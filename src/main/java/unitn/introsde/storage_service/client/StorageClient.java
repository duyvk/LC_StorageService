package unitn.introsde.storage_service.client;

import java.net.MalformedURLException;
import java.net.URL;

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
		
		
		// test your method here
		
		// --- test get user by id
		User u = storage.getUserById(1);
		System.out.println(u.getUserEmail());

		



	}
}
