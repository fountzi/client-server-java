package client_server;

public interface MyMessageManager
{
    public void connect(MyMessageListener listener);
    public void disconnect(MyMessageListener listener);
    public void sendMessage (String from, String message);
}