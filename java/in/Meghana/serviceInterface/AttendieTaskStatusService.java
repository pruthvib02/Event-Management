package in.Meghana.serviceInterface;

import java.util.List;

import in.Meghana.entity.AttendieTaskStatus;
import in.Meghana.entity.AttendiesEntity;
import in.Meghana.entity.TaskEntity;

public interface AttendieTaskStatusService {

	
	// to get the tasks with the status of an attendie
	List<AttendieTaskStatus> getTasksByAttendie(AttendiesEntity attendie);
	
	
	  // Mark a task as completed for an attendee
    void markTaskAsCompleted(AttendiesEntity attendie, TaskEntity task, boolean status);
}
