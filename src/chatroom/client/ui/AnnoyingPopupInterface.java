package chatroom.client.ui;

import chatroom.client.ChatClient;
import chatroom.protocol.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

public class AnnoyingPopupInterface implements IInterface
{
	private JDialog frame;

	@Override
	public void start(ChatClient client)
	{
		try
		{
			SwingUtilities.invokeAndWait(() ->
			{
				JPanel panel = new JPanel();
				JTextField field = new JTextField(12);
				JButton buttonSend = new JButton("Send");
				JButton buttonQuit = new JButton("Quit");

				panel.add(field);
				panel.add(buttonSend);
				panel.add(buttonQuit);

				buttonSend.addActionListener(new AbstractAction()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						client.sendMessage(field.getText());
						SwingUtilities.invokeLater(() -> field.setText(""));
					}
				});

				buttonQuit.addActionListener(new AbstractAction()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						client.quit();
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					}
				});

				frame = new JDialog();
				frame.setModal(true);
				frame.setContentPane(panel);
				frame.pack();
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			});
		} catch (InterruptedException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void displayMessage(Message message)
	{
		SwingUtilities.invokeLater(() ->
			JOptionPane.showMessageDialog(frame, message.getMessage(),
				"Message from " + message.getSender(), JOptionPane.INFORMATION_MESSAGE));
	}
}
