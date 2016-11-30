package chatroom.client.ui;

import javax.swing.*;

public class AnnoyingPopupInterface implements IInterface
{
	@Override
	public void displayMessage(String message)
	{
		JOptionPane.showMessageDialog(null, "New message: " + message, "Message!", JOptionPane.INFORMATION_MESSAGE);
	}
}
