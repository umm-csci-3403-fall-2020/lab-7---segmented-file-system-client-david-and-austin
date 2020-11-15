package segmentedfilesystem;

import java.util.HashMap;
import java.io.File;

public class RecievedFile {
    DataPacket data;
    HeaderPacket header;
    Integer numPackets;
    String fileName;

    HashMap<Integer,DataPacket> datapackets = new HashMap<>();

    public RecievedFile(){
    }
    public void writeToFile(){

    }

    public void addDP(DataPacket dpToAdd){
        datapackets.put(dpToAdd.packetNumber, dpToAdd);
        if (lastPacket(dpToAdd)){
            numPackets = dpToAdd.packetNumber;
          }
        if(allPacketsRecieved()){
            writeToFile();
        }
    }
    public void addHP(HeaderPacket hp){
        header = hp;
        fileName = hp.getFileName();
        if(allPacketsRecieved()){
            writeToFile();
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
