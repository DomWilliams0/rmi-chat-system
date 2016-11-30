package chatroom.protocol.message;

public class ChatMessage extends Message
{
	private final String sender;

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
