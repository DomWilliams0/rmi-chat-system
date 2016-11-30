package chatroom.protocol;

import java.io.Serializable;

public class Message implements Serializable
{
	private final String sender;
	private final String message;
	// TODO timestamp

	public Message(String sender, String message)
	{
		this.sender = sender;
		this.message = message;
	}

	public String getSender()
	{
		return sender;
	}

	public String getMessage()
	{
		return message;
	}
}