package chatroom.client.ui;

import chatroom.protocol.Message;

import javax.swing.*;

public class AnnoyingPopupInterface implements IInterface
{
	@Override
	public void displayMessage(Message message)
	{
		JOptionPane.showMessageDialog(null, message.getMessage(), "Message from " + message.getSender(), JOptionPane.INFORMATION_MESSAGE);
	}
}
