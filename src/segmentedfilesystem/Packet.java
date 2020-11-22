package segmentedfilesystem;

abstract class Packet {
    private int id;
  
    public int getFileID(){
    return id;
    }
    public void setFileID(int newID){
        id = newID;
    }

    public abstract void addToFile(RecievedFile rFile);
   
  
}
