package repos;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import models.MessageModel;

/**
 * @author bernardosze
 */
@ApplicationScoped
public class MessageRepo {
    private List<Session> sessions = new ArrayList<>();
    private List<MessageModel> messages = new ArrayList<>();
    
    
    public void addSession(Session session) {
        sessions.add(session);
        
    }
    
    public void removeSession(Session session) {
        sessions.remove(session);
        
    }
        
    public List<Session> getSessions() {
        return sessions;
        
    }
    
    public void addMessage(MessageModel message) {
        messages.add(message);
        
    }
    
    public List<MessageModel> getMessages() {
        return messages;
        
    }

}
