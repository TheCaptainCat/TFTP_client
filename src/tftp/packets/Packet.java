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

    public final void setDestPort(int destPort) {
        this.destPort = destPort;
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

    public DatagramPacket getDatagram() {
        return new DatagramPacket(data, data.length, address, destPort);
    }

    public static Packet buildPacket(DatagramPacket dp) {
        int opcode = (dp.getData()[0] << 8) | dp.getData()[1];
        switch (opcode) {
            case 3:
                return new DataPacket(dp);
            case 4:
                return new AckPacket(dp);
            case 5:
                return new ErrorPacket(dp);
            default:
                break;
        }
        return null;
    }
}
