package com.id.cloud.web.websocket.endpoint;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value = "/websocket/{user}") 
public class ChatServerEndpoint {
	private Session wsSession; 
	private String user;
	private static final Logger chatServerLogger = LoggerFactory.getLogger(ChatServerEndpoint.class);
	
	@OnOpen  
    public void open(Session session,  @PathParam(value = "user")String user){  
        this.wsSession = session;  
        chatServerLogger.info("*** WebSocket opened from sessionId " + session.getId()); 
        broadcast(session, "System Message: "+ user + " joined room.");
        send(session, "There are "+session.getOpenSessions().size()+" users in the room.");
        broadcast(session, "Welcome "+ user+"!");
        this.user = user;
    }  
	
	@OnMessage  
    public void inMessage(String message){  
		chatServerLogger.info("*** WebSocket Received from sessionId " + this.wsSession.getId() + " and User "+user + ": "  + message);
		broadcast(wsSession, this.user+": "+message);
    }
	
	@OnClose  
    public void end(){  
		chatServerLogger.info("*** WebSocket closed from sessionId " + this.wsSession.getId() + " and User "+user);
		broadcast(wsSession, "System Message: "+ user + " lefted room.");
	} 
	
	private void send(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch(Exception ex) {
        	chatServerLogger.error("Error in sending message to " + session + ": " + ex);
        }
    }
	
	private void broadcast(Session session, String message){
		for (Session s : session.getOpenSessions()) {
            if (s.isOpen()) {
            	send(s, message);
            }
        }
	}
}
