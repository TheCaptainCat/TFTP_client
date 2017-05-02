package tftp.send;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Sender implements Runnable {

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
                    System.out.println("Envoi !");
                    send(queue.poll());
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
    }
}
