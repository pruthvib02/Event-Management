package in.Meghana.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.Meghana.entity.AttendieTaskStatus;
import in.Meghana.entity.AttendiesEntity;
import in.Meghana.entity.Form;
import in.Meghana.entity.RegisterEntity;
import in.Meghana.entity.TaskEntity;
import in.Meghana.serviceInterface.AttendieTaskStatusService;
import in.Meghana.serviceInterface.AttendiesService;
import in.Meghana.serviceInterface.FormService;
import in.Meghana.serviceInterface.RegisterService;
import in.Meghana.serviceInterface.TaskService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/EventManagement")

public class RegisterController {

	@Autowired
	private RegisterService service;
	@Autowired
	private TaskService repo;

	@GetMapping("app")
	public String loadIndex() {
		return "index";
	}

	@GetMapping("signuppage")
	public String openSignupPage() {
		return "Signup";
	}
	

	@GetMapping("loginpage")
	public String openLoginPage() {
		return "login";
	}

	@GetMapping("forgot")
	public String openForgotPage() {
		return "forgot";
	}
	
	@GetMapping("attendielogin")
	public String openAttendieLogin() {
		return "AttendieLogin";
	}
	
	
	@GetMapping("Home")
	public String openHome(@RequestParam(required = false) String message, HttpSession session, Model model) {
		model.addAttribute("uname", session.getAttribute("uname"));
		model.addAttribute("umail", session.getAttribute("umail"));
		model.addAttribute("uphone", session.getAttribute("uphone"));
		model.addAttribute("message", message);

		return "Home";
	}

	// Registration Controller
	@PostMapping("userRegister")
	public String userRegister(@ModelAttribute RegisterEntity user, Model model) {
		//dude each and every time the model attribute is used the register 
		//Each registration form submission creates a new instance of RegisterEntity.
		//This object is only populated with the details entered by the current user.
		//No other user's data is involved because:
	//	This is a POST request handling only one user's data at a time.--vvimp
	//	The RegisterEntity object is not shared between users; it's specific to this request.
		
		boolean exist = service.checkUser(user.getUserEmail());
		
		//using the getter method to fetch the details of the users
		String page = "";
		if (exist == false) {
			Integer uid = service.saveUser(user);
			String uname = user.getUserName();

			if (uid > 0) {
				model.addAttribute("message", uname + " Registered Succesfully with id :" + uid);
				page = "login";
				//the variable which is taken up is used here
			} else {
				model.addAttribute("message", "Register UnSuccesfull");
				page = "Signup";
			}
		} else {

			model.addAttribute("message", "Registration UnSuccesfull");
			page = "Signup";

		}
		return page;
	}

	// Login Controller
//	The form collects email and password from the user.
//	userEmail and userPassword are the names used for the input fields.
	//The form is submitted using the POST method because sensitive information (password) is being sent.
	
	//RegisterEntity entity: This automatically gets populated with the form data
	//because the input names (userEmail and userPassword) match the fields in RegisterEntity.
	
	
	@PostMapping("userLogin")
	public String userLogin(@ModelAttribute RegisterEntity entity, HttpSession session, Model model) {
		String page = "";
		String status = service.loginUser(entity.getUserEmail(), entity.getUserPassword(), session);
//this will get the current email and the passoword of the user who has entered
		if (status.equals("success")) {

			//the status is set in the logic of the service the value of the status is returned as the success and the failure
			model.addAttribute("uname", session.getAttribute("uname"));
			model.addAttribute("umail", session.getAttribute("umail"));
			model.addAttribute("uphone", session.getAttribute("uphone"));

			if (entity.getUserEmail().equals("admin@gmail.com") && (entity.getUserPassword().equals("88888888"))) {
				System.out.println("Admin login Succesfull..");
				page = "Admin";
			} else {

				System.out.println("User login sucessful..");
				page = "Home";
			}

		}

		else {
			model.addAttribute("message", "Login failed");
			System.out.println("Login failed..");
			page = "login";
		}
		return page;
	}

	// Logout Controller
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		System.out.println("Logged out...");
		return "login";
	}

	// forgot controller
	@PostMapping("forgotPass")
	public String forgotPass(RegisterEntity entity, Model model) {
		String page = "";
		System.out.println(entity.getUserEmail());
		String result = service.forgotPassword(entity.getUserEmail(), entity.getUserPassword());
		if (result.equals("success")) {
			model.addAttribute("message", "Password changed succesfully");
			page = "login";
		} else {
			model.addAttribute(("message"), "No such user email");
			page = "forgot";
		}
		return page;
	}

	// for Email check with ajax
	@GetMapping("/checkEmail")
	public ResponseEntity<Boolean> checkCode(@RequestParam String useremail) {
		boolean isCodeAvailable = service.isCodeAvailable(useremail);
		return ResponseEntity.ok(isCodeAvailable);
	}

	// display all user in admin page

	@GetMapping("/users")
	public String data(Model model, @RequestParam(required = false) String message) {
		List<RegisterEntity> list = service.data();
		if (message != null) {
			model.addAttribute("message", message);
		}
		model.addAttribute("list", list);
		return "User";

	}

	// delete bookings by admin
	@GetMapping("deleteUserBooking")
	public String cancelEvent( Model model, @RequestParam Integer id) {
		service.deleteUserBookingByAdmin(id);
		return "redirect:/EventManagement/allBookings?message= Event of id: " + id + " Cancelled successfully";

	}
	
	//accept booking by admin
	@GetMapping("acceptUserBooking")
	public String acceptEvent( Model model, @RequestParam Integer id) {
		service.acceptUserBookingByAdmin(id);
		return "redirect:/EventManagement/allBookings?message= Event of id: " + id + " Accepted successfully";

	}
	
	//assign task to attendies by admin
	@GetMapping("assigntask")
	public String assignTask(Model model, @RequestParam Integer id) {
		List<AttendiesEntity> a= service.getAllAttendies();
		Form f=service.findFormById(id);
		model.addAttribute("f",f);
		model.addAttribute("a",a);
		return "Assigntask";
		

	}
	
	// all bookings in admin
	//the methid is from the delete method if something gets deleted
	@GetMapping("allBookings")
	public String bookingsForAdminPage(Model model, @RequestParam(required = false) String message) {

		String page = "";
		if (message != null) {
			model.addAttribute("message", message);
		}
		List<Form> list = service.getAllBookings();
		model.addAttribute("list", list);
		//System.out.println("booking page");
		page = "Booking";
		return page;
	}

	// delete user by admin
	@GetMapping("/delete")
	public String delete(@RequestParam Integer id) {
		service.deleteUser(id);
		return "redirect:/EventManagement/users?message=User " + id + " Deleted successfully";
	}
	
	@GetMapping("/addEvents")
	public String adminAddEvents() {	
		return "addEvents";	
	}
	
	@GetMapping("/deleteEvents")
	public String admindeleteEvents() {	
		return "deleteEvents";	
	}
	@GetMapping("/addAttendies")
	public String adminaddAttendies() {	
		return "AddAttendies";	
	}
	//all attendies in admin
	@GetMapping("/AllAttendies")
	public String adminDelAttendies(Model model, @RequestParam(required = false) String message) {	
		List<AttendiesEntity> list = service.getAllAttendies();
		if (message != null) {
			model.addAttribute("message", message);
		}
		model.addAttribute("list", list);
		
		return "ShowAttendie";	
	}
	
	//delete attendies by admin
	@GetMapping("/deleteAttendie")
	public String deleteAttendie(@RequestParam Integer id) {
		service.deleteAttendie(id);
		return "redirect:AllAttendies?message=Attendie " + id + " Deleted successfully";
	}
	@Autowired
	private AttendiesService arepo;
	
	
	
	
	//for the login of the attendie
	@PostMapping("/AttendieLogin")
	public String AttendieLogin(@RequestParam String email, @RequestParam String password, HttpSession session,
			Model model) {

		AttendiesEntity ae = arepo.findbyEmailAndPassword(email, password);
		if (ae != null) {
			session.setAttribute("attendieId", ae.getAId());
			System.out.println(ae.getAId());
			session.setAttribute("attendie", ae);
			session.setAttribute("uname", ae.getAName());
			session.setAttribute("umail", ae.getAEmail()); // Setting the email in session
			session.setAttribute("uphone", ae.getAPhone()); // Setting the phone in session
			
			session.setAttribute("category", ae.getACategory());
			
			session.setAttribute("attendie_id", ae.getAId());
			model.addAttribute("msg", "Successfully logged in"); // Add success message
			model.addAttribute("uname", ae.getAName()); // Add the user's name to the model
			model.addAttribute("umail", ae.getAEmail()); // Add the email to the model
			model.addAttribute("uphone", ae.getAPassword()); // Add the phone to the model
			return "redirect:dashboardAttendie";

		}

		else {
			model.addAttribute("msg", "Invalid email or password");
			return "login";
		}
	}
	
	//the method that is workign......
	
		/////////-------------------------------------------
		//As soon as the attendies logs in this gets showed 
		@GetMapping("/dashboardAttendie")
		public String adminAllTasksShow(Model model,HttpSession se) {	

			//System.out.println("hi");
				 String category =(String) se.getAttribute("category"); //setattribute in attendie login method
				// System.out.println(category);
		         // TaskEntity t = (TaskEntity) repo.getAllTask();
				 if(category!=null)
				 {
			 List<TaskEntity> t = (List<TaskEntity>) repo.findTasksByCategory(category);
			 System.out.println(t);
			 model.addAttribute("task",t);
			return "dashboardAttendie";
				 }
				 return "AttendieLogin";
		}
	
	//for ajaz verfiaction of mail of the attendies 
	
	
	@GetMapping("/checkEmailofAttendie")
	public ResponseEntity<Boolean> checkEmail(@RequestParam String useremail) {
		boolean isCodeAvailable = service.isCodeAvailable(useremail);
	    System.out.println("Checking email: " + useremail + " | Exists: " + isCodeAvailable);

		return ResponseEntity.ok(isCodeAvailable);
	}

	
	@PostMapping("assign")
	public String saveForm(@ModelAttribute TaskEntity task, Model model) {
		String page = "";
		int ref = service.saveTask(task);
		if (ref > 0) {
			model.addAttribute("message", "Booking succesfull");
			service.assignedUserBookingByAdmin((task).getBId());
			return "redirect:allBookings";
		} else {
			model.addAttribute("message", "Booking failed");
			page = "AssignTask";return page;
		}
		
	}
	
	
	
	
	
	
	 @Autowired
	    private AttendieTaskStatusService statusService;

	// @GetMapping("/attendie/{attendieId}/tasks")
//	@GetMapping("/dashboardAttendie")
//	private String getTasksByAttendie(HttpSession se)
//	{
//		 AttendiesEntity ae = (AttendiesEntity) se.getAttribute("attendie");
//		 List<AttendieTaskStatus> li= statusService.getTasksByAttendie(ae);
//		
//		 if(li!=null)
//		 {
//		 return "status-dashboardAttendie";
//		 }
//		 else
//		 {
//			 return "AttendieLogin";
//		 }
//	}
	
	
	//to mark the task as completed 
	@PostMapping("/updateStatus/{attendieID}/task/{taskID}/status")
	public String updateTaskStatus(HttpSession se, @PathVariable int attendieId,
            @PathVariable int taskId,
            @RequestParam boolean status)
	{
		TaskEntity t=    (TaskEntity) repo.getAllTask();
		 AttendiesEntity ae = (AttendiesEntity) se.getAttribute("attendie");		
		
		statusService.markTaskAsCompleted(ae,t, status);
		
		return "dashboardAttendie";
	}
	
	
	
	
	
@GetMapping("/tasks")
	public String showAllTasks(Model m,HttpSession se)
	{
	
	//System.out.println("allthetasksforadmin");
		List<TaskEntity> t1=   repo.getAllTask();
//		int id= (int) se.getAttribute("attendieId");
//		System.out.println(id);
		m.addAttribute("task",t1);
		
//		 m.addAttribute("attendieId",id);
		//se.setAttribute("task_id", ((TaskEntity) t1).getTId());
		//se.setAttribute("taskb_id", ((TaskEntity) t1).getBId());
		return "taskdisplay";
	}
	
	
	
	
	
	//to update the status of the task this logic
@PostMapping("/updateTaskStatus")
public String updateTaskStatus(
    @RequestParam Integer taskId, 
    @RequestParam String attendieId, 
    @RequestParam String status, 
    HttpSession se, 
    Model m) {

	Integer attendieId1 =  (Integer) se.getAttribute("attendieId");
    // Print the input values for debugging
    System.out.println("Received taskId: " + taskId);
    System.out.println("Received attendieId: " + attendieId1);
    System.out.println("Received status: " + status);

    // Parse the taskId and attendieId from the request
    // Ensure attendieId is converted to Integer for the updateStatus method
  //  Integer attendieIdInt = Integer.parseUnsignedInt(attendieId1);  // Convert the String to Integer

    // Call the updateStatus method from the repository
    Integer rowsUpdated = repo.updateStatus(taskId, attendieId1, status);

    // Debugging logs
    System.out.println("Rows updated: " + rowsUpdated);

    // Retrieve attendieId from session
    int id = (int) se.getAttribute("attendieId");
    System.out.println("ID from session: " + id);

    // Add the attendieId to the model for later use
    m.addAttribute("attendieId", id);

    // Check if the update was successful
    if (rowsUpdated > 0) {
        System.out.println("Task status updated successfully!");
    } else {
        System.out.println("Task status update failed.");
    }

    // Redirect or return the desired view
    return "redirect:/dashboardAttendie";
}

}