package AntiJava;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * UDP Client endpoint of prototype homework
 *
 * Created by fntsr on 2015/11/5.
 */
public class PrototypeClient
{
    private static final int ConnectionCount = 1;

    public static void main(String[] args) throws Exception
    {
        int xPos = 0;
        int yPos = 0;
        int counter = 0;
        UDPClient client;
        String[] IPAddresses = new String[ConnectionCount];
        BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please Enter " + ConnectionCount + " IP address");
        for (int i = 0; i < ConnectionCount; i++)
        {
            IPAddresses[i] = keyIn.readLine();
        }

        System.out.println("Start Client");
        client = new UDPClient(IPAddresses);

        //noinspection InfiniteLoopStatement
        while (true)
        {
            counter += 2;
            String sentence = xPos + " " + yPos;

            if (counter == 20)
            {
                xPos = xPos > 99 ? 0 : xPos + 1;
                yPos = yPos > 99 ? 0 : yPos + 1;
                counter = 0;
            }
            client.sendData(sentence);
            Thread.sleep(200);
        }
    }
}
