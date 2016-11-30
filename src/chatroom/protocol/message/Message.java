package chatroom.protocol.message;

import java.io.Serializable;

public abstract class Message implements Serializable
{
	protected final String message;

	public Message(String message)
	{
		this.message = message;
	}

	public abstract String format();
}
