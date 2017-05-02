package tftp.send;

import java.net.DatagramPacket;
import java.net.InetAddress;

public abstract class Packet {
    private int port;
    private InetAddress address;
    private byte[] data;

    public Packet(int port, InetAddress address) {
        this.port = port;
        this.address = address;
        this.data = null;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }
    
    public final DatagramPacket getDatagram() {
        return new DatagramPacket(data, data.length, address, port);
    }
}
