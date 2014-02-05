package unitn.introsde.storage_service.utils;

import java.util.Comparator;
import java.util.Map;

import unitn.introsde.storage_service.model.Caregiver;

public class CaregiverValueComparator implements Comparator<Caregiver> {
	Map<Caregiver, Double> base;
	public CaregiverValueComparator(Map<Caregiver, Double>base ) {
		this.base = base;
	}
	@Override
	public int compare(Caregiver arg0, Caregiver arg1) {
		if(base.get(arg0) >= base.get(arg1))
			return -1;		
		return 1;
	}

}
