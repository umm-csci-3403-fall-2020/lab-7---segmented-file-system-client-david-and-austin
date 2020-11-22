package segmentedfilesystem;

import java.util.ArrayList;

public class DataPacket extends Packet{
    private byte[] data;
    private int packetNumber;
    private int dataLength;
    private  boolean lastPacket;

    public DataPacket(byte [] packetBuffer,byte statusByte, int packetNum){
        this.packetNumber = packetNum;
        this.data = packetBuffer;
        this.lastPacket = (statusByte % 4 == 3);
        this.dataLength = packetBuffer.length;
    }

    public ArrayList<Byte> getData(){
        ArrayList<Byte> outputBytes = new ArrayList<>();
        for(int i =0; i< dataLength;i++){
            outputBytes.add(data[i]);
        }
        return outputBytes;
    }

   public int getPacketNumber(){
       return packetNumber;
   }
    public void addToFile(RecievedFile rFile){
        rFile.addDP(this);
    }
    public boolean isLastPacket(){
        return lastPacket;
    }
}
