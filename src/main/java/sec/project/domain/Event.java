package sec.project.domain;

import java.util.Objects;
import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Event extends AbstractPersistable<Long> implements Comparable<Event> {

    private String name;
    private String location;
    private String when;

    public Event() {
        super();
    }

    public Event(String name, String location, String when) {
        this();
        this.name = name;
        this.location = location;
        this.when = when;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.location);
        hash = 41 * hash + Objects.hashCode(this.when);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.when, other.when)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Event o) {
        return this.name.compareTo(o.name);
    }
}
