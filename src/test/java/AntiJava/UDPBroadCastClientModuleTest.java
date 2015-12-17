package AntiJava;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Vector;

import static org.mockito.Mockito.*;

/**
 *
 * Created by fntsr on 2015/12/17.
 */
public class UDPBroadCastClientModuleTest
{
    private UDPBroadCastClientModule udpBroadCastClientModule;
    private Sender sender;
    private ICentralizedDataCenter centralizedDataCenter;
    private ITCPServer tcpServer;

    @Before
    public void setUp() throws Exception
    {
        // Generate Mock Object
        sender = mock(Sender.class);
        centralizedDataCenter = mock(ICentralizedDataCenter.class);
        tcpServer = mock(ITCPServer.class);
        udpBroadCastClientModule = new UDPBroadCastClientModule(sender, centralizedDataCenter, tcpServer);

        // Prepare return value
        Vector<InetAddress> IPs = new Vector<>();
        IPs.add(InetAddress.getByName("127.0.0.1"));
        IPs.add(InetAddress.getByName("140.115.53.41"));

        Vector<IDynamicObject> dynamicObjects = new Vector<>();
        IDynamicObject character01 = mock(IDynamicObject.class);
        IDynamicObject character02 = mock(IDynamicObject.class);
        IDynamicObject item01 = mock(IDynamicObject.class);
        IDynamicObject item02 = mock(IDynamicObject.class);
        dynamicObjects.add(character01);
        dynamicObjects.add(character02);
        dynamicObjects.add(item01);
        dynamicObjects.add(item02);

        // stubbing appears before the actual execution
        when(character01.toString()).thenReturn("Character 1 3 10 0 0");
        when(character02.toString()).thenReturn("Character 2 4 15 0 0");
        when(item01.toString()).thenReturn("Item itemName1 0 true 0 0");
        when(item02.toString()).thenReturn("Item itemName2 1 false 2 2");
        when(tcpServer.getClientIPTable()).thenReturn(IPs);
        when(centralizedDataCenter.getUpdateInfo()).thenReturn(dynamicObjects);
    }

    @After
    public void tearDown() throws Exception
    {
        sender = null;
        centralizedDataCenter = null;
        tcpServer = null;
        udpBroadCastClientModule = null;
    }

    /**
     * 測試 Add 指令是否成功送出
     * @throws Exception
     */
   @Test
   public void testAddCommandReceived() throws Exception
   {
       udpBroadCastClientModule.startUDPBroadCast();
       Thread.sleep(1000);
       udpBroadCastClientModule.stopUDPBroadCast();

       verify(sender, times(1)).send("ADD Character 1 3 10 0 0");
       verify(sender, times(1)).send("ADD Character 2 4 15 0 0");
       verify(sender, times(1)).send("ADD Item itemName1 0 true 0 0");
       verify(sender, times(1)).send("ADD Item itemName2 1 false 2 2");
   }

    /**
     * 測試 Update 指令是否成功送出
     * @throws Exception
     */
    @Test
    public void testUpdateCommandReceived() throws Exception
    {
        udpBroadCastClientModule.startUDPBroadCast();
        Thread.sleep(1000);
        udpBroadCastClientModule.stopUDPBroadCast();

        verify(sender, atLeastOnce()).send("UPDATE Character 1 3 10 0 0");
        verify(sender, atLeastOnce()).send("UPDATE Character 1 3 10 0 0");
        verify(sender, atLeastOnce()).send("UPDATE Item itemName1 0 true 0 0");
        verify(sender, atLeastOnce()).send("UPDATE Item itemName2 1 false 2 2");
    }

    /**
     * 測試送出的頻率是否正常
     * @throws Exception
     */
    @Test
    public void testTimerNormal() throws Exception
    {
        udpBroadCastClientModule.startUDPBroadCast();
        Thread.sleep(1000);
        udpBroadCastClientModule.stopUDPBroadCast();

        verify(sender, times(5)).send("UPDATE Character 1 3 10 0 0");
    }
}