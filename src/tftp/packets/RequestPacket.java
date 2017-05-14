package tftp.packets;

import java.net.InetAddress;

public abstract class RequestPacket extends Packet {
    protected String filename;

    public RequestPacket(int destPort, InetAddress address, byte opcode, String filename) {
        super(destPort, address);
        this.filename = filename;
        byte[] data = new byte[9 + filename.length()];
        data[0] = 0;
        data[1] = opcode;
        for (int i = 0; i < filename.length(); i++) {
            data[2 + i] = (byte) filename.charAt(i);
        }
        data[filename.length() + 2] = 0;
        for (int i = 0; i < "octet".length(); i++) {
            data[3 + filename.length() + i] = (byte) "octet".charAt(i);
        }
        data[data.length - 1] = 0;
        setData(data);
    }
}
