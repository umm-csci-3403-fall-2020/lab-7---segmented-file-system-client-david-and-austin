package segmentedfilesystem;

import java.net.DatagramPacket;

public class PacketMan {
    //TODO: make PacketMan able to interpret header/data packets, parse into RecievedFile
    public PacketMan(DatagramPacket[] packets){

    }

    //TODO: create method that checks if all packets have been received
    public boolean allPacketsReceived() {
        return false;
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
}
