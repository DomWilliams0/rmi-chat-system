package chatroom.client;

import chatroom.client.ui.ConsoleInterface;
import chatroom.client.ui.GraphicalInterface;
import chatroom.client.ui.IInterface;
import chatroom.protocol.IClient;
import chatroom.protocol.IServer;
import chatroom.protocol.message.ChatMessage;
import chatroom.protocol.message.Message;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatClient extends UnicastRemoteObject implements IClient
{
	private IServer server;
	private String username;

	private IInterface ui;

	public ChatClient(String username, IInterface ui) throws RemoteException
	{
		super();
		this.username = username;
		this.ui = ui;
	}

	private boolean lookupServer(String host)
	{
		try
		{
			Registry registry = LocateRegistry.getRegistry(host);
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
			String error;
			if ((error = server.join(this)) != null)
			{
				System.err.printf("Failed to connect to server: %s\n", error);
				return false;
			}

			// disconnect on quit
			Runtime.getRuntime().addShutdownHook(new Thread(this::quit));

			ui.start(this);

		} catch (RemoteException e)
		{
			e.printStackTrace();
		}

		return true;
	}

	public void sendMessage(String message)
	{
		try
		{
			if (!message.isEmpty())
				server.sendMessage(new ChatMessage(username, message));
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

	public void quit()
	{
		try
		{
			server.quit(this);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws RemoteException
	{
		if (args.length < 2 || args.length > 3)
		{
			System.err.println("Usage: <username> <gui|console> [host ( = localhost)]");
			System.exit(1);
			return;
		}

		String username = args[0];
		boolean gui = args[1].equals("gui");
		String host = args.length == 3 ? args[2] : null;

		IInterface ui = gui ? new GraphicalInterface() : new ConsoleInterface(System.out);
		ChatClient chatClient = new ChatClient(username, ui);

		boolean success = false;

		if (chatClient.lookupServer(host))
		{
			success = chatClient.start();
		}

		System.exit(success ? 0 : 1);
	}

	@Override
	public void receiveMessageFromServer(Message message) throws RemoteException
	{
		ui.displayMessage(message);
	}

	@Override
	public String getUsername() throws RemoteException
	{
		return username;
	}
}
