package sockets;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import models.MessageModel;
import repos.MessageRepo;

/**
 * @author bernardosze
 */
@ServerEndpoint("/chat")
public class SocketServer {
    //Connect to a "DB"
    @Inject
    private MessageRepo repo;
    
    @OnOpen
    public void open(Session session) {
        repo.addSession(session);
        
    }
    
    @OnClose
    public void close(Session session) {
        repo.removeSession(session);
        
    }
    
    // Should MATCH our MessageModel
    @OnMessage
    public void message(Session session, String message) {
        Gson gson = new Gson();
        MessageModel messageModel = gson.fromJson(message, MessageModel.class);
        if(messageModel.getAction().equals("send")) {
            //ADD to repo
            messageModel.setCreatedDate(new Date());
            repo.addMessage(messageModel);
            
            //Send message to all connected clients
            sendToAll(messageModel);
            
        } else if(messageModel.getAction().equals("list")) {
            //Send all Messages from repo
            repo.getMessages().forEach(msg -> sendToAll(msg));
        }
    }
    
    private void sendToAll(MessageModel message) {
        Gson gson = new Gson();
        String json = gson.toJson(message);
        
        repo.getSessions().forEach(x -> {
            try {
                x.getBasicRemote().sendText(json);
            } catch(IOException e) {
                System.out.println(e);
            }
        });
    }
}
