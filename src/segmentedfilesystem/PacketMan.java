package segmentedfilesystem;

import java.util.Arrays;

public class PacketMan {
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
            int packetNum = makePacketNumber(rawBytes);

            packet = new DataPacket(buffer,statusByte,packetNum);
        //    destinationFile.addDP(newDPacket);
        }
        packet.setFileID(fileID); 

        return packet;
    }

    public int makePacketNumber(byte[] bytes) {
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
