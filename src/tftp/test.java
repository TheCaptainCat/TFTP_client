package tftp;

import java.net.InetAddress;
import tftp.send.packets.ReadRequestPacket;

public class test {
    public static void main(String[] args) throws Exception {
        ReadRequestPacket rrp = new ReadRequestPacket(69, InetAddress.getByName("127.0.0.1"), "test.txt");
        byte[] data = rrp.getData();
        System.out.println(new String(data));
    }
}
