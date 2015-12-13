import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Vector;

/**
 * UDP Client which send message
 *
 * Created by fntsr on 2015/11/5.
 */
public abstract class UDPClient
{
    public int frequency;
    protected Vector<InetAddress> IPAddresses;

    public UDPClient() throws Exception
    {
        IPAddresses = new Vector<>();
    }

    public UDPClient(String[] IPs) throws Exception
    {
        IPAddresses = new Vector<>();

        for (String IP : IPs)
        {
            IPAddresses.add(InetAddress.getByName(IP));
        }
    }

    public void sendData(String message) throws Exception
    {
        byte[] data = message.getBytes();

        DatagramSocket clientSocket = new DatagramSocket();

        for (InetAddress IPAddress: IPAddresses)
        {
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
            clientSocket.send(sendPacket);
        }

        clientSocket.close();
    }

    public void start() throws Exception
    {
        // default frequency setting if it not be initialized.
        frequency = (frequency == 0) ? 10 : frequency;

        // noinspection InfiniteLoopStatement
        while(true)
        {
            Thread.sleep(1000/frequency);
            onCycle();
        }
    }

    protected void onCycle() throws Exception
    {
        throw new UnsupportedOperationException();
    }
}
