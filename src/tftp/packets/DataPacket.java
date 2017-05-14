package tftp.packets;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class DataPacket extends Packet {
    private int id;
    private byte[] content;
    private int length;
    
    public DataPacket(DatagramPacket dp) {
        super(dp.getPort(), dp.getAddress());
        byte[] data = dp.getData();
        setData(data);
        id = (data[2] << 8) | data[3];
        content = new byte[512];
        int i = 0;
        while (i < 512 && data[i + 4] != 0) {
            content[i] = data[i + 4];
            i++;
        }
        length = i;
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
