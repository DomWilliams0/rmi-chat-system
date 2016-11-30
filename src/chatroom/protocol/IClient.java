package chatroom.protocol;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote
{
	void receiveMessageFromServer(String message) throws RemoteException;

	String getUsername() throws RemoteException;
}
