package chatroom.protocol;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote
{
	void receiveMessageFromServer(Message message) throws RemoteException;

	void receiveBroadcastFromServer(String message) throws RemoteException;

	String getUsername() throws RemoteException;
}
