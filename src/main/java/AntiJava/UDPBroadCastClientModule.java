package AntiJava;
import java.util.Vector;

/**
 * UDP BroadCast client is a thread which loops forever to get information from
 * Centralized Data Center and broadcast to all the client computer.
 *
 * Created by fntsr on 2015/12/7.
 */
public class UDPBroadCastClientModule implements Runnable
{
    private Sender sender;
    private ICentralizedDataCenter centralizedDataCenter;
    private ITCPServer tcpServer;
    private Thread broadcastThread;
    private Boolean runningFlag;
    private final int PERIOD = 200;

    /**
     * 建構子，並初始化頻率
     * @param sender Sender 實例
     * @param dataCenter CentralizedDataCenter 實例
     * @param tcpServer TCP Server 實例
     * @throws Exception
     */
    public UDPBroadCastClientModule(Sender sender, ICentralizedDataCenter dataCenter, ITCPServer tcpServer) throws Exception
    {
        super();
        this.sender = sender;
        this.centralizedDataCenter = dataCenter;
        this.tcpServer = tcpServer;
    }

    public enum SendCommands
    {
        ADD,
        UPDATE
    }

    /**
     * called by main program of server computer when the all the connection is
     * established and the networked game is started // The method starts the UDP Broadcast thread.
     */
    public void startUDPBroadCast() throws Exception
    {
        runningFlag = true;
        broadcastThread = new Thread(this);
        broadcastThread.start();

    }

    public void stopUDPBroadCast() throws Exception
    {
        runningFlag = false;
        broadcastThread.join();
    }

    public void sendInfo(SendCommands command) throws Exception
    {
        Vector<IDynamicObject> updatedDynamicObjects = centralizedDataCenter.getUpdateInfo();

        for (IDynamicObject dynamicObject : updatedDynamicObjects)
        {
            String commandToken = command.toString().toUpperCase();
            String message = commandToken + " " + dynamicObject.toString();

            sender.send(message);
        }
    }

    @Override
    public void run()
    {
        try
        {
            sender.IPAddresses = tcpServer.getClientIPTable();
            sendInfo(SendCommands.ADD);

            while(runningFlag)
            {
                Thread.sleep(PERIOD);
                sendInfo(SendCommands.UPDATE);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
