package segmentedfilesystem;



public class HeaderPacket extends Packet{
    private String filename;

    public HeaderPacket(byte[] packetBytes) {
        String filename = new String(packetBytes);
        this.filename = filename;
    }

    public String getFileName(){
        return filename;
    }
    public void addToFile(RecievedFile rFile){
        rFile.addHP(this);
    }
}
