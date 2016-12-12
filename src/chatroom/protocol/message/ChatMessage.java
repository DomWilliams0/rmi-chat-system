package chatroom.protocol.message;

/**
 * Represents a message from another client
 */
public class ChatMessage extends Message
{
	private final String sender;

	/**
	 * @param sender  The sender of this message
	 * @param message The message's content
	 */
	public ChatMessage(String sender, String message)
	{
		super(message);
		this.sender = sender;
	}

	@Override
	public String format()
	{
		return String.format("[%s] %s\n", sender, message);
	}

}
