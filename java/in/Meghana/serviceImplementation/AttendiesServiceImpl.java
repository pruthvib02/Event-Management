package in.Meghana.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.Meghana.entity.AttendiesEntity;
import in.Meghana.entity.RegisterEntity;
import in.Meghana.repository.AttendiesRepo;
import in.Meghana.serviceInterface.AttendiesService;

@Service
public class AttendiesServiceImpl implements AttendiesService{
	@Autowired
	private AttendiesRepo repo;
	
	
	@Override
	public Integer saveAttendie(AttendiesEntity entity) {
		// TODO Auto-generated method stub
		return repo.save(entity).getAId();
	}


	


	@Override
	public AttendiesEntity findbyEmailAndPassword(String email, String password) {
		// TODO Auto-generated method stub
		return repo.findByEmailAndPassword(email, password);
	}


	



	@Override
	public     boolean existsByAEmail(String email) {
		 return repo.existsByAEmail(email);
	}
 
		




	@Override
	public AttendiesEntity findbyEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}


	

}
