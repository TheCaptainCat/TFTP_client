package tftp.transfer;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import tftp.packets.DataPacket;

public class FileTransfer extends Observable {
    protected final String filename;
    protected final InetAddress address;
    protected final int connectionPort;
    protected final List<DataPacket> packets;


    public FileTransfer(String filename, InetAddress address, int connectionPort) {
        this.filename = filename;
        this.address = address;
        this.connectionPort = connectionPort;
        this.packets = new ArrayList<>();
    }
    
    public void notifyNewMessage(String message) {
        setChanged();
        notifyObservers(message);
    }
}
