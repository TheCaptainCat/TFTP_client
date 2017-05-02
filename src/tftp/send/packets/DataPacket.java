package tftp.send.packets;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class DataPacket extends Packet {
    private int id;
    private byte[] content;
    private int length;
    
    public DataPacket(DatagramPacket dp) {
        super(dp.getPort(), dp.getAddress());
        byte[] data = dp.getData();
        int opcode = (data[0] << 8) | data[1];
        id = (data[2] << 8) | data[3];
        content = new byte[512];
        int i = 4;
        while (data[i] != 0) {
            content[i - 4] = data[i];
            i++;
        }
        length = i - 4;
    }

    public int getId() {
        return id;
    }

    public byte[] getContent() {
        return content;
    }

    public int getLength() {
        return length;
    }
}
