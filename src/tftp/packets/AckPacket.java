package tftp.packets;

import java.net.InetAddress;

public class AckPacket extends Packet {
    int id;

    public AckPacket(int destPort, InetAddress address, int id) {
        super(destPort, address);
        this.id = id;
        byte[] data = new byte[4];
        data[0] = 0;
        data[1] = 4;
        data[2] = (byte) (id << 8);
        data[3] = (byte) id;
        setData(data);
    }

    @Override
    public String toString() {
        return String.format("AckPacket n°%d", id);
    }
}
