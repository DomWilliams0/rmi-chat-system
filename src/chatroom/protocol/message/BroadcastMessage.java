package chatroom.protocol.message;

public class BroadcastMessage extends Message
{
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
