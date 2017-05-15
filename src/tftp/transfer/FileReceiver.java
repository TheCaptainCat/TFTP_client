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
            DatagramSocket socket = new DatagramSocket();
            ReadRequestPacket rrp = new ReadRequestPacket(connectionPort, address, filename);
            Sender sender = new Sender(socket, rrp);
            {
                Receiver receiver = new Receiver(socket);
                receiver.addObserver(this);
                new Thread(receiver).start();
                while (!receiver.isReady()) {}
            }
            sender.send();
            while (true) {
                wait();
                DataPacket lastPacket = packets.get(packets.size() - 1);
                AckPacket ack = new AckPacket(lastPacket.getDestPort(), address, lastPacket.getId());
                Sender ackSender = new Sender(socket, ack);
                {
                    Receiver receiver = new Receiver(socket);
                    receiver.addObserver(this);
                    new Thread(receiver).start();
                    while (!receiver.isReady()) {}
                }
                ackSender.send();
                if (lastPacket.getLength() != 512)
                    break;
            }
            socket.close();
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
