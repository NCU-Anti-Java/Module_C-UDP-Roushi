import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by fntsr on 2015/11/5.
 */
public class UDPServer
{
    public void Start() throws Exception
    {
        Connect();
    }

    private void Connect() throws Exception
    {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] receiveData = new byte[1024];

        while (true)
        {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            String sentence = new String(receivePacket.getData());
            String pos[] = sentence.split(" ");
            assert(pos.length == 2);
            System.out.println("(" + pos[0] + ", " + pos[1] + ")");
        }
    }
}
