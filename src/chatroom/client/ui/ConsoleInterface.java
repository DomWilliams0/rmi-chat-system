package chatroom.client.ui;

import chatroom.client.ChatClient;
import chatroom.protocol.message.Message;

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
		stream.print(message.format());
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
