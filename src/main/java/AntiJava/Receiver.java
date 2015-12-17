package AntiJava;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by fntsr on 2015/12/17.
 */
public class Receiver
{
    public String recieve() throws Exception
    {
        final int SIZE = 1024;
        byte[] receiveData = new byte[SIZE];

        DatagramSocket serverSocket = new DatagramSocket(9876);
        DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(packet);
        String message = new String(packet.getData(), packet.getOffset(), packet.getLength());

        return message;
    }
}
