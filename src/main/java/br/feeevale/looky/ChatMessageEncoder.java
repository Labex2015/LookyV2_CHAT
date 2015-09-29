package br.feeevale.looky;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {

    private final Logger log = Logger.getLogger(getClass().getName());

	@Override
	public void init(final EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public String encode(final ChatMessage chatMessage) throws EncodeException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String message = Json.createObjectBuilder()
				.add("message", chatMessage.getMessage())
				.add("sender", chatMessage.getSender())
				.add("received", simpleDateFormat.format(chatMessage.getReceived()))
                .add("idFrom", chatMessage.getIdFrom())
                .add("idTo", chatMessage.getIdTo())
                .add("type", chatMessage.getType()).build().toString();

        log.info("Enviando mensagem: "+message);
        return message;
	}
}
