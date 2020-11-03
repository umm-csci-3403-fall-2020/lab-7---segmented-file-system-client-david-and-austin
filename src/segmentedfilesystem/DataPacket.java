package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class DataPacket extends Packet{
    byte data;
    byte[] packetNumber;

    public DataPacket(DatagramPacket rawBytes){
        status = rawBytes.getData()[0];
        id = rawBytes.getData()[1];
        packetNumber[0] = rawBytes.getData()[2];
        packetNumber[1] = rawBytes.getData()[3];
        data = Arrays.copyOfRange(rawBytes.getData(),4,rawBytes.getLength());
    }

    //TODO: Not 100% how this should work, but I'm imagining it just returns the byte that the packet should be holding
    public int getData{
        return -1;
    }

}
