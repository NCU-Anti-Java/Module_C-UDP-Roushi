package AntiJava.UDPUpdateServer;

/**
 * UDP Server endpoint of prototype homework
 *
 * Created by fntsr on 2015/11/5.
 */
public class Server
{
    public static void main(String[] args) throws Exception
    {
        MyServer server = new MyServer();
        server.start();
    }

    private static class MyServer extends UDPServer
    {
        @Override
        protected void onReceiveMessage(String message) throws Exception
        {
            String pos[] = message.split(" ");
            assert(pos.length == 2);
            System.out.println("(" + pos[0] + ", " + pos[1] + ") ");
        }
    }
}
