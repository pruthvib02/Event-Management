package in.Meghana.serviceInterface;

import java.util.List;

import in.Meghana.entity.AttendiesEntity;
import in.Meghana.entity.RegisterEntity;


public interface AttendiesService {
	Integer saveAttendie(AttendiesEntity entity);
	//boolean existsbyemail(String email);
	AttendiesEntity findbyEmail(String email);
	AttendiesEntity findbyEmailAndPassword(String email,String password);
	boolean existsByAEmail(String email);
//	

}
