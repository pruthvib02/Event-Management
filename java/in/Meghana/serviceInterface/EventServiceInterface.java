package in.Meghana.serviceInterface;

import java.util.List;

import in.Meghana.entity.EventsEntity;
import in.Meghana.entity.Form;

public interface EventServiceInterface {

	// public String loadEvent(String eventCategory);
	public EventsEntity loadEventData(String eventCategory);

	public List<Form> getUserEvents(String email);

	public void deleteUserBooking(Integer id);
	
	Integer saveEvent(EventsEntity entity);
	
	public void deleteEvent(String eventCategory);

}
