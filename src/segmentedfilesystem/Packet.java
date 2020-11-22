package segmentedfilesystem;

import java.util.Arrays;

abstract class Packet {
    byte status;
    int id;
  
    public void getFileID(){

    }
    public abstract void addToFile(RecievedFile rFile);
  
}
