package in.Meghana.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Tasks")
public class TaskEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tId;
	private String bId;
	private String eName;
	private String eDate;
	private String eTime;
	private String eAddress;
	private String VenueManagement;
	private String LogisticsAndOperations;
	private String EntertainmentAndActivities;
	private String CateringAndHospitality;
	private boolean status;
//	@ManyToOne
//    @JoinColumn(name = "attendie_id")  // Ensure this column is linked to the Attendee table
//    private AttendiesEntity attendie; 
//	    
}
