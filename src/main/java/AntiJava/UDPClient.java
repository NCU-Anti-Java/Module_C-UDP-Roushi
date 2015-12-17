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
public class UDPClient implements Runnable
{
    public int frequency = 10;
    public ISenderListener sendListener;
    public Vector<InetAddress> IPAddresses;
    private Boolean runningFlag = true;


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

    public void start()
    {
        runningFlag = true;
    }

    public void stop()
    {
        runningFlag = false;
    }

    @Override
    public void run()
    {
        assert frequency != 0;
        while(runningFlag)
        {
            try
            {
                Thread.sleep(1000/frequency);

                if (null != sendListener)
                {
                    sendListener.onCycle();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
