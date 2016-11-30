package chatroom.server;

import chatroom.protocol.IClient;
import chatroom.protocol.IServer;
import chatroom.protocol.message.BroadcastMessage;
import chatroom.protocol.message.Message;

import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer extends UnicastRemoteObject implements IServer
{
	private final Map<String, IClient> clients;
	private final List<Message> messageHistory;

	private static final String CHAT_HISTORY_FILE = "chat_history.ser";
	private static final int HISTORY_BUFFER_SIZE = 5;


	public ChatServer() throws RemoteException
	{
		super();
		clients = new HashMap<>();
		messageHistory = new ArrayList<>();
		loadChatHistory();
	}

	@Override
	public void sendMessage(Message message) throws RemoteException
	{
		for (IClient client : clients.values())
			client.receiveMessageFromServer(message);

		addMessageToHistory(message);
	}

	private void broadcastMessage(String message) throws RemoteException
	{
		sendMessage(new BroadcastMessage(message));
	}

	@Override
	public String join(IClient client) throws RemoteException
	{
		String username = client.getUsername();
		if (clients.containsKey(username))
			return "There is already a client connected with that username";

		broadcastMessage(username + " joined");
		clients.put(username, client);

		// send history
		for (Message message : messageHistory)
			client.receiveMessageFromServer(message);

		System.out.printf("Client '%s' joined\n", username);

		return null;
	}

	@Override
	public void quit(IClient client) throws RemoteException
	{
		String username = client.getUsername();

		if (clients.remove(username) != null)
		{
			System.out.printf("Client '%s' quit\n", username);
			broadcastMessage(username + " quit");
		}
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

	private void addMessageToHistory(Message message)
	{
		messageHistory.add(message);

		if (messageHistory.size() % HISTORY_BUFFER_SIZE == 0)
			saveChatHistory();
	}

	private void saveChatHistory()
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CHAT_HISTORY_FILE, false));
			oos.writeObject(messageHistory);
			oos.close();
			System.out.printf("Saved %d messages from chat history to %s\n", messageHistory.size(), CHAT_HISTORY_FILE);

		} catch (IOException e)
		{
			System.err.printf("Failed to save chat history: %s\n", e.getMessage());
		}
	}

	private void loadChatHistory()
	{
		try
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CHAT_HISTORY_FILE));

			ArrayList<Message> newHistory = (ArrayList<Message>) ois.readObject();
			messageHistory.clear();
			messageHistory.addAll(newHistory);
			System.out.printf("Loaded %d messages into chat history from %s\n", newHistory.size(), CHAT_HISTORY_FILE);

			ois.close();
		} catch (IOException | ClassNotFoundException e)
		{
			System.err.printf("Failed to load chat history: %s\n", e.getMessage());
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
