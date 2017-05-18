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
        id = ((data[2] << 8) & 0x0000ff00) | (data[3] & 0x000000ff);
        content = new byte[512];
        int i = 0;
        while (i < 512 && i < dp.getLength()) {
            content[i] = data[i + 4];
            i++;
        }
        length = i;
    }
    
    public DataPacket(int port, InetAddress address, int id, byte[] content, int contentLength) {
        super(port, address);
        this.content = content;
        this.id = id;
        byte[] data = new byte[content.length + 4];
        data[0] = 0;
        data[1] = 3;
        data[2] = ((byte)((id & 0x0000ff00) >> 8));
        data[3] = ((byte)(id & 0x000000ff));
        length = 0;
        while (length < 512 && length < contentLength) {
            data[length + 4] = content[length];
            length++;
        }
        setData(data);
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

    @Override
    public DatagramPacket getDatagram() {
        return new DatagramPacket(data, length + 4, address, destPort);
    }

    @Override
    public String toString() {
        return String.format("DataPacket n°%d (%d bytes)", id, length);
    }
}
