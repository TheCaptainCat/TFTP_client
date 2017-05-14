package tftp.transfert;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import tftp.packets.AckPacket;
import tftp.packets.DataPacket;
import tftp.packets.ReadRequestPacket;
import tftp.receive.Receiver;
import tftp.send.Sender;

public class FileReceiver implements Observer, Runnable {
    private final String filename;
    private final InetAddress address;
    private final int connectionPort;
    private List<DataPacket> packets;

    public FileReceiver(String filename, InetAddress address, int connectionPort) {
        this.filename = filename;
        this.address = address;
        this.connectionPort = connectionPort;
        this.packets = new ArrayList<>();
    }

    @Override
    public synchronized void run() {
        try {
            boolean done = false;
            ReadRequestPacket rrp = new ReadRequestPacket(connectionPort, address, filename);
            DatagramSocket socket = new DatagramSocket();
            rrp.setSrcPort(socket.getLocalPort());
            Sender sender = new Sender(socket, rrp);
            sender.send();
            socket.close();
            while (!done) {
                Receiver receiver = new Receiver(rrp.getSrcPort());
                receiver.addObserver(this);
                new Thread(receiver).start();
                wait();
                DataPacket lastPacket = packets.get(packets.size() - 1);
                AckPacket ack = new AckPacket(lastPacket.getDestPort(), address, lastPacket.getId());
                ack.setSrcPort(rrp.getSrcPort());
                DatagramSocket ackSocket = new DatagramSocket(rrp.getSrcPort());
                Sender ackSender = new Sender(ackSocket, ack);
                ackSender.send();
                ackSocket.close();
                if (lastPacket.getLength() != 512)
                    done = true;
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(FileReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        if (o instanceof Receiver) {
            if (arg instanceof DataPacket) {
                DataPacket packet = (DataPacket) arg;
                packets.add(packet);
                notify();
            }
        }
    }
}
