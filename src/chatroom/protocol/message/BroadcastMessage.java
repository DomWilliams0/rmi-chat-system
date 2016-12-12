package chatroom.protocol.message;

/**
 * Represents a message from the server
 */
public class BroadcastMessage extends Message
{
	/**
	 * @param message The message's content
	 */
	public BroadcastMessage(String message)
	{
		super(message);
	}

	@Override
	public String format()
	{
		return String.format("The server says: %s\n", message);
	}
}
