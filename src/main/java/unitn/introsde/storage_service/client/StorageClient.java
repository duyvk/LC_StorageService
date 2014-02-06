package unitn.introsde.storage_service.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import unitn.introsde.storage_service.model.Caregiver;
import unitn.introsde.storage_service.model.Goal;
import unitn.introsde.storage_service.model.Lifestatus;
import unitn.introsde.storage_service.model.Measuredefinition;
import unitn.introsde.storage_service.model.Measurehistory;
import unitn.introsde.storage_service.model.User;
import unitn.introsde.storage_service.utils.Utils;
import unitn.introsde.storage_service.ws.Storage;

public class StorageClient {

	static URL url;

	public static void main(String[] args) {
		try {
			url = new URL ("http://localhost:5900/ws/storage?wsdl");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		QName qname = new QName("http://ws.storage_service.introsde.unitn/", "StorageService");

		Service service = Service.create(url,qname);

		Storage storage = service.getPort(Storage.class);
		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// test your method here
		
		/* -------------------------GoalTracking Service --------------------*/
		List<Measurehistory> hisOfMeas = storage.trackGoalbyUser(1, 1);
		if (hisOfMeas.size() !=0){
			Goal trackedGoal = storage.getGoalById(1);
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
		
/*		
		System.out.println("----------------------------CALCULATOR SERVICES------------------------------------");
		List<User> returnUsers = storage.searchUserbyName("duy khuong", 4);
		for (User u : returnUsers)
			System.out.println(u.getUserFirstName()+ " "+u.getUserLastName());
		
		
		System.out.println("----------------------------USER SERVICES------------------------------------");
		System.out.println("--------------Test get user by id---------------");
		User u = storage.getUserById(1);
		System.out.println(u.getUserEmail());
		
		System.out.println("--------------Test create user---------------");
		User newUser = new User();
		newUser.setUserFirstName("testnewuserwithonlyfirstname");
		try {
			newUser.setUserBirthDate(format.parse("1989-01-01"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newUser.setUserEmail("abc@anc");
		newUser.setUserGender("male");
		newUser.setUserLastName("lastname");
		//System.out.println(storage.addUser(newUser));
		
		System.out.println("--------------Test update user---------------");
		User updatedUser = new User();
		updatedUser.setUserId(52);
		updatedUser.setUserEmail("duyvk142@gmail.com");
		System.out.println(updatedUser.getUserEmail());
		try{
			//System.out.println(storage.updateUser(updatedUser));
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(storage.getUserById(updatedUser.getUserId()).getUserEmail());
		
		System.out.println("--------------Test remove user---------------");
		//System.out.println(storage.removeUser(151));
		
		
		*/
		System.out.println("----------------------------GOAL SERVICES------------------------------------");
		System.out.println("-----------------Test get Goal by goal_id-----------");
		Goal g = storage.getGoalById(1);
		if (g!=null)
			System.out.println(g.getGoalId());
		
		System.out.println("-----------------Test remove Goal by goal_id-----------");
		System.out.println(storage.removeGoal(5));
		
		System.out.println("-----------------Test remove Goal by user_id, goal_id-----------");
		System.out.println(storage.removeGoalByUser(1,651));
		
		System.out.println("-----------------Test remove Goal by caregiver_id,goal_id-----------");
		System.out.println(storage.removeGoalByCaregiver(2	, 601));

		System.out.println("-----------------Test add Goal -----------");
/*
		Goal newGoal = new Goal();
		User gu = storage.getUserById(1);
		Caregiver gcg = storage.getCaregiverById(2);
		Measuredefinition dmf = storage.getMeaDefById(1);
		
		newGoal.setUser(gu);
		newGoal.setCaregiver(gcg);
		newGoal.setMeasuredefinition(dmf);
		newGoal.setGoal_from_date(new Date());
		newGoal.setGoal_type("daily");
		try {
			newGoal.setGoal_to_date(format.parse("2014-02-15"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newGoal.setGoal_expected_value(100);
		newGoal.setGoalDesc("aaa");
		
		// add Goal
		System.out.println(storage.addGoal(newGoal));*/
/*		
		System.out.println("-----------------Test update Goal -----------");
		Goal updatedGoal = new Goal();
		updatedGoal.setGoalId(101);
		updatedGoal.setMeasuredefinition(Measuredefinition.getMeasureDefById(2));
		System.out.println(Measuredefinition.getMeasureDefById(2).getMeaDef_name());
		updatedGoal.setCaregiver(Caregiver.getCaregiverById(1));
		System.out.println(Caregiver.getCaregiverById(1).getCgFirstName());
		
		System.out.println(storage.updateGoal(updatedGoal));
		
		System.out.println("-----------------Test update Goal by user-----------");
		Goal updatedGoal2 = new Goal();
		updatedGoal2.setGoalId(101);
		updatedGoal2.setMeasuredefinition(Measuredefinition.getMeasureDefById(1));
		
		System.out.println(storage.updateGoalByUser(100, updatedGoal2));
		
		System.out.println("-----------------Test update Goal by caregiver-----------");
		Goal updatedGoal3 = new Goal();
		updatedGoal3.setGoalId(101);
		updatedGoal3.setMeasuredefinition(Measuredefinition.getMeasureDefById(2));
		
		System.out.println(storage.updateGoalByUser(1000, updatedGoal3));
*/	
	/*	System.out.println("-----------------Test add lifeStatus -----------");	
		
			Lifestatus lf=new Lifestatus();
			
			User user=storage.getUserById(2);
			Measuredefinition di = storage.getMeaDefById(4);
		
			
			lf.setUser(user);
	        lf.setMeasuredefinition(di);
	       try {
				lf.setLifeStatus_update_time(format.parse("2014-03-15") );
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        lf.setLifeStatus_value(89.0);
	        
	        
	        System.out.println(storage.addHealthStatus(lf));*/
	        
	     System.out.println("-----------------Test update lifeStatus -----------");	
	
	      //  System.out.println(storage.updateLifeStatus(2, 100));
	        
	        
	
	
	}

}
