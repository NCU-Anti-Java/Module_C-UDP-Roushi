package AntiJava;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Vector;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

/**
 * Created by fntsr on 2015/12/17.
 */
public class UDPUpdateServerModuleTest
{
    private Receiver receiver;
    private IDynamicObjectModule dynamicObjectModule;
    private UDPUpdateServerModule udpUpdateServerModule;

    @Before
    public void setUp() throws Exception
    {
        // Generate Mock Object
        receiver = mock(Receiver.class);
        dynamicObjectModule = mock(IDynamicObjectModule.class);
        udpUpdateServerModule = new UDPUpdateServerModule(receiver, dynamicObjectModule);
    }

    @After
    public void tearDown() throws Exception
    {
        receiver = null;
        dynamicObjectModule = null;
        udpUpdateServerModule = null;
    }

    /**
     * 測試沒收到任意訊息是否不會有任何反應
     * @throws Exception
     */
    @Test
    public void testReceiveNothing() throws Exception
    {
        // stubbing appears before the actual execution
        when(receiver.recieve()).thenReturn("");

        udpUpdateServerModule.initUDPServer();
        Thread.sleep(1000);
        udpUpdateServerModule.stopUDPServer();

        verify(dynamicObjectModule, never()).addVirtualCharacter(anyInt());
        verify(dynamicObjectModule, never()).addItem(anyString(), anyInt(), anyBoolean(), anyInt(), anyInt());
        verify(dynamicObjectModule, never()).updateVirtualCharacter(anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
        verify(dynamicObjectModule, never()).updateItem(anyInt(), anyBoolean(), anyInt());
    }

    /**
     * 測試新增角色是否正常運作
     * @throws Exception
     */
    @Test
    public void testAddCharacter() throws Exception
    {
        // stubbing appears before the actual execution
        when(receiver.recieve())
                .thenReturn("ADD Character 1")
                .thenReturn("ADD Character 2")
                .thenReturn("");

        udpUpdateServerModule.initUDPServer();
        Thread.sleep(1000);
        udpUpdateServerModule.stopUDPServer();

        verify(dynamicObjectModule, times(1)).addVirtualCharacter(1);
        verify(dynamicObjectModule, times(1)).addVirtualCharacter(2);
    }

    /**
     * 測試新增物品是否正常運作
     * @throws Exception
     */
    @Test
    public void testAddItem() throws Exception
    {
        // stubbing appears before the actual execution
        when(receiver.recieve())
                .thenReturn("ADD Item itemName1 0 true 0 0")
                .thenReturn("ADD Item itemName2 1 false 2 2")
                .thenReturn("");

        udpUpdateServerModule.initUDPServer();
        Thread.sleep(1000);
        udpUpdateServerModule.stopUDPServer();

        verify(dynamicObjectModule, times(1)).addItem("itemName1", 0, true, 0, 0);
        verify(dynamicObjectModule, times(1)).addItem("itemName2", 1, false, 2, 2);
    }

    /**
     * 測試更新角色是否正常運作
     * @throws Exception
     */
    @Test
    public void testUpdateCharacter() throws Exception
    {
        // stubbing appears before the actual execution
        when(receiver.recieve())
                .thenReturn("UPDATE Character 1 3 10 3 4")
                .thenReturn("UPDATE Character 2 1 15 20 18")
                .thenReturn("");

        udpUpdateServerModule.initUDPServer();
        Thread.sleep(1000);
        udpUpdateServerModule.stopUDPServer();

        verify(dynamicObjectModule, times(1)).updateVirtualCharacter(1, 3, 10, 3 ,4);
        verify(dynamicObjectModule, times(1)).updateVirtualCharacter(2, 1, 15, 20 ,18);
    }

    /**
     * 測試更新物品是否正常運作
     * @throws Exception
     */
    @Test
    public void testUpdateItem() throws Exception
    {
        // stubbing appears before the actual execution
        when(receiver.recieve())
                .thenReturn("UPDATE Item 0 true 1")
                .thenReturn("UPDATE Item 1 false 2")
                .thenReturn("");

        udpUpdateServerModule.initUDPServer();
        Thread.sleep(1000);
        udpUpdateServerModule.stopUDPServer();

        verify(dynamicObjectModule, times(1)).updateItem(0, true, 1);
        verify(dynamicObjectModule, atLeast(1)).updateItem(1, false, 2);
    }

    /**
     * 測試參數數目不符合的狀況
     * @throws Exception
     */
    @Test
    public void testWrongArgumentCount() throws Exception
    {
        // stubbing appears before the actual execution
        when(receiver.recieve())
                .thenReturn("ADD")
                .thenReturn("ADD Character")
                .thenReturn("ADD Item itemName1 true 0 0")
                .thenReturn("UPDATE")
                .thenReturn("UPDATE Character 1 3 3 4")
                .thenReturn("UPDATE Item 0 1")
                .thenReturn("");

        udpUpdateServerModule.initUDPServer();
        Thread.sleep(1000);
        udpUpdateServerModule.stopUDPServer();

        verify(dynamicObjectModule, never()).addVirtualCharacter(anyInt());
        verify(dynamicObjectModule, never()).addItem(anyString(), anyInt(), anyBoolean(), anyInt(), anyInt());
        verify(dynamicObjectModule, never()).updateVirtualCharacter(anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
        verify(dynamicObjectModule, never()).updateItem(anyInt(), anyBoolean(), anyInt());
    }

    /**
     * 測試參數值或型別不合法的狀態
     * @throws Exception
     */
    @Test
    public void testWrongArgumentType() throws Exception
    {
        // stubbing appears before the actual execution
        when(receiver.recieve())
                .thenReturn("Do Something")
                .thenReturn("ADD Something")
                .thenReturn("ADD Character one")
                .thenReturn("ADD Item itemName1 zero type 0 0")
                .thenReturn("UPDATE  Something")
                .thenReturn("UPDATE Character one three 10 3 4")
                .thenReturn("UPDATE Item zero true 1")
                .thenReturn("");

        udpUpdateServerModule.initUDPServer();
        Thread.sleep(1000);
        udpUpdateServerModule.stopUDPServer();

        verify(dynamicObjectModule, never()).addVirtualCharacter(anyInt());
        verify(dynamicObjectModule, never()).addItem(anyString(), anyInt(), anyBoolean(), anyInt(), anyInt());
        verify(dynamicObjectModule, never()).updateVirtualCharacter(anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
        verify(dynamicObjectModule, never()).updateItem(anyInt(), anyBoolean(), anyInt());
    }
}