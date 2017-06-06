package tftp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import tftp.transfer.FileReceiver;
import tftp.transfer.FileSender;

public class Client extends Observable implements Runnable, Observer {

    // true is the client have to send
    // false otherwise
    private final boolean sending;
    private final String address;
    private final String fileName;

    public Client(boolean sending, String address, String fileName) {
        this.sending = sending;
        this.address = address;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            if (sending) {
                FileSender fs = new FileSender(fileName, InetAddress.getByName(address), 69);
                fs.addObserver(this);
                new Thread(fs).start();
            } else {
                setChanged();
                notifyObservers("Receiving file...");
                FileReceiver fr = new FileReceiver(fileName, InetAddress.getByName(address), 69);
                fr.addObserver(this);
                new Thread(fr).start();
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}
