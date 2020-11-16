package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;
import java.util.ArrayList;

public class DataPacket extends Packet{
    byte[] data;
    int packetNumber;
    byte status;
    int dataLength;

    public DataPacket(byte statusByte, byte fileID, byte [] packetBuffer){
        this.id = fileID;
        status= statusByte;
        packetNumber = getPacketNumber(packetBuffer);
        data = packetBuffer;
        dataLength = packetBuffer.length;
    }

    //TODO: Not 100% how this should work, but I'm imagining it just returns the byte that the packet should be holding
    public ArrayList<Byte> getData(){
        ArrayList<Byte> outputBytes = new ArrayList<>();
        for(int i =0; i< dataLength;i++){
            outputBytes.add(data[i]);
        }
        return outputBytes;
    }

    public int getPacketNumber(byte[] bytes) {
        int number;
        int primaryByte = bytes[2] & 0xff;
        int secondaryByte = bytes[3] & 0xff;

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
