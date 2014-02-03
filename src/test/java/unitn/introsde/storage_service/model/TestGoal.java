package unitn.introsde.storage_service.model;

import java.util.List;

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
