package segmentedfilesystem;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;

public class RecievedFile {
   private  HeaderPacket header;
   private Integer numPackets;
   private String fileName;

    HashMap<Integer,DataPacket> datapackets = new HashMap<>();

    public RecievedFile(){
    }
    public void writeToFile(){
        System.out.println("Writing file " + fileName);
        ArrayList<Integer> keys = makeKeys(numPackets);
        ArrayList<Byte> outputBuffer = makeOutputBuffer(numPackets, keys);
        byte[] bytes = createBytes(outputBuffer);
        outputFile(bytes);
      }
    private void outputFile(byte[] bytes) {
      try {
        System.out.println("VALID NAME: " + fileName);
        FileOutputStream file = new FileOutputStream(fileName);
        file.write(bytes);
        file.close();
      } catch (IOException e){
        System.err.println("Error attempting to write file " + fileName);
        System.err.println(e);
      }
    }
    private byte[] createBytes(ArrayList<Byte> outputBuffer){
      byte[] bytes = new byte[outputBuffer.size()];
      for (int i = 0; i <outputBuffer.size() ; i++) {
        bytes[i]=(byte) outputBuffer.get(i);
      }
      return bytes;
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
        datapackets.put(dpToAdd.getPacketNumber(), dpToAdd);
        if (dpToAdd.isLastPacket()){
            numPackets = dpToAdd.getPacketNumber() + 1;
          }
        if(allPacketsForFile()){
            writeToFile();
        }
    }
    public void addHP(HeaderPacket hp){
        header = hp;
        fileName = hp.getFileName();
        if(allPacketsForFile()){
            writeToFile();
        }
    }

    public boolean allPacketsForFile(){
        if(numPackets == null){
            return false;
        }
        return numPackets.equals(datapackets.size()) && header != null;
    }
}
