package chatroom.protocol;

import chatroom.protocol.message.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Represents the chat server
 */
public interface IServer extends Remote
{
	String SERVER_KEY = "ChatroomServer";

	/**
	 * Sends the given message to the server, and in effect to all other clients connected to the server
	 * @param message The message to send
	 */
	void sendMessage(Message message) throws RemoteException;

	/**
	 * Attempts to connect the given client to the server
	 * @param client The client to connect
	 * @return An error message, or null if successful
	 */
	String join(IClient client) throws RemoteException;

	/**
	 * Disconnects the given client from the server
	 * @param client The client to disconnect
	 */
	void quit(IClient client) throws RemoteException;
}
