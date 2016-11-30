package chatroom.client;

import chatroom.protocol.IClient;
import chatroom.protocol.IServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatClient implements IClient, Serializable
{
	private IServer server;
	private String username;

	public ChatClient(String username)
	{
		this.username = username;
	}

	private boolean lookupServer(String host, int port)
	{
		try
		{
			Registry registry = LocateRegistry.getRegistry(host, port);
			server = (IServer) registry.lookup(IServer.SERVER_KEY);
			return true;
		} catch (Exception e)
		{
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
			return false;
		}
	}

	private boolean start()
	{
		try
		{
			// join server
			System.out.println("Joining server");
			if (!server.join(this))
				return false;

			// send test message
			System.out.println("Sending test message");
			server.sendMessage("hello!");

			// leave
			System.out.println("Disconnecting");
			server.quit(this);

		} catch (RemoteException e)
		{
			e.printStackTrace();
		}

		return true;
	}


	public static void main(String[] args)
	{
		// TODO args
		String username = "alice";
		String host = null;
		int port = 0;

		ChatClient chatClient = new ChatClient(username);

		boolean success = false;
		if (chatClient.lookupServer(host, port))
		{
			success = chatClient.start();
		}

		System.exit(success ? 0 : 1);
	}

	@Override
	public void receiveMessageFromServer(String message) throws RemoteException
	{
		System.out.println("message = " + message);
	}

	@Override
	public String getUsername() throws RemoteException
	{
		return username;
	}
}
