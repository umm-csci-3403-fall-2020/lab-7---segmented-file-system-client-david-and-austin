package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class PacketMan {
    public PacketMan(){

    }

    //Checks packet type. if header, return 0, if data, return 1.
    public int packetType(DatagramPacket packet) {
        int n = packet.getData()[0];
        if  (n%2 == 0) {
            return 0;
        }else{
            return 1;
        }
    }

    public Packet buildPacket(byte[] rawBytes){
        Packet packet;
        byte fileID = rawBytes[1];
        byte statusByte = rawBytes[0];
        if(statusByte % 2 == 0){
            //it's a headerpacket
            //System.out.println("It's a header packet!");
            byte [] filteredBuffer = Arrays.copyOfRange(rawBytes, 2, rawBytes.length);
            packet = new HeaderPacket(filteredBuffer);
           // destinationFile.addHP(newHPacket);
        }else{
            //it's a data packet
            //System.out.println("It's a data packet!");
            byte[] buffer = Arrays.copyOfRange(rawBytes, 4, rawBytes.length);
            int packetNum = (rawBytes[2] & 0xFF) * 256 + (rawBytes[3] & 0xFF);

            packet = new DataPacket(buffer,statusByte,packetNum);
        //    destinationFile.addDP(newDPacket);
        }
        packet.setFileID(fileID); 
        packet.setStatus(statusByte);

        return packet;
    }
   

 
}
