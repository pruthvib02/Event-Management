package in.Meghana.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.RequestParam;

import in.Meghana.entity.TaskEntity;
import jakarta.transaction.Transactional;

@Transactional
public interface TaskRepo extends JpaRepository<TaskEntity, Integer>{
	
	@Query("select t from TaskEntity t where bId=:id")
	TaskEntity findTaskByBId(String id);
	
	@Query("select t from TaskEntity t where (VenueManagement=:aId) or (LogisticsAndOperations=:aId) or (CateringAndHospitality=:aId) or (EntertainmentAndActivities=:aId) ")
	List<TaskEntity> getTaskByIdEmail(int aId);
	
	@Query("select t from TaskEntity t")
	   List<TaskEntity> findAll();
	
	
		@Query("SELECT t FROM TaskEntity t WHERE " +
	           "(:category = 'VenueManagement' AND t.VenueManagement IS NOT NULL) OR " +
	           "(:category = 'LogisticsAndOperations' AND t.LogisticsAndOperations IS NOT NULL) OR " +
	           "(:category = 'CateringAndHospitality' AND t.CateringAndHospitality IS NOT NULL) OR " +
	           "(:category = 'EntertainmentAndActivities' AND t.EntertainmentAndActivities IS NOT NULL)")
	   //TaskEntity findTasksByCategory(@RequestParam("category") String category);	    
	List<TaskEntity> findTasksByCategory(@RequestParam("category") String category);
		

//		private Integer tId;
//		private String bId;
//		private String eName;
//		private String eDate;
//		private String eTime;
//		private String eAddress;
//		private String VenueManagement;
//		private String LogisticsAndOperations;
//		private String EntertainmentAndActivities;
//		private String CateringAndHospitality;
//		private boolean status;
		
		
		@Query("UPDATE TaskEntity t SET t.status = :status WHERE t.tId = :taskId AND "
	            + "(t.CateringAndHospitality = :attendieId OR "
	            + "t.EntertainmentAndActivities = :attendieId OR "
	            + "t.LogisticsAndOperations = :attendieId OR "
	            + "t.VenueManagement = :attendieId)")
	    Integer updateTaskStatus(@Param("taskId") String taskId, @Param("attendieId") String attendieId, @Param("status") String status);

}



