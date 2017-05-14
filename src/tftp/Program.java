package tftp;

import java.net.InetAddress;
import tftp.send.Sender;
import tftp.packets.ReadRequestPacket;

public class Program {
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        new Thread(client).start();
    }
}
