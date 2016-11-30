package chatroom.client.ui;

import chatroom.client.ChatClient;
import chatroom.protocol.message.Message;

public interface IInterface
{
	/**
	 * Should block
	 */
	void start(ChatClient client);

	void displayMessage(Message message);

	// TODO user list
}
