package unitn.introsde.storage_service.model;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import unitn.introsde.storage_service.utils.UserValueComparator;

public class TestUserValueComparator {
	
	public static void main(String[] args) {

		Map<User, Double> map  = new HashMap<User, Double>();
		UserValueComparator vcm = new UserValueComparator(map);
		TreeMap<User, Double> tmap = new TreeMap<User, Double>(vcm);
		
		User u1 = new User();
		u1.setUserId(1);
		User u2 = new User();
		u2.setUserId(2);
		User u3 = new User();
		u3.setUserId(3);
		
		map.put(u1, 2.0);
		map.put(u2, 1.0);
		map.put(u3, 3.0);
		
		tmap.putAll(map);
		System.out.println(tmap);
	}
}
