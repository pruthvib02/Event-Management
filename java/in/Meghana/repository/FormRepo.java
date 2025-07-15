package in.Meghana.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.Meghana.entity.Form;
import jakarta.transaction.Transactional;

@Transactional
public interface FormRepo extends JpaRepository<Form, Integer> {
	@Modifying
	@Query("SELECT f FROM Form f WHERE f.email = :uemail")
	List<Form> findEventByEmail(String uemail);

	@Modifying
	@Query("DELETE FROM Form WHERE id =:id")
	void cancelEventById(Integer id);
	
	@Modifying
	@Query("UPDATE Form SET status = 'accepted' WHERE id = :id")
	void acceptEventById(Integer id);
	
	@Modifying
	@Query("UPDATE Form SET status = 'assigned' WHERE id = :id")
	void assignEventById(String id);
	
	
	@Query("SELECT f FROM Form f WHERE f.id = :id")
	Form findEventById(Integer id);
}
