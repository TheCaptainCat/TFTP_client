package tftp.send;

import java.net.DatagramPacket;
import java.net.InetAddress;

public abstract class Packet {
    private int destPort;
    private int srcPort;
    private InetAddress address;
    private byte[] data;

    public Packet(int destPort, InetAddress address) {
        this.srcPort = 0;
        this.destPort = destPort;
        this.address = address;
        this.data = null;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setSrcPort(int srcPort) {
        this.srcPort = srcPort;
    }

    public int getDestPort() {
        return destPort;
    }

    public int getSrcPort() {
        return srcPort;
    }

    public InetAddress getAddress() {
        return address;
    }
    
    public final DatagramPacket getDatagram() {
        return new DatagramPacket(data, data.length, address, destPort);
    }
}
