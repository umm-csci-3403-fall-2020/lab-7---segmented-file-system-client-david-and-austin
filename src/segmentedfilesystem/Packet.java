package segmentedfilesystem;

import java.nio.Buffer;
import java.util.Arrays;

abstract class Packet {
    byte status;
    int id;
    public static Packet sortPacket(byte [] packetBuffer){
        byte statusByte = packetBuffer[0];
        byte fileID = packetBuffer[1];
        boolean isDataPacket = (statusByte%2 ==1);
        Packet packet;
        
        if(isDataPacket){
            byte[] buffer = Arrays.copyOfRange(packetBuffer, 4, packetBuffer.length);
            packet = new DataPacket(statusByte, fileID,packetBuffer);
        }else{
            packet = new HeaderPacket(packetBuffer);
        }
        return packet;
    }
    public void getFileID(){

    }
  
}
