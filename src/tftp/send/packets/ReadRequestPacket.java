package tftp.send.packets;

import java.net.InetAddress;

public class ReadRequestPacket extends RequestPacket {
    
    public ReadRequestPacket(int destPort, InetAddress address, String filename) {
        super(destPort, address, (byte) 1, filename);
    }
}
