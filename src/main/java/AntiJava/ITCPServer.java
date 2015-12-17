package AntiJava;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

/**
 * TCP Server module (once started) contains several threads
 * (one thread for each client computer and a main listening thread).
 * Each thread loops forever to receive message from TCP client module.
 * On receiving a message, it decodes the message and interact with Centralized Data Center accordingly.
 *
 * Created by fntsr on 2015/12/13.
 */
public interface ITCPServer
{
    Vector<InetAddress> getClientIPTable() throws UnknownHostException;
}
