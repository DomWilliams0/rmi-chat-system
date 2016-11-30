package chatroom.client.ui;

import chatroom.client.ChatClient;
import chatroom.protocol.IServer;
import chatroom.protocol.Message;

public interface IInterface
{
	/**
	 * Should block
	 */
	void start(ChatClient client);

	void displayMessage(Message message);

	void displayBroadcast(String message);

	static String formatMessage(Message message)
	{
		return String.format("[%s] %s\n", message.getSender(), message.getMessage());
	}

	static String formatBroadcast(String message)
	{
		return String.format("The server says: %s\n", message);
	}

	// TODO user list
}
