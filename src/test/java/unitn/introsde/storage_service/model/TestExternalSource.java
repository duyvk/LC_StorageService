package unitn.introsde.storage_service.model;

public class TestExternalSource {
public static void main(String[] args) {
	Externalsource e = Externalsource.getFoodSourceById(1);
	System.out.println(e.getExsourceName());
	
	
	Externalsource e1 = new Externalsource();
	e1.setExsourceName("nothing");
	//System.out.println(Externalsource.addFoodSource(e1).getExsourceName());
	
	Externalsource e2 = new Externalsource();
	e2.setExSource_id(901);
	e2.setExsourceUrl("http");
	System.out.println(Externalsource.updateFoodSource(e2));
	
	System.out.println(Externalsource.removeFoodSource(901));
}
}
