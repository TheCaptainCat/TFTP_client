package tftp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import tftp.receive.Receiver;
import tftp.send.Sender;
import tftp.send.packets.ReadRequestPacket;

public class Client implements Observer, Runnable{
    private Sender sender;

    public Client() {
        sender = new Sender();
        sender.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Sender)
            System.out.println("Hello");
        if (o instanceof Receiver)
            System.out.println("Hola");
    }

    @Override
    public void run() {
        try {
            new Thread(sender).start();
            ReadRequestPacket rrp = new ReadRequestPacket(69, InetAddress.getByName("127.0.0.1"), "test.txt");
            sender.sendPacket(rrp);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }
}
