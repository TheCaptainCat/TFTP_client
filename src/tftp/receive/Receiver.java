package tftp.receive;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import tftp.packets.Packet;

public class Receiver extends Observable implements Runnable {
    private final DatagramSocket socket;
    private volatile boolean ready;

    public boolean isReady() {
        return ready;
    }

    public void setReady() {
        this.ready = true;
    }

    public Receiver(DatagramSocket socket) {
        this.socket = socket;
        this.ready = false;
    }

    @Override
    public synchronized void run() {
        try {
            byte[] buf = new byte[2048];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            System.out.println(String.format("Recieving on %d", socket.getLocalPort()));
            setReady();
            socket.receive(dp);
            System.out.println(String.format("Received from %d", dp.getPort()));
            Packet p = Packet.buildPacket(dp);
            setChanged();
            notifyObservers(p);
        } catch (IOException ex) {
            System.out.println("Socket closed, transfer done.");
        }
    }
}
