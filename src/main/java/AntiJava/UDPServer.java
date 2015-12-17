package AntiJava;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * UDP Server which receive message
 *
 * Created by fntsr on 2015/11/5.
 */
public class UDPServer implements Runnable
{
    public IReceiveListener receiveListener;
    private Boolean runningFlag = true;

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
        final int SIZE = 1024;
        byte[] receiveData = new byte[SIZE];

        DatagramSocket serverSocket = null;
        try
        {
            serverSocket = new DatagramSocket(9876);
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }

        while (runningFlag)
        {
            assert serverSocket != null;

            try
            {
                DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(packet);
                String message = new String(packet.getData(), packet.getOffset(), packet.getLength());

                if (null != receiveListener)
                {
                    receiveListener.onReceiveMessage(message);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
