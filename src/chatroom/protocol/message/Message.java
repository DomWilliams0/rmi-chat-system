package chatroom.protocol.message;

import java.io.Serializable;

/**
 * Abstract class representing a message
 */
public abstract class Message implements Serializable
{
	protected final String message;

	/**
	 * @param message The message's content
	 */
	public Message(String message)
	{
		this.message = message;
	}

	/**
	 * @return A formatted string displaying this message
	 */
	public abstract String format();
}
