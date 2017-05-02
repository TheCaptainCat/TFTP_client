package tftp.receive;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Observable;
import tftp.send.packets.DataPacket;

public class Receiver extends Observable implements Runnable {
    private int port;

    public Receiver(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(port);
            byte[] buf = new byte[2048];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            ds.receive(dp);
            new DataPacket(dp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            ds.close();
        }
    }
}
