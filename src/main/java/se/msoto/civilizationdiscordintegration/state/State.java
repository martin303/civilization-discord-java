package se.msoto.civilizationdiscordintegration.state;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class State {

    private final List<Event> events;

    public State(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public String getNextUser() {
        if (events.size() == 0) {
            return "Unknown";
        }
        return events.get(events.size() - 1).getNextUser();

    }
    public String getPreviousUser() {
        if (events.size() < 2) {
            return "Unknown";
        }
        return events.get(events.size() - 2).getNextUser();
    }

    public Event getLastEvent() {
        if (events.size() == 0) {
            return Event.builder().build();
        }
        return events.get(events.size() - 1);
    }

    public int getNumberOfEventsPastDay() {
        return (int) events.stream()
                .filter(event -> event.getTimestamp().isAfter(LocalDateTime.now().minusHours(24)))
                .count();
    }

}
