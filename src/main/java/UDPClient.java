import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by fntsr on 2015/11/5.
 */
public class UDPClient
{
    public static final int ConnectionCount = 2;

    private int xPos;
    private int yPos;
    private InetAddress[] IPAddresses;

    UDPClient(String[] IPs) throws Exception
    {
        xPos = 0;
        yPos = 0;
        IPAddresses = new InetAddress[ConnectionCount];

        if (IPs.length != ConnectionCount)
        {
            throw new Exception();
        }

        for (int i = 0; i < ConnectionCount; i++)
        {
            IPAddresses[i] = InetAddress.getByName(IPs[i]);
        }
    }

    public void Start() throws Exception
    {
        int counter = 0;
        while(true)
        {
            Thread.sleep(200);
            counter += 2;
            SendData();

            if (counter == 20)
            {
                xPos = xPos > 99 ? 0 : xPos + 1;
                yPos = yPos > 99 ? 0 : yPos + 1;
                counter = 0;
            }
        }
    }

    private void SendData() throws Exception
    {
        DatagramSocket clientSocket = new DatagramSocket();
        byte[] sendData;

        String sentence = xPos + " " + yPos;
        sendData = sentence.getBytes();

        for (int i = 0; i < ConnectionCount; i++)
        {
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddresses[i], 9876);
            clientSocket.send(sendPacket);
        }

        clientSocket.close();
    }
}
