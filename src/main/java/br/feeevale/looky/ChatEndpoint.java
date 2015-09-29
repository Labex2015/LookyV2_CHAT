package br.feeevale.looky;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


//mvn embedded-glassfish:run  RUN SERVER

@ServerEndpoint(value = "/chat/{room}/{idFrom}/{user}", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint {
	private final Logger log = Logger.getLogger(getClass().getName());

	public static final String LEAVE = "LEAVE", LOGIN = "LOGIN", MESSAGE= "MESSAGE", FIRST = "FIRST";

	@OnOpen
	public void open(final Session session, @PathParam("room") final String room, @PathParam("idFrom") Long idFrom,
					 						@PathParam("user") String user){
		session.getUserProperties().put("idFrom", idFrom);
		session.getUserProperties().put("user", user);
		session.getUserProperties().put("room", room);
		Boolean isFirst = true;

		sendMessageForAll(session, LOGIN, null);
        if(session.getOpenSessions().size() > 1)
		    isFirst = false;

		if(isFirst) {
            try {
                session.getBasicRemote().sendObject(new ChatMessage(idFrom, 0L,
                        "Bem-vindo, não há ninguém online ainda.", user, new Date(), LOGIN));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        }


    }

	@OnMessage
	public void onMessage(final Session session, final ChatMessage chatMessage) {
		String room = (String) session.getUserProperties().get("room");
        try {
			for (Session s : session.getOpenSessions()) {
				if (s.isOpen()
						&& room.equals(s.getUserProperties().get("room"))) {
                    chatMessage.setType(MESSAGE);
					s.getBasicRemote().sendObject(chatMessage);
				}
			}
		} catch (IOException | EncodeException e) {
			log.log(Level.WARNING, "onMessage failed", e);
		}
	}

	@OnClose
	public void onClose(Session session){
        sendMessageForAll(session, LEAVE, null);
        session.getOpenSessions().remove(session);
	}

	private void sendMessageForAll(Session session,String type, ChatMessage message) {
        ChatMessage chatMessage = null;
        if (type.equals(LOGIN)) {
            chatMessage = new ChatMessage((Long) session.getUserProperties().get("idFrom"), 0L,
                    (String) session.getUserProperties().get("user") + " entrou na conversa.",
                    (String) session.getUserProperties().get("user"), new Date(), LOGIN);
        } else if (type.equals(MESSAGE)){
            chatMessage = message;
        }else if(type.equals(LEAVE)){
				chatMessage = new ChatMessage((Long)session.getUserProperties().get("idFrom"), 0L,
                        (String)session.getUserProperties().get("user")+" saiu da conversa.",
                        (String)session.getUserProperties().get("user"), new Date(),LEAVE);
		}
		String room = (String) session.getUserProperties().get("room");
        Long id = (Long) session.getUserProperties().get("idFrom");
		for (Session s : session.getOpenSessions()) {
			if (s.getUserProperties().get("room").equals(room) &&
                    (Long)s.getUserProperties().get("idFrom") != id) {
				try {
					s.getBasicRemote().sendObject(chatMessage);
				}catch (IOException e){
					e.printStackTrace();
				}catch (EncodeException e){
					e.printStackTrace();
				}
			}
		}
	}
}
