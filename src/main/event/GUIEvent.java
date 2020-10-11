package main.event;

import java.util.Objects;

public class GUIEvent {

    private String message;
    private IGUIEventSender source;

    public GUIEvent(String message, IGUIEventSender source){
        this.message = message;
        this.source = source;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IGUIEventSender getSource() {
        return source;
    }

    public void setSource(IGUIEventSender source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GUIEvent guiEvent = (GUIEvent) o;
        return Objects.equals(message, guiEvent.message) &&
                Objects.equals(source, guiEvent.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, source);
    }
}

