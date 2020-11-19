package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class HeaderPacket extends Packet{
    String filename;

    public HeaderPacket(byte[] rawBytes) {
        status = rawBytes[0];
        id= rawBytes[1];
        String filename = new String(rawBytes);
        this.filename = filename;
    }

    // TODO: 10/27/2020 GetFileName should be able to read ond parse a file name from the Packet object
    public String getFileName(){
        return filename;
    }
}
