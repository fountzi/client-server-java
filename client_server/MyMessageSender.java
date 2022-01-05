package client_server;
import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;

public class MyMessageSender implements Runnable, MySocketMessengerConstants
{
    private Socket clientSocket;
    private String messageToSend;

    public MyMessageSender(Socket socket, String userName, String message)
    {
        clientSocket=socket;
        messageToSend= userName+MESSAGE_SEPARATOR+message;
    }

    public void run()
    {
        try 
        {
            Formatter output= new Formatter(clientSocket.getOutputStream());    
            output.format("%s\n",messageToSend);
            output.flush();
        }
         catch (IOException ioException) 
         {
            ioException.printStackTrace();
        }
    }
}