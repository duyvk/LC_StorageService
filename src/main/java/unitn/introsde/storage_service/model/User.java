package unitn.introsde.storage_service.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import unitn.introsde.storage_service.DAO.DBHelper;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
	})
@XmlRootElement
@Table(name="user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private int userId;

	@Temporal(TemporalType.DATE)
	@Column(name="user_birth_date")
	private Date userBirthDate;

	@Lob
	@Column(name="user_email")
	private String userEmail;

	@Lob
	@Column(name="user_first_name")
	private String userFirstName;

	@Lob
	@Column(name="user_gender")
	private String userGender;

	@Lob
	@Column(name="user_last_name")
	private String userLastName;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getUserBirthDate() {
		return this.userBirthDate;
	}

	public void setUserBirthDate(Date userBirthDate) {
		this.userBirthDate = userBirthDate;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserFirstName() {
		return this.userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserGender() {
		return this.userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserLastName() {
		return this.userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	
    ////////////////////////////////// 
    // CRUD operation for User Model 
      /////////////////////////////////


public static User getUserById(Integer id) {
     EntityManager em = DBHelper.instance.createEntityManager();
     User p = em.find(User.class, id);
     DBHelper.instance.closeConnections(em);
     return p;
}

public static User addUser(User user){
     EntityManager em = DBHelper.instance.createEntityManager();
     EntityTransaction tx = em.getTransaction();

     tx.begin();
     em.persist(user);
     tx.commit();


    DBHelper.instance.closeConnections(em);
    return user;
}
public static boolean removePerson(int id)
{

   EntityManager em = DBHelper.instance.createEntityManager();

    User u = em.find(User.class, id);
    System.out.println(u.getUserEmail());
    if (u == null){
    	return false;
    }
      
   EntityTransaction tx = em.getTransaction();

   tx.begin();
   u = em.merge(u);
   em.remove(u);
   tx.commit();

   em.close();

   return true;
}

public static User updateUser(User u){
	
	User user =User.getUserById(u.getUserId());
	
	  user.setUserFirstName(u.getUserFirstName());
	  user.setUserLastName(u.getUserLastName());
	  user.setUserBirthDate(u.getUserBirthDate());
	  user.setUserEmail(u.getUserEmail());
	  user.setUserGender(u.getUserGender());
	  
	
	  EntityManager em =DBHelper.instance.createEntityManager();
	  EntityTransaction tx = em.getTransaction();

	   tx.begin();
	   user = em.merge(user);
	   tx.commit();

	   em.close();
	
	   return user;
}

public static List<User> getAll() {
   EntityManager em = DBHelper.instance.createEntityManager();
    List<User> list = em.createNamedQuery("User.findAll")
            .getResultList();

    em.close();
    return list;
}

//******************************************************************************

public static List<User> getByBirthdate(String start, String end) {
    EntityManager em = DBHelper.instance.createEntityManager();
    List<User> list = em
                    .createNamedQuery("Person.findByBirthdate")
                    .setParameter("start", start).setParameter("end", end)
                    .getResultList();
    ;
    em.close();
    return list;
}
	
	
}