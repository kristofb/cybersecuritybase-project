package sec.project.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Account extends AbstractPersistable<Long> {

    @Column(unique = true)
    private String username;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Event> registeredEvents;

    public Account() {
    }

    public Account(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Event> getRegisteredEvents() {
        return registeredEvents;
    }

    public void setRegisteredEvents(List<Event> registeredEvents) {
        this.registeredEvents = registeredEvents;
    }

    public void registerTo(Event event) {
        List<Event> events = getRegisteredEvents();
        if (!events.contains(event)) {
            events.add(event);
            setRegisteredEvents(events);
        }
    }

    public void unregisterTo(Event event) {
        List<Event> events = getRegisteredEvents();
        if (events.contains(event)) {
            events.remove(event);
            setRegisteredEvents(events);
        }
    }

}
