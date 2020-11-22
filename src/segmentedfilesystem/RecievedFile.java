package segmentedfilesystem;

import java.util.HashMap;
//import java.io.File;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;

public class RecievedFile {
    Byte fileID = 0;
    DataPacket data;
    HeaderPacket header;
    Integer numPackets;
    String fileName;
    String percentCompleted;

    HashMap<Integer,DataPacket> datapackets = new HashMap<>();

    public RecievedFile(){
    }
    public void writeToFile(){
        System.out.println("Writing file " + fileName);
        ArrayList<Integer> keys = makeKeys(numPackets);
        ArrayList<Byte> outputBuffer = makeOutputBuffer(numPackets, keys);
        byte[] bytes = new byte[outputBuffer.size()];
        for (int i = 0; i <outputBuffer.size() ; i++) {
          bytes[i]=(byte) outputBuffer.get(i);
          setPercentCompleted(i);
        }
        try {
          System.out.println("VALID NAME: " + fileName);
          FileOutputStream file = new FileOutputStream(fileName);
          file.write(bytes);
          file.close();
        } catch (IOException e){
          System.err.println("Error attempting to write file " + fileName);
          System.err.println("COMPUTER GOES BRRRRRRRRRRR WITH THIS ERROR");
          System.err.println(e);
        }
      }
      private static ArrayList<Integer> makeKeys(int numPackets){
        ArrayList<Integer> keys = new ArrayList<>();
        for(int i = 0; i < numPackets; i++){
            keys.add(i);
          }
        return keys;    
      }
      private ArrayList<Byte> makeOutputBuffer(int numPackets,ArrayList<Integer> keys){
        ArrayList<Byte> outputBuffer = new ArrayList<>();

        for(int i = 0; i < numPackets; i++){
            outputBuffer.addAll(datapackets.get(keys.get(i)).getData());
          }
          return outputBuffer;
      }

    public void addDP(DataPacket dpToAdd){
        datapackets.put(dpToAdd.packetNumber, dpToAdd);
        if (dpToAdd.isLastPacket()){
            numPackets = dpToAdd.packetNumber + 1;
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
        return numPackets.equals(datapackets.size()) && header != null;
    }

    public void setPercentCompleted(int i){
      double interation = i*1.0;
      percentCompleted = "PERCENT COMPLETED: "+ interation/numPackets + "%";
    }
    public String getPercentCompleted(){
      return percentCompleted;
    }
}
