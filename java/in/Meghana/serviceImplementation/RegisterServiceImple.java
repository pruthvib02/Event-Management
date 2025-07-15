package in.Meghana.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.Meghana.entity.AttendiesEntity;
import in.Meghana.entity.Form;
import in.Meghana.entity.RegisterEntity;
import in.Meghana.entity.TaskEntity;
import in.Meghana.repository.AttendiesRepo;
import in.Meghana.repository.FormRepo;
import in.Meghana.repository.RegisterRepo;
import in.Meghana.repository.TaskRepo;
import in.Meghana.serviceInterface.RegisterService;
import jakarta.servlet.http.HttpSession;

@Service
public class RegisterServiceImple implements RegisterService {
	@Autowired
	private RegisterRepo repo;

	@Override
	public Integer saveUser(RegisterEntity entity) {

		return repo.save(entity).getUserId();
	}

	@Override
	public String loginUser(String email, String password, HttpSession session) {
		System.out.println(email + "this is in imple");
		String b;
		if ((repo.findByUserEmail(email).getUserEmail().equals(email))
				&& (repo.findByUserEmail(email).getUserPassword().equals(password))) {
			System.out.println("login Succesfull...........");
			session.setAttribute("umail", repo.findByUserEmail(email).getUserEmail());
			session.setAttribute("uname", repo.findByUserEmail(email).getUserName());
			session.setAttribute("uphone", repo.findByUserEmail(email).getUserPhone());
			b = "success";
		} else {
			System.out.println("Login unsuccess..");
			b = "fail";
		}
		return b;

	}

	@Override
	public boolean isCodeAvailable(String useremail) {

		return !repo.existsByUserEmail(useremail);
	}

	@Override
	public boolean checkUser(String email) {
		return repo.existsByUserEmail(email);
		//return result;
	}

	@Override
	public String forgotPassword(String userEmail, String userPassword) {
		String status = "";
		boolean result = repo.existsByUserEmail(userEmail);
		if (result) {
			int i = repo.updateUserPassword(userEmail, userPassword);
			if (i > 0) {
				status = "success";
				System.out.println(status);
			} else {
				status = "updatation failure";
				System.out.println(status);
			}
		} else {
			System.out.println("No such mail found");
			status = "failure";
			System.out.println(status);
		}
		return status;
	}

	@Override
	public List<RegisterEntity> data() {

		List<RegisterEntity> list = repo.findAll();
		return list;
	}

	@Override
	public void deleteUser(Integer id) {
		repo.deleteById(id);
	}

	@Autowired
	private FormRepo fRepo;

	@Override
	public List<Form> getAllBookings() {
		List<Form> list = fRepo.findAll();
		return list;
	}

	@Override
	public void deleteUserBookingByAdmin(Integer id) {
		fRepo.deleteById(id);

	}
	
	@Autowired
	private AttendiesRepo arepo;
	@Override
	public List<AttendiesEntity> getAllAttendies() {
		List<AttendiesEntity> list = arepo.findAll();
		return list;
	}

	@Override
	public void deleteAttendie(Integer id) {
		arepo.deleteById(id);
		
	}

	@Override
	public String AttendieLogin(String email, String password, HttpSession session) {
		
		String b;
		if ((arepo.findByaEmail(email).getAEmail().equals(email))
				&& (arepo.findByaEmail(email).getAPassword().equals(password))) {
			System.out.println("login Succesfull...........");
			session.setAttribute("umail", arepo.findByaEmail(email).getAEmail());
			session.setAttribute("uname", arepo.findByaEmail(email).getAName());
			session.setAttribute("uphone", arepo.findByaEmail(email).getAPhone());
			b = "success";
		} else {
			System.out.println("Login unsuccess..");
			b = "fail";
		}
		return b;
	}

	@Override
	public void acceptUserBookingByAdmin(Integer id) {
		// TODO Auto-generated method stub
		fRepo.acceptEventById(id);
	}

	@Override
	public Form findFormById(Integer id) {
		// TODO Auto-generated method stub
		return fRepo.findEventById(id);
	}

	@Autowired
	private TaskRepo trepo;
	
	@Override
	public int saveTask(TaskEntity task) {
		// TODO Auto-generated method stub
		return trepo.save(task).getTId();
	}

	@Override
	public void assignedUserBookingByAdmin(String bId) {
		// TODO Auto-generated method stub
		fRepo.assignEventById(bId);
	}

	@Override
	public List<TaskEntity> getAllTasks() {
		// TODO Auto-generated method stub
		return trepo.findAll();
	}

	@Override
	public TaskEntity getTaskByBId(String id) {
		
		return trepo.findTaskByBId(id);
	}
	
	
	@Override
	public AttendiesEntity getAttendisById(String id) {
		// TODO Auto-generated method stub
		int number = Integer.parseInt(id);
		return arepo.getById(number);
	}

	@Override
	public List<TaskEntity> getTaskByIdEmail(int aId) {
		// TODO Auto-generated method stub
		
		return trepo.getTaskByIdEmail(aId);
	}

	

}
