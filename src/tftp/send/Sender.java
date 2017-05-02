package tftp.send;

import tftp.send.packets.Packet;
import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import tftp.receive.Receiver;

public class Sender extends Observable implements Runnable {

    private Queue<Packet> queue;

    public Sender() {
        queue = new ConcurrentLinkedQueue<>();
    }

    public synchronized void sendPacket(Packet p) {
        queue.add(p);
        notify();
    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                while (!queue.isEmpty()) {
                    Packet dp = queue.poll();
                    send(dp);
                    setChanged();
                    notifyObservers();
                }
                wait();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void send(Packet p) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        p.setSrcPort(ds.getLocalPort());
        ds.send(p.getDatagram());
        ds.close();
    }
}
