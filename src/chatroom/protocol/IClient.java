package chatroom.protocol;

import chatroom.protocol.message.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote
{
	void receiveMessageFromServer(Message message) throws RemoteException;

	String getUsername() throws RemoteException;
}
