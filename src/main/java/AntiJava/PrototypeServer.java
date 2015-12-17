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
        ReceiveListener listener = new ReceiveListener();
        UDPServer server = new UDPServer();
        server.receiveListener = listener;
        server.start();
        server.run();
    }

    private static class ReceiveListener implements IReceiveListener
    {
        @Override
        public void onReceiveMessage(String message) throws Exception
        {
            String pos[] = message.split(" ");
            assert(pos.length == 2);
            System.out.println("(" + pos[0] + ", " + pos[1] + ") ");
        }
    }
}
