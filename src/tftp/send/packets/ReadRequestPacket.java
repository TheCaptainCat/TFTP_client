package tftp.send.packets;

import java.net.InetAddress;

public class ReadRequestPacket extends RequestPacket {
    
    public ReadRequestPacket(int port, InetAddress address, String filename) {
        super(port, address, (byte) 1, filename);
    }
}
