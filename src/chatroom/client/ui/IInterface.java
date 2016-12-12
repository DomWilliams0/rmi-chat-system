package chatroom.client.ui;

import chatroom.client.ChatClient;
import chatroom.protocol.message.Message;

/**
 * Interface that represents a user interface
 */
public interface IInterface
{
	/**
	 * Starts the UI - this should block until the UI is closed
	 *
	 * @param client The client
	 */
	void start(ChatClient client);

	/**
	 * Displays the given message on the UI
	 *
	 * @param message The message to display
	 */
	void displayMessage(Message message);

	// TODO user list
}
