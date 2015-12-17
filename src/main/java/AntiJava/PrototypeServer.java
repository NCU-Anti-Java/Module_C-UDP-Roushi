package AntiJava;

/**
 * UDP Server endpoint of prototype homework
 *
 * Created by fntsr on 2015/11/5.
 */
public class PrototypeServer
{
    public static void main(String[] args) throws Exception
    {
        Receiver receiver = new Receiver();

        while (true)
        {
            String message = receiver.recieve();
            String pos[] = message.split(" ");

            if (pos.length == 2)
            {
                System.out.println("(" + pos[0] + ", " + pos[1] + ") ");
            }
        }
    }
}
