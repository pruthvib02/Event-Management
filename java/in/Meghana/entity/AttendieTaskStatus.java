package in.Meghana.entity;

//package in.Meghana.entity;

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
@Table(name="AttendieTaskStatus")
public class AttendieTaskStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "attendie_id")
    private AttendiesEntity attendie;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    private boolean status;
}
