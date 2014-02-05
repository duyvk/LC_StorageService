package unitn.introsde.storage_service.model;

import java.util.List;

import unitn.introsde.storage_service.utils.Utils;
import unitn.introsde.storage_service.ws.StorageImpl;

public class TestGoal {
	public static void main(String[] args) {
		
		// test getGoalsByUserId(2)
		List<Goal> ugoals = Goal.getGoalsByUserId(2);
		System.out.println(ugoals.size());
		System.out.println(ugoals.get(0).getGoal_expected_value());
		
		// test getGoalsByCaregiverId(2)
		List<Goal> cggoals = Goal.getGoalsByCaregiverId(1);
		System.out.println(cggoals.size());
		System.out.println(ugoals.get(0).getUser().getUserFirstName());
		
	}
}


/*     test trackGoalByUser  in StorageImpl.java
public static void main(String[] args) {
	StorageImpl s = new StorageImpl();
	List<Measurehistory> hisOfMeas = s.trackGoalbyUser(1, 1);
	if (hisOfMeas.size() !=0){
		Goal trackedGoal = s.getGoalById(1);
		String unit = (trackedGoal.getMeasuredefinition().getMeaDef_unit()!=null)? trackedGoal.getMeasuredefinition().getMeaDef_unit() : " unit ";
		String goalDes = 	"\nGoalID:\t"+trackedGoal.getGoalId() +
						 	"\n  about:\t"+trackedGoal.getMeasuredefinition().getMeaDef_name()+
						 	"\n  expect:\t"+trackedGoal.getGoal_expected_value()+" "+unit+
						 	"\n  type:\t\t"+trackedGoal.getGoal_type()+
						 	"\n  from:\t\t"+Utils.dateToString(trackedGoal.getGoal_from_date())+
						 	"\n  to:\t\t"+Utils.dateToString(trackedGoal.getGoal_to_date())+
						 	"\n\t* * *";
		// check goal type and do statistics
		System.out.println(goalDes);
		System.out.println("Your Progress:");
		
		for (Measurehistory mh : hisOfMeas){
			System.out.println(Utils.timestampToString(mh.getMeaHis_updated_time())
					+ "\t"+ mh.getMeaHis_value()+" "+ unit);
			}
	}
}*/