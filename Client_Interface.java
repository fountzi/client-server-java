import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.border.BevelBorder;

public class Client_interface extends JFrame
{
    private JMenu serverMenu;
    private JTextArea messageArea;
    private JTextArea inputArea;
    private JButton connectButton;
    private JMenuIteam connectMenuItem;
    private JButton disconnectButton;
    private JMenuItem disconnectMenuItem;
    private JButton sendButton;
    private JLabel statusBar;
    private String userName;
    private MyMessageManager messageManager;
    private My_MessageListener messageListener;

    public Client_interface(MyMessageManager manager)
    {
        super("My messenger");
        messageManager=manager;
        messageListener= new My_MessageListener();
        serverMenu= new JMenu("Server");
        serverMenu.setMnemonic('S');
        JMenuBar menuBar= new JMenuBar();
        menuBar.add(serverMenu);
        Icon connectIcon= new ImageIcon(getClass().getResource("images/Connect.gif"));
        connectButton = new JButton("Connect",connectIcon);
        connectMenuItem = new JMenuItem("Connect",connectIcon);
        connectMenuItem.setMnemonic('C');
        ActionListener connectListener = new connectListener();
        connectButton.addActionListener(connectListener);
        
        Icon disconnectIcon = new ImageIcon(getClass.getResource("images/Disconnect.gif"));
        disconnectButton = new JButton("Disconnect",disconnectIcon);
        disconnectMenuItem = new JMenuItem("Disconnect", disconnectIcon);
        disconnectMenuItem.setMnemonic('D');
        disconnectButton.setEnabled(false);
        disconnectMenuItem.setEnabled(false);
        ActionListener disconnectListener = new DisconnectListener();
        disconnectButton.addActionListener(disconnectListener);
        disconnectMenuItem.addActionListener(disconnectListener);

        serverMenu.add(connectMenuItem);
        serverMenu.add(disconnectMenuItem);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(connectButton);
        buttonPanel.add(disconnectButton);
        messageArea= new JTextArea();
        messageArea.setEnabled(false);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        JPanel messagePanel= new JPanel();
        messagePanel.setLayout(new BorderLayout(10,10));
        messagePanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
        inputArea=new JTextArea(4,20);
        inputArea.setWrapStyleWord(true);
        inputArea.setLineWrap(true);
        inputArea.setEditable(false);
        Icon sendIcon = new ImageIcon(getClass().getResource("images/Send.gif"));
        sendButton= new JButton("Send",sendIcon);
        sendButton.setEnabled(false);
        
        sendButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event)
            {
                messageManager.sendMessage(userName,inputArea.getText());
                inputArea.setText("");

            }
        });

        Box box = new Box (BoxLayout.X_AXIS);
        box.add(new JScrollPane(inputArea));
        box.add(sendButton);
        messagePanel.add(box, BorderLayout.SOUTH);

        statusBar = new JLabel("Not Connected");
        statusBar.setBorder( new BevelBorder(BevelBorder.LOWERED));
        add(buttonPanel, BorderLayout.NORTH);
        add(messagePanel,BorderLayout.SOUTH);
        addWindowListener(new WindowAdapter(){
            public void windowClosing( WindowEvent event)
            {
                messageManager.disconnect(messageListener);
                System.exit(0);
            }
        });

    }

    private class ConnectListener implements ActionListener
    {
        public void actionPerfomed(ActionEvent event)
        {
            messsageManager.connect(messageListener);
            userName=JOptionPane.showInputDialog( client_interface.this, "Enter user name");
            messageArea.setText("");
            connectButton.setEnabled(false);
            connectMenuItem.setEnabled(false);
            disconnectButton.setEnabled(true);
            disconnectMenuItem.setEnabled(true);
            sendButton.setEnabled(true);
            inputArea.setEditable(true);
            inputArea.requestFocus();
            statusBar.setText("Connected: "+userName);

        }
    }

    private class DisconnectListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            messageManager.disconnect(messageListener);
            sendButton.setEnabled(false);
            disconnectButton.setEnabled(false);
            disconnectMenuItem.setEnabled(false);
            inputArea.setEditable(false);
            connectButton.setEnabled(true);
            connectMenuItem.setEnabled(true);
            statusBar.setText("Not Connected");

        }
    }
    private class My_MessageListener implements My_MessageListener
    {
        public void messageReceived ( String from, String message)
        {
            SwingUtilities.invokeLater(new MessageDisplayer(from, message));

        }
    }

    private class MessageDisplayer implements Runnable
    {
        private String fromUser;
        private MessageDisplayer(String from, String body)
        {
            fromUser=from;
            messageBody=body;

        }
        public void run()
        {
            messageArea.append("/n"+fromUser+"> "+messageBody);
        }
    }
}