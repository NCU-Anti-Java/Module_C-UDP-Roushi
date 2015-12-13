import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDP Server which receive message
 *
 * Created by fntsr on 2015/11/5.
 */
public abstract class UDPServer
{
    public void Start() throws Exception
    {
        final int SIZE = 1024;
        DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] receiveData = new byte[SIZE];

        // noinspection InfiniteLoopStatement
        while (true)
        {
            DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(packet);
            String message = new String(packet.getData(), packet.getOffset(), packet.getLength());
            onReceiveMessage(message);
        }
    }

    protected void onReceiveMessage(String message) throws Exception
    {
        throw new UnsupportedOperationException();
    }
}
