package tftp;

import java.net.InetAddress;
import tftp.send.Sender;
import tftp.send.packets.ReadRequestPacket;

public class test {
    public static void main(String[] args) throws Exception {
        ReadRequestPacket rrp = new ReadRequestPacket(69, InetAddress.getByName("127.0.0.1"), "test.txt");
        Sender sender = new Sender();
        new Thread(sender).start();
        sender.sendPacket(rrp);
    }
}
