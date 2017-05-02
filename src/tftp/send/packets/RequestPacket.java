package tftp.send.packets;

import java.net.InetAddress;
import tftp.send.Packet;

public abstract class RequestPacket extends Packet {
    
    public RequestPacket(int port, InetAddress address, byte opcode, String filename) {
        super(port, address);
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
