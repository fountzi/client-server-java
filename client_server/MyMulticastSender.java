package client_server;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MyMulticastSender implements Runnable, MySocketMessengerConstants
{
    private byte[] messageBytes;

    public MyMulticastSender(byte[] bytes)
    {
        messageBytes=bytes;
    }

    public void run()
    {
        try 
        {
            DatagramSocket socket= new DatagramSocket(MULTICAST_SENDING_PORT);
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            DatagramSocket packet= new DatagramSocket(messageBytes, messageBytes.length,group,MULTICAST_LISTENING_PORT);  
            socket.send(packet);
            socket.close();
        } 
        catch (IOException ioExceptions) 
        {
            ioException.printStackTrace();
        }
    }
}