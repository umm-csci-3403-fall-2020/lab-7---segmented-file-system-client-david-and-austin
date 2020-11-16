package segmentedfilesystem;

import java.util.HashMap;
import java.io.File;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;

public class RecievedFile {
    DataPacket data;
    HeaderPacket header;
    Integer numPackets;
    String fileName;

    HashMap<Integer,DataPacket> datapackets = new HashMap<>();

    public RecievedFile(){
    }
    public void writeToFile(){
        System.out.println("Writing file " + fileName);
        ArrayList<Byte> outputBuffer = new ArrayList<>();
        ArrayList<Integer> keys = new ArrayList<>();
        for(int i = 0; i < numPackets; i++){
          keys.add(i);
        }
        for(int i = 0; i < numPackets; i++){
          outputBuffer.addAll(datapackets.get(keys.get(i)).getData());
        }
        byte[] byteArr = new byte[outputBuffer.size()];
        for (int i = 0; i <outputBuffer.size() ; i++) {
          byteArr[i]=(byte) outputBuffer.get(i);
        }
        try {
          FileOutputStream file = new FileOutputStream(fileName);
          file.write(byteArr);
        } catch (IOException e){
          System.err.println("Error attempting to write file " + fileName);
          System.err.println("COMPUTER GOES BRRRRRRRRRRR WITH THIS ERROR");
          System.err.println(e);
        }
    
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
