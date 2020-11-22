package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;
import java.util.ArrayList;

public class DataPacket extends Packet{
    byte[] data;
    int packetNumber;
    int dataLength;
    boolean lastPacket;

    public DataPacket(byte [] packetBuffer,byte statusByte, int packetNum){
        this.packetNumber = packetNum;
        this.data = packetBuffer;
        this.lastPacket = (statusByte % 4 == 3);
        this.dataLength = packetBuffer.length;
    }

    public ArrayList<Byte> getData(){
        ArrayList<Byte> outputBytes = new ArrayList<>();
        for(int i =0; i< dataLength;i++){
            outputBytes.add(data[i]);
        }
        return outputBytes;
    }

    public int getPacketNumber(byte[] bytes) {
       /* int number;
        int primaryByte = bytes[2] & 0xff;
        int secondaryByte = bytes[3] & 0xff;

        if (primaryByte < 0) {
            primaryByte += 256;
        }
        if (secondaryByte < 0) {
            secondaryByte += 256;
        }
        number = (256 * primaryByte) + secondaryByte;*/
        int packetNum = (bytes[2] & 0xFF) * 256 + (bytes[3] & 0xFF);

        return packetNum;
    }
    public void addToFile(RecievedFile rFile){
        rFile.addDP(this);
    }
    public boolean isLastPacket(){
        return lastPacket;
    }
}
