package tftp.packets;

import java.net.DatagramPacket;
import java.net.InetAddress;

public abstract class Packet {
    protected int destPort;
    protected int srcPort;
    protected InetAddress address;
    protected byte[] data;

    public Packet(int destPort, InetAddress address) {
        this.srcPort = 0;
        this.destPort = destPort;
        this.address = address;
        this.data = null;
    }

    public final void setData(byte[] data) {
        this.data = data;
    }

    public final byte[] getData() {
        return data;
    }

    public final void setSrcPort(int srcPort) {
        this.srcPort = srcPort;
    }

    public final int getDestPort() {
        return destPort;
    }

    public final int getSrcPort() {
        return srcPort;
    }

    public final InetAddress getAddress() {
        return address;
    }
    
    public final DatagramPacket getDatagram() {
        return new DatagramPacket(data, data.length, address, destPort);
    }
    public static Packet buildPacket(DatagramPacket dp) {
        int opcode = (dp.getData()[0] << 8) | dp.getData()[1];
        if (opcode == 3)
            return new DataPacket(dp);
        return null;
    }
}
