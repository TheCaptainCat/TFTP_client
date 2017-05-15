package tftp.transfer;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import tftp.packets.AckPacket;
import tftp.packets.DataPacket;
import tftp.packets.WriteRequestPacket;
import tftp.receive.Receiver;
import tftp.send.Sender;

public class FileSender extends FileTransfer implements Observer, Runnable {
    int sentId;
    int receivedId;
    int comPort;

    public FileSender(String filename, InetAddress address, int connectionPort) {
        super(filename, address, connectionPort);
        this.sentId = 0;
        this.receivedId = 0;
        this.comPort = 0;
    }

    private void processFile() throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        int i = 1;
        while (true) {
            byte[] data = new byte[512];
            int length = fis.read(data, 0, 512);
            DataPacket packet = new DataPacket(connectionPort, address, i++, data);
            packets.add(packet);
            if (length != 512)
                break;
        }
    }

    @Override
    public synchronized void run() {
        try {
            processFile();
            DatagramSocket socket = new DatagramSocket();
            WriteRequestPacket wrp = new WriteRequestPacket(connectionPort, address, filename);
            Sender sender = new Sender(socket, wrp);
            {
                Receiver receiver = new Receiver(socket);
                receiver.addObserver(this);
                new Thread(receiver).start();
                while (!receiver.isReady()) {}
            }
            sender.send();
            while (true) {
                wait();
                if (sentId != receivedId)
                    break;
                if (sentId == packets.size())
                    break;
                DataPacket dataPacket = packets.get(sentId);
                dataPacket.setDestPort(comPort);
                Sender dataSender = new Sender(socket, dataPacket);
                {
                    Receiver receiver = new Receiver(socket);
                    receiver.addObserver(this);
                    new Thread(receiver).start();
                    while (!receiver.isReady()) {}
                }
                dataSender.send();
                sentId++;
            }
            socket.close();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        if (o instanceof Receiver) {
            if (arg instanceof AckPacket) {
                AckPacket ack = (AckPacket) arg;
                receivedId = ack.getId();
                if (comPort == 0)
                    comPort = ack.getDestPort();
                notify();
            }
        }
    }
}
