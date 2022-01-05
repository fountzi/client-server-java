package client_server;

public class MyClientMessenger
{
    public static void main(String args[])
    {
        MyMessageManager messageManager;
        if(args.length==0) messageManager= new MySocketMessageManager("localhost");
        else messageManager = new MySocketMessageManager(args[0]);

        Client_interface clientGUI= new Client_interface(messageManager);
        clientGUI.setSize(300,400);
        clientGUI.setResizable(false);
        clientGUI.setVisible(true);
    }
}
