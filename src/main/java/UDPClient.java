import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by fntsr on 2015/11/5.
 */
public class UDPClient
{
    public static void main(String args[]) throws Exception
    {
        BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));

        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");

        byte[] sendData;
        byte[] receiveData = new byte[1024];

        String sentence = keyIn.readLine();
        sendData = sentence.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        clientSocket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);

        String receiveSentence = new String(receivePacket.getData());
        System.out.println("Receive: " + receiveSentence);
        clientSocket.close();
    }
}
