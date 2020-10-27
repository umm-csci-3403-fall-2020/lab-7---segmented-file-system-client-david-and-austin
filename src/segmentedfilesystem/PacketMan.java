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

    //Checks if a packet is a header packet
    public boolean isHPacket(DatagramPacket packet) {
        int n = packet.getData()[0];
        if  (n%2 == 0) {
            return true;
        }else{
            return false;
        }
    }
}
