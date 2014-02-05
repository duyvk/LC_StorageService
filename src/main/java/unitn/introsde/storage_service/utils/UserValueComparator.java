package unitn.introsde.storage_service.utils;

import java.util.Comparator;
import java.util.Map;

import unitn.introsde.storage_service.model.User;

public class UserValueComparator implements Comparator<User> {
	Map<User, Double> base;
	public UserValueComparator(Map<User, Double>base ) {
		this.base = base;
	}
	@Override
	public int compare(User arg0, User arg1) {
		if(base.get(arg0) >= base.get(arg1))
			return -1;		
		return 1;
	}

}
