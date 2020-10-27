package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class HeaderPacket extends Packet{

    byte status;
    byte ID;
    byte[] data;

    public void HPacketStruct(DatagramPacket rawBytes) {
        status = rawBytes.getData()[0];
        ID = rawBytes.getData()[1];
        data = Arrays.copyOfRange(rawBytes.getData(),2,rawBytes.getLength());
    }

    // TODO: 10/27/2020 GetFileName should be able to read ond parse a file name from the Packet object
    public String getFileName(){
        return "";
    }
}
