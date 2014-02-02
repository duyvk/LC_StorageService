package unitn.introsde.storage_service.BackUpofREST;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.sound.midi.Soundbank;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import unitn.introsde.storage_service.model.Lifestatus;
import unitn.introsde.storage_service.model.Measurehistory;
import unitn.introsde.storage_service.model.User;
import unitn.introsde.storage_service.DAO.DBHelper;


@Stateless
@LocalBean
@Path("/user")
public class resources {

	/** The uri info. */
	@Context
	UriInfo uriInfo;
	
	/** The request. */
	@Context
	Request request;

	/**
	 * Gets the User by id.
	 * Part 1: GET on /person/{id}
	 * @param user_id the id
	 * @return the User by id
	 */
	@Path("{user_id: \\d+}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public User getUserById(@PathParam("user_id") int id){
		EntityManager em = DBHelper.instance.createEntityManager();
		User u = em.find(User.class, id);
		em.close();
		if(u==null)
			u = new User();
		return u;
	}
	/**
	 * Update person.
	 * part 2: PUT on /person/{id}
	 *
	 * @param user_id the id
	 * @param user_first_name the firstname
	 * @param user_last_name the lastname
	 * @param user_email the email
	 * @param user_birth_date the Userbirthday
	 * @param user_gender the usergender
	 * @return the response
	 */
	@Path("{idPerson: \\d+}")
	@PUT
	@Produces({MediaType.TEXT_PLAIN})
	public Response updateUser (@PathParam("user_id") Long id,
									@FormParam("user_first_name") String firstname,
									@FormParam("user_last_name")String lastname,
									@FormParam("user_email") String email,
									@FormParam("user_birth_date") String Userbirthday,
									@FormParam("user_gender") String usergender){
		
		String msg = null;
		
		// no inserted data
		if (firstname == null & lastname == null && email == null
				&& Userbirthday == null && usergender == null)
			msg = "NOT IMPLEMENTED: no data is given: nothing to edit";
		
		EntityManager em = DBHelper.instance.createEntityManager();
		EntityTransaction tx = DBHelper.instance.getTransaction(em);
		
		// no person with id
		User u = em.find(User.class, id);
		if(u==null)
			msg = "NOT IMPLEMENTED: no person with ID: "+id;
		else
			{
				//update
				if(firstname!=null)u.setUserFirstName(firstname);
				if(lastname!=null)u.setUserLastName(lastname);
				if(usergender!=null)u.setUserGender(usergender);
				if(email!=null)u.setUserEmail(email);
				if(Userbirthday!=null){
					if (!Userbirthday.matches("\\d{4}-\\d{2}-\\d{2}")){
						System.out.println("not match");
						msg="NOT IMPLEMENTED: wrong Date format. It should be yyyy-MM-dd";
					}else{
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						Date date;
						try {
							date = formatter.parse(Userbirthday);
							u.setUserBirthDate(date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}	
				}
			}
		if (msg != null){
			return Response.status(Response.Status.BAD_REQUEST).
            entity(msg).
            type(MediaType.TEXT_PLAIN).
            build();
		}
		
		// update to database
		tx.begin();
		em.merge(u);
		tx.commit();
		em.close();
		
		return Response.ok(msg, "text/plain").entity("User with ID: "+id).build();
	}
	
	/**
	 * Removes the person.
	 * part 3: DELETE on /person/{id}
	 *
	 * @param user_id the id
	 * @return the response
	 */
	@Path("{idPerson: \\d+}")
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	public Response removeUser(@PathParam("idUser") Long id) {
		String msg=null;
		
		EntityManager em = DBHelper.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		// get person
		User u = em.find(User.class, id);
		System.out.println(u.getUserEmail());
		// check person
		if(u==null){
			msg = "NOT IMPLEMENTED: no person with ID: "+id;
		}
		if(msg!=null){
			return Response.status(Response.Status.BAD_REQUEST)
						.entity(msg)
						.build();
		}
		tx.begin();
		
		/*// remove all history of that person
		List<Lifestatus> removeUserLS = 
			em.createQuery("select l from Lifestatus l join l.user u " +
				"where u.user_id = :id").setParameter("id", id).getResultList();
		if(removeUserLS!=null && removeUserLS.size()!=0)
			for	(Lifestatus mh : removeUserLS)
				em.remove(mh);*/
		
		// remove person
		em.remove(u);
		
		tx.commit();
		em.close();
		return Response.ok("text/plain").entity("User with id:"+id).build();
	}
	/**
	 * Adds the person.
	 * 
	 * Part 4: POST on /person
	 * @param user_id the id
	 * @param user_first_name the firstname
	 * @param user_last_name the lastname
	 * @param user_email the email
	 * @param user_birth_date the Userbirthday
	 * @param user_gender the usergender
	 * @return the response
	 */
	@POST
	@Produces({MediaType.TEXT_PLAIN})
	public Response addUser (
									@FormParam("user_first_name") String firstname,
									@FormParam("user_last_name")String lastname,
									@FormParam("user_email") String email,
									@FormParam("user_birth_date") String Userbirthday,
									@FormParam("user_gender") String usergender){
		 
		String msg = null;
		
		//no inserted data
		if (firstname == null & lastname == null && email == null
				&& Userbirthday == null && usergender == null)
			msg = "NOT IMPLEMENTED: no data is given: nothing to add";
		
		EntityManager em = DBHelper.instance.createEntityManager();
		EntityTransaction tx = DBHelper.instance.getTransaction(em);
		
		User u = new User();
		if(firstname!=null)u.setUserFirstName(firstname);
		if(lastname!=null)u.setUserLastName(lastname);
		if(usergender!=null)u.setUserGender(usergender);
		if(email!=null)u.setUserEmail(email);
		if(Userbirthday!=null){
			if (!Userbirthday.matches("\\d{4}-\\d{2}-\\d{2}")){
				System.out.println("not match");
				msg="NOT IMPLEMENTED: wrong Date format. It should be yyyy-MM-dd";
			}else{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date date;
				try {
					date = formatter.parse(Userbirthday);
					u.setUserBirthDate(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
		if (msg != null){
			return Response.status(Response.Status.BAD_REQUEST).
	        entity(msg).
	        type(MediaType.TEXT_PLAIN).
	        build();
		}
		
		// update to database
		tx.begin();
		em.persist(u);
		tx.commit();
		em.close();
		
		return Response.ok(msg, "text/plain").entity("done").build();
	}
	
	/**
	 * Gets the history By time range.
	 *
	 * Part 11 : GET /person/{id}/{measure}?before={beforeDate}&after={afterDate} 
	 * should return the history of {measure} for person {id} in the specified range of date
	 *
	 * Part 5 : GET /person/{id}/{measure} should return the list of values (the history) of 
	 * {measure} for person {id} if the @QueryParam is missing
	 *
	 * If both {beforeDate} and {afterDate} missing , return all history of {measure}
	 * for person {id}
	 *
	 * @param info the info
	 * @param idPerson the id person
	 * @param measure the measure
	 * @return the hisby time range
	 */
	@GET
	@Path("{idPerson}/{measure}")
		public List<Measurehistory> getHisByTimeRange(@Context UriInfo info,
													@PathParam("userId")Long userid,
													@PathParam("measure")String measure ) {
		EntityManager em = DBHelper.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		List <Measurehistory> mh = new ArrayList<Measurehistory>();
		
		// with queryParam
		if(info.getQueryParameters().containsKey("before")&&
				info.getQueryParameters().containsKey("after")){
			String beforeDate = info.getQueryParameters().getFirst("before");
			String afterDate = info.getQueryParameters().getFirst("after");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Timestamp bDate = new Timestamp(formatter.parse(beforeDate).getTime());
				Timestamp aDate = new Timestamp(formatter.parse(afterDate).getTime());
				
				if(bDate.after(aDate)){
					mh =  em.createNamedQuery("HealthMeasureHistory.findByTimeRange")
										.setParameter("idPerson", userid)
										.setParameter("measureName", measure)
										.setParameter("beforeDate", bDate)
										.setParameter("afterDate", aDate)
										.getResultList();
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{ // if not
			mh = em.createNamedQuery("HealthMeasureHistory.findByPidMid")
			.setParameter("idPerson", userid)
			.setParameter("measureName", measure)
			.getResultList();
		}
		tx.commit();
		em.close();
		return mh;
	}
}
