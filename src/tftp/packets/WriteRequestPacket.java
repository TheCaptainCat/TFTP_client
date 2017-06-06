package tftp.packets;

import java.net.InetAddress;

public class WriteRequestPacket extends RequestPacket {
    public WriteRequestPacket(int destPort, InetAddress address, String filename) {
        super(destPort, address, (byte) 2, filename);
    }

    @Override
    public String toString() {
        return String.format("WriteRequestPacket: %s", filename);
    }
}
