package unitn.introsde.storage_service.endpoint;

import javax.xml.ws.Endpoint;

import unitn.introsde.storage_service.ws.StorageImpl;


// TODO: Auto-generated Javadoc
/**
 * The Class PeoplePublisher.
 */
public class StoragePublisher {
	
	/** The server url. */
	public static String SERVER_URL = "http://localhost";
	
	/** The port. */
	public static String PORT = "5900";
	
	/** The base url. */
	public static String BASE_URL = "/ws/storage";
	
	/**
	 * Gets the endpoint url.
	 *
	 * @return the endpoint url
	 */
	public static String getEndpointURL() {
		return SERVER_URL+":"+PORT+BASE_URL;
	}
 
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		String endpointUrl = getEndpointURL();
		System.out.println("Starting Storage Service...");
		System.out.println("--> Published at = "+endpointUrl);
		Endpoint.publish(endpointUrl, new StorageImpl());
    }
}

