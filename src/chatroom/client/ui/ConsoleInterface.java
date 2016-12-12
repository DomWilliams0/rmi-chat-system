package chatroom.client.ui;

import chatroom.client.ChatClient;
import chatroom.protocol.message.Message;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Represents a simple command line UI
 */
public class ConsoleInterface implements IInterface
{
	private final PrintStream out;
	private final InputStream in;

	/**
	 * @param out The stream to write output to
	 * @param in  The stream to read input from
	 */
	public ConsoleInterface(PrintStream out, InputStream in)
	{
		this.out = out;
		this.in = in;
	}

	@Override
	public void displayMessage(Message message)
	{
		out.flush();
		out.print(message.format());
	}

	@Override
	public void start(ChatClient client)
	{
		Scanner scanner = new Scanner(in);
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
