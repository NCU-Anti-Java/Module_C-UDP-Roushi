package AntiJava;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Vector;

/**
 * Created by fntsr on 2015/12/17.
 */
public class Sender
{
    public Vector<InetAddress> IPAddresses;
    public DatagramSocket clientSocket;

    public Sender() throws Exception
    {
        clientSocket = new DatagramSocket();
    }

    public void send(String message) throws Exception
    {
        for (InetAddress IPAddress: IPAddresses)
        {
            byte[] data = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
            clientSocket.send(sendPacket);

            clientSocket.close();
        }
    }
}