package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
