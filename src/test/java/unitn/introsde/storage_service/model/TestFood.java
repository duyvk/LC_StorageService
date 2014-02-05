package unitn.introsde.storage_service.model;

import java.util.List;

public class TestFood {
public static void main(String[] args) {
	
	
	
	List<Food> foods = Food.getLocalFoodsByUserId(1);
	for (Food f : foods)
		System.out.println(f.getFood_name());
	
	System.out.println(Food.getFoodById(1));
	
	Food newf = new Food();
	newf.setFood_name("dont know");
	newf.setFoodCalories(100);
	newf.setUser(User.getUserById(1));
	
	System.out.println(Food.addFood(newf).getFood_id());
	newf.setFood_name("not sure");
	System.out.println(Food.updateFood(newf).getFood_id());
	System.out.println(Food.removeFood(1101));
	
}
}
