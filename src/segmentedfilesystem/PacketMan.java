package segmentedfilesystem;

import java.net.DatagramPacket;

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

    public void buildPacket(byte[] rawBytes, RecievedFile destinationFile){
        if(rawBytes[0] % 2 == 0){
            //it's a headerpacket
            //System.out.println("It's a header packet!");
            HeaderPacket newHPacket = new HeaderPacket(rawBytes);
            destinationFile.addHP(newHPacket);
        }else{
            //it's a data packet
            //System.out.println("It's a data packet!");
            DataPacket newDPacket = new DataPacket(rawBytes);
            destinationFile.addDP(newDPacket);
        }
    }
}
