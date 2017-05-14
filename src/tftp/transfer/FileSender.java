package tftp.transfer;

import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;

public class FileSender extends FileTransfer implements Observer, Runnable {
    public FileSender(String filename, InetAddress address, int connectionPort) {
        super(filename, address, connectionPort);
    }

    @Override
    public void update(Observable o, Object o1) {
        
    }

    @Override
    public void run() {
        
    }
}
