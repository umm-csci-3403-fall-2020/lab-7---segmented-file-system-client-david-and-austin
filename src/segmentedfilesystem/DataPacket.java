package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class DataPacket extends Packet{
    byte[] data;
    int packetNumber;

    public DataPacket(DatagramPacket recievedPacket){
        byte[] rawBytes = recievedPacket.getData();
        status = rawBytes[0];
        id = rawBytes[1];
        packetNumber = getPacketNumber(rawBytes);
        data = Arrays.copyOfRange(recievedPacket.getData(),4,recievedPacket.getLength());
    }

    //TODO: Not 100% how this should work, but I'm imagining it just returns the byte that the packet should be holding
    public int getData(){
        return -1;
    }

    public int getPacketNumber(byte[] bytes) {
        int number;
        int primaryByte = bytes[2];
        int secondaryByte = bytes[3];

        if (primaryByte < 0) {
            primaryByte += 256;
        }
        if (secondaryByte < 0) {
            secondaryByte += 256;
        }
        number = (256 * primaryByte) + secondaryByte;

        return number;
    }
}
