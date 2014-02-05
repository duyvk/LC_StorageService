package unitn.introsde.storage_service.model;

import unitn.introsde.storage_service.ws.StorageImpl;

public class TestFoodTrack {
	public static void main(String[] args) {
		StorageImpl i = new StorageImpl();
		System.out.println(i.getFoodInforOfFoodTrack(2));
	}
}
