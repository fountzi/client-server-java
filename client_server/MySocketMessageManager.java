package client_server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MySocketMessageManager implements MyMessageManager, MySocketMessengerConstants
{
    private Socket clientSocket;
    private String serverAddress;
    private MyPacketReceiver receiver;
    private boolean connected = false;
    private ExecutorService serverExecutor;

    public MySocketMessageManager(String address)
    {
        serverAddress= address;
        serverExecutor= Executors.newCachedThreadPool();
    }
    public void connect(MyMessageListener listener)
    {
        if(connected)
            return;
        try 
        {
            clientSocket= new Socket(InetAddress.getByName(serverAddress),SERVER_PORT);
            receiver= new MyPacketReceiver(listener);
            serverExecutor.execute(receiver);
            connected= true;    
        } 
        catch (IOException ioExceptions) 
        {
            ioExceptions.printStackTrace();
        }
        
    }

    public void disconnect(MyMessageListener listener)
    {
        if(!connected) return;
        try 
        {
            Runnable disconnecter= new MyMessageSender(clientSocket,"",DISCONNECT_STRING);
            Future disconnecting = serverExecutor.submit(disconnecter);
            disconnecting.get();
            receiver.stopListening();
            clientSocket.close();


                
        } 
        catch (ExecutionException  exception) 
        {
            exception.printStackTrace();
        }
        catch(InterruptedException exception)
        {
            exception.printStackTrace();
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
        connected=false;
    }

    public void sendMessage(String from,String message)
    {
        if(!connected) return;
        serverExecutor.execute(new MyMessageSender(clientSocket,from,message));
        
    }
}
