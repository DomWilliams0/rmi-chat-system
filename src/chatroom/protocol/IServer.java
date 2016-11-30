package chatroom.protocol;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote
{
	String SERVER_KEY = "ChatroomServer";

	void sendMessage(Message message) throws RemoteException;

	void broadcastMessage(String message) throws RemoteException;

	String join(IClient client) throws RemoteException;

	void quit(IClient client) throws RemoteException;
}
