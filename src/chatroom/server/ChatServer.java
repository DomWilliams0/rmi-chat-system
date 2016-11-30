package chatroom.server;

import chatroom.protocol.IClient;
import chatroom.protocol.IServer;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatServer implements IServer
{

	@Override
	public void sendMessage(String message) throws RemoteException
	{
		System.out.printf("Sending message '%s'\n", message);
	}

	@Override
	public boolean join(IClient client) throws RemoteException
	{
		System.out.printf("Client '%s' joined\n", client.getUsername());
		return true;
	}

	@Override
	public void quit(IClient client) throws RemoteException
	{
		System.out.printf("Client '%s' quit\n", client.getUsername());
	}

	private void register()
	{
		try
		{
			IServer stub = (IServer) UnicastRemoteObject.exportObject(this, 0);

			Registry registry = LocateRegistry.getRegistry();
			registry.bind(IServer.SERVER_KEY, stub);

			System.out.println("Server started");
		} catch (Exception e)
		{
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		ChatServer server = new ChatServer();
		server.register();
	}

}
