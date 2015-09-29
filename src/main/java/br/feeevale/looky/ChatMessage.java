package br.feeevale.looky;

import java.util.Date;

public class ChatMessage {

    public static final String LOGIN = "LOGIN";
    public static final String LEAVE = "LEAVE";
    public static final String MESSAGE = "MESSAGE";
    public static final String FIRST = "FIRST";

	private Long idFrom;
	private Long idTo;
	private String message;
	private String sender;
	private Date received;
	private String type;


	public ChatMessage() {
	}

	public ChatMessage(Long idFrom, Long idTo, String message, String sender, Date received) {
		this.idFrom = idFrom;
		this.idTo = idTo;
		this.message = message;
		this.sender = sender;
		this.received = received;
	}

	public ChatMessage(Long idFrom, Long idTo, String message, String sender, Date received, String type) {
		this.idFrom = idFrom;
		this.idTo = idTo;
		this.message = message;
		this.sender = sender;
		this.received = received;
		this.type = type;
	}

	public final String getMessage() {
		return message;
	}

	public final void setMessage(final String message) {
		this.message = message;
	}

	public final String getSender() {
		return sender;
	}

	public final void setSender(final String sender) {
		this.sender = sender;
	}

	public final Date getReceived() {
		return received;
	}

	public final void setReceived(final Date received) {
		this.received = received;
	}

	public Long getIdFrom() {
		return idFrom;
	}

	public void setIdFrom(Long idFrom) {
		this.idFrom = idFrom;
	}

	public Long getIdTo() {
		return idTo;
	}

	public void setIdTo(Long idTo) {
		this.idTo = idTo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ChatMessage [message=" + message + ", sender=" + sender
				+""+idFrom+"}, received=" + received + ", idFrom="+idFrom+", idTo="+idTo+"]";
	}
}
