/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tftp.packets;

import java.net.DatagramPacket;

/**
 *
 * @author p1609594
 */
public class ErrorPacket extends Packet {

    private int errorCode;
    private String message;

    public ErrorPacket(DatagramPacket dp) {
        super(dp.getPort(), dp.getAddress());
        byte[] data = dp.getData();
        setData(data);
        this.errorCode = ((data[2] << 8) & 0x0000ff00) | (data[3] & 0x000000ff);
        this.message = "";
        int i = 4;
        while (data[i] != 0) {            
            this.message += (char)data[i];
            i++;
        }
    }

    public String getMessage() {
        return message;
    }
    
}
