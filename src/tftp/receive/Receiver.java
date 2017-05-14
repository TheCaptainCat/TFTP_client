package tftp.receive;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import tftp.packets.Packet;

public class Receiver extends Observable implements Runnable {
    private int port;

    public Receiver(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(port);
            byte[] buf = new byte[2048];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            System.out.println(String.format("Recieving on %d", port));
            socket.receive(dp);
            socket.close();
            System.out.println(String.format("Received from %d", dp.getPort()));
            Packet p = Packet.buildPacket(dp);
            setChanged();
            notifyObservers(p);
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
