package br.feeevale.looky;

import java.io.StringReader;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {

    private final Logger log = Logger.getLogger(getClass().getName());

	@Override
	public void init(final EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public ChatMessage decode(final String textMessage) throws DecodeException {
		ChatMessage chatMessage = new ChatMessage();
        try {
            JsonObject obj = Json.createReader(new StringReader(textMessage))
                    .readObject();
            chatMessage.setMessage(obj.getString("message"));
            chatMessage.setSender(obj.getString("sender"));
            Long idFrom = Long.parseLong(String.valueOf(obj.get("idFrom") == null ? 0 : obj.get("idFrom")));
            Long idTo = Long.parseLong(String.valueOf(obj.get("idTo") == null ? 0 : obj.get("idTo")));
            chatMessage.setIdFrom(idFrom);
            chatMessage.setIdTo(idTo);
            chatMessage.setType(obj.getString("type") == null ? "" : obj.getString("type"));
            chatMessage.setReceived(new Date());
        }catch (Exception e){
            log.log(Level.WARNING,"Erro ao converter mensagem", e);
        }
        log.info("Retornonando mensagem: "+chatMessage.toString());
		return chatMessage;
	}

	@Override
	public boolean willDecode(final String s) {
		return true;
	}
}
