package unitn.introsde.storage_service.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import unitn.introsde.storage_service.DAO.DBHelper;


/**
 * The persistent class for the food database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Food.findAll", query="SELECT f FROM Food f")
	})
@XmlRootElement
@Table(name="food")
public class Food implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int food_id;

	@Column(name="food_calories")
	private int foodCalories;

	@Column(name="food_fat")
	private double foodFat;

	@Lob
	private String food_name;

	@Column(name="food_protein")
	private double foodProtein;

	public Food() {
	}

	public int getFood_id() {
		return this.food_id;
	}

	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}

	public int getFoodCalories() {
		return this.foodCalories;
	}

	public void setFoodCalories(int foodCalories) {
		this.foodCalories = foodCalories;
	}

	public double getFoodFat() {
		return this.foodFat;
	}

	public void setFoodFat(double foodFat) {
		this.foodFat = foodFat;
	}

	public String getFood_name() {
		return this.food_name;
	}

	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}

	public double getFoodProtein() {
		return this.foodProtein;
	}

	public void setFoodProtein(double foodProtein) {
		this.foodProtein = foodProtein;
	}

	
	

    ////////////////////////////////// 
    // CRUD operation for food Model 
      /////////////////////////////////

public static Food getFoodById(Integer id) {
     EntityManager em = DBHelper.instance.createEntityManager();
     Food f = em.find(Food.class, id);
     DBHelper.instance.closeConnections(em);
     return f;
}

public static Food addFood(Food food){
     EntityManager em = DBHelper.instance.createEntityManager();
     EntityTransaction tx = em.getTransaction();

     tx.begin();
     em.persist(food);
     tx.commit();


    DBHelper.instance.closeConnections(em);
    return food;
}
public static boolean removeFood(int id)
{

   EntityManager em = DBHelper.instance.createEntityManager();

   Food f = em.find(Food.class, id);

    if (f == null){
	return false;
       }
      
   EntityTransaction tx = em.getTransaction();

   tx.begin();
   f = em.merge(f);
   em.remove(f);
   tx.commit();

   em.close();

   return true;
}

public static Food updateUser(Food f){
	
	Food food =Food.getFoodById(f.getFood_id());
	
	  food.setFood_name(f.getFood_name());
	  food.setFoodCalories(f.getFoodCalories());
	  food.setFoodFat(f.getFoodFat());
	  food.setFoodProtein(f.getFoodProtein());
	  
	 
	  
	
	  EntityManager em =DBHelper.instance.createEntityManager();
	  EntityTransaction tx = em.getTransaction();

	   tx.begin();
	   food = em.merge(food);
	   tx.commit();

	   em.close();
	
	   return food;
}


public static List<Food> getAll() {
   EntityManager em = DBHelper.instance.createEntityManager();
    List<Food> list = em.createNamedQuery("Food.findAll")
            .getResultList();

    em.close();
    return list;
}
	
}