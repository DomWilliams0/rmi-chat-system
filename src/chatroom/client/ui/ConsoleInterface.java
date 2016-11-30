package chatroom.client.ui;

import java.io.PrintStream;

public class ConsoleInterface implements IInterface
{
	private final PrintStream stream;

	public ConsoleInterface(PrintStream stream)
	{
		this.stream = stream;
	}

	@Override
	public void displayMessage(String message)
	{
		stream.printf("[UNKNOWN] %s\n", message);
	}
}
