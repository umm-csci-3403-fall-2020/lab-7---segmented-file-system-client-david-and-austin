package segmentedfilesystem;

import java.net.DatagramPacket;

public class DataPacket {
    int status;
    int data;
    int id;
    int packetNumber;

    public DataPacket(DatagramPacket rawBytes){
        byte[] temp = new byte[rawBytes.getLength()];
        temp = rawBytes.getData();
        temp[0] = Byte.
    }

    //TODO: checks to see if packet is last one being sent
    public boolean isLastPacket(){
        return false;
    }

    //TODO: Not 100% how this should work, but I'm imagining it just returns the byte that the packet should be holding
    public int getData{
        return -1;
    }

}
