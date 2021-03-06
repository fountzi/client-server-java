package client_server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.StringTokenizer;

public class MyMessageReceiver implements Runnable, MySocketMessengerConstants
{
    private BufferedReader input;
    private MyMessageListener messageListener;
    private boolean keepListening= true;
    public MyMessageReceiver(MyMessageListener listener,Socket clientSocket)
    {
        messageListener=listener;
        try
        {
            clientSocket.setSoTimeout(5000);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (IOException ioException) 
        {
            ioException.printStackTrace();
        }
    }

    public void run()
    {
        String message;
        while(keepListening)
        {
            try 
            {
                message= input.readLine();   
            } 
            catch (SocketTimeoutException socketTimeoutException) {
                continue;
            }
            catch (IOException ioException){
                ioException.printStackTrace();
                break;
            }

            if(message!=null)
            {
                StringTokenizer tokenizer = new StringTokenizer(message,MESSAGE_SEPARATOR);
                if(tokenizer.countTokens()==2)
                {
                    messageListener.messageReceived(tokenizer.nextToken(),tokenizer.nextToken());   
                }
                else
                {
                    if(message.equalsIgnoreCase(MESSAGE_SEPARATOR+DISCONNECT_STRING))
                    stopListening();
                }
            }
            try
            {
                input.close();
            } 
            catch (IOException ioException) 
            {
                ioException.printStackTrace();
            }
           
        }

    }
    public void stopListening()
    {
        keepListening=false;
    }
    
}