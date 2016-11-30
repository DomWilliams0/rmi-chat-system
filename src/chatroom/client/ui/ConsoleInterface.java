package chatroom.client.ui;

import chatroom.client.ChatClient;
import chatroom.protocol.Message;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleInterface implements IInterface
{
	private final PrintStream stream;

	public ConsoleInterface(PrintStream stream)
	{
		this.stream = stream;
	}

	@Override
	public void displayMessage(Message message)
	{
		stream.flush();
		stream.printf("[%s] %s\n", message.getSender(), message.getMessage());
	}

	@Override
	public void displayBroadcast(String message)
	{
		stream.flush();
		stream.printf("The server says: %s\n", message);
	}

	@Override
	public void start(ChatClient client)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter a message to send, or /quit to leave");
		while (true)
		{
			String line = scanner.nextLine();
			if (line.equals("/quit"))
			{
				break;
			}

			client.sendMessage(line);
		}

		scanner.close();
	}
}
