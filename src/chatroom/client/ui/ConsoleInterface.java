package chatroom.client.ui;

import chatroom.protocol.Message;

import java.io.PrintStream;

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
		stream.printf("[%s] %s\n", message.getSender(), message.getMessage());
	}
}
