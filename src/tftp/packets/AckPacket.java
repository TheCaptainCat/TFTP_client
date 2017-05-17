package tftp.packets;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class AckPacket extends Packet {
    int id;

    public int getId() {
        return id;
    }

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
    
    public AckPacket(DatagramPacket dp) {
        super(dp.getPort(), dp.getAddress());
        byte[] data = dp.getData();
        setData(data);
        this.id = ((data[2] << 8) & 0x0000ff00) | (data[3] & 0x000000ff);
    }

    @Override
    public String toString() {
        return String.format("AckPacket n°%d", id);
    }
}
