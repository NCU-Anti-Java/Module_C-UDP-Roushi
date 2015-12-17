package AntiJava;
import java.util.Arrays;

/**
 * UDP Update AntiJava.Server is a thread which loops forever to get message from UDP
 * broadcast client and update the data in Dynamic Object Module.
 *
 * Created by fntsr on 2015/12/7.
 */
public class UDPUpdateServerModule implements Runnable
{
    private IDynamicObjectModule dynamicObjectModule;
    private Thread serverThread;
    private Boolean runningFlag = true;
    private Receiver receiver;

    /**
     * 建構子
     * @param receiver Receiver 的實例
     * @param dom DynamicObject 的實例
     */
    public UDPUpdateServerModule(Receiver receiver, IDynamicObjectModule dom)
    {
        this.receiver = receiver;
        this.dynamicObjectModule = dom;
    }

    /**
     * 初始化 UDP伺服器，並開始接收訊息
     * @throws Exception
     */
    public void initUDPServer() throws Exception
    {
        runningFlag = true;
        serverThread = new Thread(this);
        serverThread.start();
    }

    /**
     * 停止 UDP 伺服器
     * @throws Exception
     */
    public void stopUDPServer() throws Exception
    {
        runningFlag = false;
        serverThread.join();
    }

    /**
     * 當 UDP 收到訊息的 Calllback 方法
     * @param message 收到的訊息
     * @throws Exception
     */
    public void receiveMessage(String message) throws Exception
    {
        String[] tokens = message.split(" ");

        if (tokens.length <= 2)
        {
            return;
        }

        String[] args = Arrays.copyOfRange(tokens, 2, tokens.length);
        String command = tokens[0];
        String sprite = tokens[1];

        try
        {
            switch (command)
            {
                case "ADD":
                    add(sprite, args);
                    break;

                case "UPDATE":
                    update(sprite, args);
                    break;

                default:
                    System.out.println("Invalid command: " + command);
            }
        }
        catch (IllegalArgumentException e)
        {
            return;
        }
    }

    private void add(String sprite, String[] args) throws Exception
    {
        switch (sprite)
        {
            case "Character":
                addCharacter(args);
                break;

            case "Item":
                addItem(args);
                break;

            default:
                System.out.println("Invalid sprite: " + sprite);
        }
    }

    private void update(String sprite, String[] args) throws Exception
    {
        switch (sprite)
        {
            case "Character":
                updateCharacter(args);
                break;

            case "Item":
                updateItem(args);
                break;

            default:
                System.out.println("Invalid sprite: " + sprite);
        }
    }

    private void addCharacter(String[] args) throws Exception
    {
        if (args.length != 1) {
            return;
        }

        int clientNo = Integer.parseInt(args[0]);
        dynamicObjectModule.addVirtualCharacter(clientNo);
    }

    private void addItem(String[] args) throws Exception
    {
        if (args.length != 5) {
            return;
        }

        String name = args[0];
        int index = Integer.parseInt(args[1]);
        boolean shared = Boolean.parseBoolean(args[2]);
        int xPos = Integer.parseInt(args[3]);
        int yPos = Integer.parseInt(args[4]);
        dynamicObjectModule.addItem(name, index, shared, xPos, yPos);
    }

    private void updateCharacter(String[] args) throws Exception
    {
        if (args.length != 5) {
            return;
        }

        int clientNo = Integer.parseInt(args[0]);
        int dir = Integer.parseInt(args[1]);
        int speed = Integer.parseInt(args[2]);
        int xPos = Integer.parseInt(args[3]);
        int yPos = Integer.parseInt(args[4]);
        dynamicObjectModule.updateVirtualCharacter(clientNo, dir, speed, xPos, yPos);
    }

    private void updateItem(String[] args) throws Exception
    {
        if (args.length != 3) {
            return;
        }

        int index = Integer.parseInt(args[0]);
        boolean shared = Boolean.parseBoolean(args[1]);
        int owner = Integer.parseInt(args[2]);
        dynamicObjectModule.updateItem(index, shared, owner);
    }

    @Override
    public void run()
    {
        try
        {
            while (runningFlag)
            {
                String message = receiver.recieve();
                receiveMessage(message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
