package main.event;

public interface IGUIEventSender {
    void fireEvent(GUIEvent event) throws Exception;
    void addListener(IGUIEventListener listener);
    void removeListener(IGUIEventListener listener);
}
