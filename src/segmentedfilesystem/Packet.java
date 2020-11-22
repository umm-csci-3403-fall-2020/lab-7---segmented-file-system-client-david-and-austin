package segmentedfilesystem;

import java.util.Arrays;

abstract class Packet {
    private byte status;
    private int id;
  
    public int getFileID(){
    return id;
    }
    public void setFileID(int newID){
        id = newID;
    }
    public byte getStatus(){
        return status;
    }
    public void setStatus(byte newStatus){
        status = newStatus;
    }

    public abstract void addToFile(RecievedFile rFile);
   
  
}
