package in.Meghana.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.Meghana.entity.AttendieTaskStatus;
import in.Meghana.entity.AttendiesEntity;
import in.Meghana.entity.TaskEntity;
import jakarta.transaction.Transactional;
@Transactional

public interface AttendieTaskStatusRepository extends JpaRepository<AttendieTaskStatus, Integer>{

	//to find the status by attendee ans task
	AttendieTaskStatus findByAttendieAndTask(AttendiesEntity attendie,TaskEntity task);
	
	//find all the tasks with status for a specifix attendei
	List<AttendieTaskStatus> findByAttendie(AttendiesEntity attendie);
}
