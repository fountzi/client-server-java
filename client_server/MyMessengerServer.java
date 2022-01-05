package client_server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyMessengerServer implements MyMessageListener
{
    private ExecutorService serverExecutor;
    public void startServer()
    {
        serverExecutor= Executors.newCachedThreadPool();
        try 
        {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT,100);
            System.out.println("%s%d%s","Server listening on port", SERVER_PORT,"...");
            while(true)
            {
                Socket clientSocket=serverSocket.accept();
                serverExecutor.execute(new MyMessageReceiver(this.clientSocket));
                System.out.println("Connection received from: "+clientSocket.getInetAddress());

            }
        } 
        catch (IOException ioException) 
        {
            ioException.printStackTrace();
        }
    }
    
    public void messageReceived(String from, String message)
    {
        String completeMessage = from + MESSAGE_SEPARATOR+ message;
        serverExecutor.execute(new MyMulticastSender(completeMessage.getBytes()));
    }
}
