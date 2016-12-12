package chatroom.protocol;

import chatroom.protocol.message.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Represents a chat client
 */
public interface IClient extends Remote
{
	/**
	 * Callback for received messages from the server
	 *
	 * @param message The received message
	 */
	void receiveMessageFromServer(Message message) throws RemoteException;

	/**
	 * @return The client's username
	 */
	String getUsername() throws RemoteException;
}
