import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by fntsr on 2015/11/5.
 */
public class Client
{
    public static void main(String[] args) throws Exception
    {
        String[] IPAddresses = new String[UDPClient.ConnectionCount];
        BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < UDPClient.ConnectionCount; i++)
        {
            IPAddresses[i] = keyIn.readLine();
        }

        UDPClient client = new UDPClient(IPAddresses);
        client.Start();
    }
}
