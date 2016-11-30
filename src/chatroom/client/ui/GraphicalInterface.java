package chatroom.client.ui;

import chatroom.client.ChatClient;
import chatroom.protocol.Message;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

public class GraphicalInterface implements IInterface
{
	private JDialog frame;
	private JTextArea history;

	@Override
	public void start(ChatClient client)
	{
		try
		{
			SwingUtilities.invokeAndWait(() ->
			{
				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
				{
					// no prettiness for you
				}


				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

				JPanel controlPanel = new JPanel();
				JTextField chatBox = new JTextField(12);
				JButton buttonSend = new JButton("Send");
				JButton buttonQuit = new JButton("Quit");

				controlPanel.add(chatBox);
				controlPanel.add(buttonSend);
				controlPanel.add(buttonQuit);

				JPanel historyPanel = new JPanel(new BorderLayout());
				int historyPad = 10;
				history = new JTextArea(12, 12);
				history.setEditable(false);
				JScrollPane historyScroll = new JScrollPane(history);
				historyPanel.add(historyScroll, BorderLayout.CENTER);
				historyPanel.setBorder(BorderFactory.createEmptyBorder(historyPad, historyPad, 0, historyPad));

				panel.add(historyPanel);
				panel.add(controlPanel);

				chatBox.addKeyListener(new KeyAdapter()
				{
					@Override
					public void keyPressed(KeyEvent e)
					{
						if (e.getKeyCode() == KeyEvent.VK_ENTER)
							buttonSend.doClick();

					}
				});
				buttonSend.addActionListener(new AbstractAction()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						client.sendMessage(chatBox.getText());
						SwingUtilities.invokeLater(() -> chatBox.setText(""));
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
			history.append(IInterface.formatMessage(message))
		);
	}

	@Override
	public void displayBroadcast(String message)
	{
		SwingUtilities.invokeLater(() ->
			history.append(IInterface.formatBroadcast(message))
		);
	}
}