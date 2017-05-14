package tftp.transfer;

import java.net.InetAddress;

public class FileTransfer {
    protected final String filename;
    protected final InetAddress address;
    protected final int connectionPort;

    public FileTransfer(String filename, InetAddress address, int connectionPort) {
        this.filename = filename;
        this.address = address;
        this.connectionPort = connectionPort;
    }
}
