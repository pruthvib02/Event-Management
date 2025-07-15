package in.Meghana.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Bookings")
public class Form {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String eventName;
	private String date;
	private String time;
	private String address;
	@Column(name = "status", columnDefinition = "VARCHAR(255) DEFAULT 'pending'")
	private String status;
	private Long attendees;

	
	//private Long attendees;
}
