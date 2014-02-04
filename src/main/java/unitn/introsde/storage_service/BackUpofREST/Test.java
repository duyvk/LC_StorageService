package unitn.introsde.storage_service.BackUpofREST;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.Scanner;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


public class Test {

	/*
	
	/////////////////////////////// USER
	public User getPersonById( Integer id) {
		User p =User.getUserById(id);
	      return p;
		}
	
	public boolean remove(Integer id)
	{
		boolean p=User.removePerson(3);
		return p;
	}
	
	
	public User AddUser(User user){
		
		User u = User.addUser(user);
		return u;
	}
	
	public  Integer all()
	{
		
		List<User> userList=new ArrayList<User>();
		userList=User.getAll();
		return  userList.size();
	}
	
	/////////////////////////////////////////LifeStatus
	
	public Lifestatus removeLifeStatus(int id){
		
		Lifestatus p = Lifestatus.removeLifeStatus(3);	
		return p;
	}
	
	public List<Lifestatus> getLifeStatusByUserId(int id) {
		
		List<Lifestatus> UserLifeStatus =Lifestatus.getLifeStatusByUserId(id);
		
		return UserLifeStatus;
	
	}
	*/
	
	public static void main(String[] args) throws Exception {
		
		
		String protocol = "http://";
		String port = ":5906";
		String hostname="";
		
		try {
			hostname = InetAddress.getLocalHost().getHostAddress();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(hostname.equals("127.0.0.1"))
			hostname = "localhost";
		
		String baseUrl = protocol + hostname + port;
		
		ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
		WebResource resource = client.resource(baseUrl);
		

		Scanner scanner = new Scanner(System.in);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int  id = 1;
		String url = baseUrl + "/user/"+id;
		
		resource = client.resource(url);
		resources res=new resources();
		res.getUserById(id);
			
		
		
		
	/*	Test t = new Test();
		t.remove(201);
		if(t.remove(3)){
		System.out.println("done !!");
		}
		else
		{
			System.out.println("not done !!");

		}
		
		
		System.out.println(t.getPersonById(3).getLifeStatus().get(0).getLifeStatus_value());
		System.out.println(t.remove(3));
		System.out.println(t.getPersonById(3).getUserFirstName());
		User u=new User();
		u.setUserFirstName("Duy");
		u.setUserLastName("vu");
		u.setUserEmail("Duy@yahoo.it");
		u.setUserGender("male");
		
		
		t.AddUser(u);
		
		
		Lifestatus.addUserLifeStatus(201, 1, 89.0);
		
		Lifestatus lf=Lifestatus.getspecificMeasure(1,1);
		System.out.println("adjnad"+lf.getLifeStatus_value());
		
		List<Lifestatus> lif=t.getLifeStatusByUserId(201);
		System.out.println(lif.size());
		for(Lifestatus lff:  lif ){
			System.out.println(lff.getLifeStatus_value());
		}
		
	Calendar cal =Calendar.getInstance();
		cal.set(2014,Calendar.JANUARY,24);
		
		Calendar cal2 =Calendar.getInstance();
		cal2.set(2014,Calendar.FEBRUARY,26);
		
		List<Goal> g=Goal.getGoalByTimeRange(1,cal.getTime(),cal2.getTime());
		
		
		List<Goal> go=Goal.getAllUserGoals(1);
		System.out.println(g.size());
		for(Goal lff:  g){
			System.out.println(lff.getGoalDesc());
		}
		
   System.out.println("numero dei user e"+t.all());*/
	}

}
