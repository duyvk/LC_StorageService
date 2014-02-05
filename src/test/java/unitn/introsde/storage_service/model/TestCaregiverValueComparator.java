package unitn.introsde.storage_service.model;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import unitn.introsde.storage_service.utils.CaregiverValueComparator;

public class TestCaregiverValueComparator {
	
	public static void main(String[] args) {

		Map<Caregiver, Double> map  = new HashMap<Caregiver, Double>();
		CaregiverValueComparator vcm = new CaregiverValueComparator(map);
		TreeMap<Caregiver, Double> tmap = new TreeMap<Caregiver, Double>(vcm);
		
		Caregiver u1 = new Caregiver();
		u1.setCgId(1);
		Caregiver u2 = new Caregiver();
		u2.setCgId(2);
		Caregiver u3 = new Caregiver();
		u3.setCgId(3);
		
		map.put(u1, 2.0);
		map.put(u2, 1.0);
		map.put(u3, 3.0);
		
		tmap.putAll(map);
		System.out.println(tmap);
	}
}
