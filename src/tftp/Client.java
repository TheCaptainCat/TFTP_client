package tftp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tftp.transfer.FileReceiver;
import tftp.transfer.FileSender;

public class Client implements Runnable{

    public Client() {
        
    }

    @Override
    public void run() {
        try {
            /*FileReceiver fr = new FileReceiver("test.txt", InetAddress.getByName("192.168.1.14"), 69);
            new Thread(fr).start();*/
            FileSender fs = new FileSender("test2.txt", InetAddress.getByName("192.168.1.14"), 69);
            new Thread(fs).start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
