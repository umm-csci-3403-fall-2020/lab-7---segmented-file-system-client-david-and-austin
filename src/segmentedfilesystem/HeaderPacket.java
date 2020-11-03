package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class HeaderPacket extends Packet{
    byte[] filename;

    public HeaderPacket(DatagramPacket rawBytes) {
        status = rawBytes.getData()[0];
        id= rawBytes.getData()[1];
        filename = Arrays.copyOfRange(rawBytes.getData(),2,rawBytes.getLength());
    }

    // TODO: 10/27/2020 GetFileName should be able to read ond parse a file name from the Packet object
    public String getFileName(){
        return "";
    }
}
