package aacohn84.elevatorsimulator.event;

import java.util.ArrayList;
import java.util.List;

public class Event<Args> {
    List<EventListener<Args>> listeners;

    public Event() {
        listeners = new ArrayList<>();
    }

    public void attach(EventListener<Args> listener) {
        listeners.add(listener);
    }

    public void notify(Args args) {
        for (EventListener<Args> listener : listeners) {
            listener.update(args);
        }
    }
}
