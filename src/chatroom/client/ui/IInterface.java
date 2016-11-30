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

	// TODO user list
}
