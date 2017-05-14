package tftp.transfer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import tftp.packets.AckPacket;
import tftp.packets.DataPacket;
import tftp.packets.ReadRequestPacket;
import tftp.receive.Receiver;
import tftp.send.Sender;

public class FileReceiver extends FileTransfer implements Observer, Runnable {
    public FileReceiver(String filename, InetAddress address, int connectionPort) {
        super(filename, address, connectionPort);
    }

    private void saveFile() {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            for (DataPacket packet : packets) {
                fos.write(packet.getContent(), 0, packet.getLength());
            }
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(FileReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void run() {
        try {
            ReadRequestPacket rrp = new ReadRequestPacket(connectionPort, address, filename);
            DatagramSocket socket = new DatagramSocket();
            rrp.setSrcPort(socket.getLocalPort());
            Sender sender = new Sender(socket, rrp);
            sender.send();
            socket.close();
            while (true) {
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
                    break;
            }
            saveFile();
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
