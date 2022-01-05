package client_server;
public interface MySocketMessengerConstants
{
    public static final String MULTICAST_ADDRESS="239.0.0.1";
    public static final int MULTICAST_PORT=5555;
    public static final int MULTICAST_SENDIND_PORT=5554;
    public static final int SERVER_PORT=12345;
    public static final String DISCONNECT_STRING="DISCONNECT";
    public static final String MESSAGE_SEPARATOR=">>>";
    public static final int MESSAGE_SIZE= 512;

}