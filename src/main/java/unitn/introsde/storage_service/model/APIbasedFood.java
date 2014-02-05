package unitn.introsde.storage_service.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import unitn.introsde.storage_service.DAO.DBHelper;

import java.util.Date;
import java.util.List;


@XmlRootElement
public class APIbasedFood implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int food_id;
	private int foodSource;
	private String foodName;
	private int foodCalories;
	private double foodProtein;
	private double foodFat;

	public APIbasedFood() {
	}
	
	public int getFood_id() {
		return food_id;
	}

	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}

	public int getFoodSource() {
		return foodSource;
	}

	public void setFoodSource(int foodSource) {
		this.foodSource = foodSource;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public int getFoodCalories() {
		return foodCalories;
	}

	public void setFoodCalories(int foodCalories) {
		this.foodCalories = foodCalories;
	}

	public double getFoodProtein() {
		return foodProtein;
	}

	public void setFoodProtein(double foodProtein) {
		this.foodProtein = foodProtein;
	}

	public double getFoodFat() {
		return foodFat;
	}

	public void setFoodFat(double foodFat) {
		this.foodFat = foodFat;
	}
	
}