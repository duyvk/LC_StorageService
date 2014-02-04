package unitn.introsde.storage_service.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import unitn.introsde.storage_service.utils.Utils;

public class TestMeasureHistory {
	public static void main(String[] args) {
		
		Date beforeDate = Utils.strToDate("2014-01-05");
		Date afterDate = Utils.strToDate("2014-01-01");
		
		List <Measurehistory> result = Measurehistory.getMeaHisByTimeRange(1, 1, beforeDate, afterDate);
		System.out.println(result.size());
		for(Measurehistory m : result){
			System.out.println(m.getMeaHis_updated_time().toString());
		}
	}
}
