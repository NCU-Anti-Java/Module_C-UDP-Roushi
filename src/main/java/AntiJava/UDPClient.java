package AntiJava;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Vector;

/**
 * UDP Client which send message
 *
 * Created by fntsr on 2015/11/5.
 */
public class UDPClient
{
    public Vector<InetAddress> IPAddresses;
    private Sender sender;

    public UDPClient() throws Exception
    {
        IPAddresses = new Vector<>();
        sender = new Sender();
    }

    public UDPClient(String[] IPs) throws Exception
    {
        for (String IP : IPs)
        {
            sender.IPAddresses.add(InetAddress.getByName(IP));
        }
    }

    public void sendData(String message) throws Exception
    {
        sender.send(message);
    }
}
