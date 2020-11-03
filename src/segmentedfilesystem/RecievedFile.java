package segmentedfilesystem;

import java.util.HashMap;


public class RecievedFile {
    DataPacket data;
    HeaderPacket header;
    Integer numPackets;


    HashMap<byte[],DataPacket> datapackets = new HashMap<>();

    public RecievedFile(){
        data = dp;
        header = hp;
    }

    public void addDP(DataPacket dpToAdd){
        datapackets.put(dpToAdd.packetNumber, dpToAdd);
        if (lastPacket(dpToAdd)){
            numPackets = dpToAdd.packetNumber + 1;
            lastPacket = addedPacket;
          }
    }

    public boolean allPacketsRecieved(){
        if(numPackets == null){
            return false;
        }
        return numPackets == datapackets.size() && header != null;
    }

    public static boolean lastPacket(Packet packet){
        return packet.status % 4==3;
    }



}
