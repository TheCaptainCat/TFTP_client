package tftp.send;

import tftp.packets.Packet;
import java.io.IOException;
import java.net.DatagramSocket;

public class Sender {
    private final DatagramSocket socket;
    private final Packet packet;

    public Sender(DatagramSocket socket, Packet packet) {
        this.socket = socket;
        this.packet = packet;
    }

    public void send() throws IOException {
        System.out.println(String.format("Sending on %d to %d: %s", socket.getLocalPort(), packet.getDestPort(), packet));
        socket.send(packet.getDatagram());
    }
}
