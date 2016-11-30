package chatroom.server;

import chatroom.protocol.IClient;
import chatroom.protocol.IServer;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ChatServer extends UnicastRemoteObject implements IServer
{
	private Map<String, IClient> clients;

	public ChatServer() throws RemoteException
	{
		super();
		clients = new HashMap<>();
	}

	@Override
	public void sendMessage(String message) throws RemoteException
	{
		for (IClient client : clients.values())
			client.receiveMessageFromServer(message);
	}

	@Override
	public String join(IClient client) throws RemoteException
	{
		String username = client.getUsername();
		if (clients.containsKey(username))
			return "There is already a client connected with that username";

		clients.put(username, client);
		System.out.printf("Client '%s' joined\n", username);

		return null;
	}

	@Override
	public void quit(IClient client) throws RemoteException
	{
		String username = client.getUsername();

		if (clients.remove(username) != null)
			System.out.printf("Client '%s' quit\n", client.getUsername());
	}

	private void register()
	{
		try
		{
			Naming.rebind(IServer.SERVER_KEY, this);
			System.out.println("Server started");
		} catch (Exception e)
		{
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		try
		{
			ChatServer server = new ChatServer();
			server.register();
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

}
