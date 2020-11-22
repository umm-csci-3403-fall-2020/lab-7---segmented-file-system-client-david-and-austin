package segmentedfilesystem;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class FileRetriever {
	Stack<DataPacket> data = new Stack<>();
	int port;
	String server;
	DatagramSocket socket;
	HashMap<Integer,RecievedFile> rfiles = new HashMap<>();
	int filesCompleted;

	public FileRetriever(String server, int port) throws SocketException{
        this.port = port;
		this.server = server;
		socket = new DatagramSocket();
		filesCompleted = 0;
	}
	private void connectToServer(DatagramSocket sock) throws IOException{
		byte[] buf = new byte[1028];
		InetAddress address = InetAddress.getByName(server);
		DatagramPacket packet = new DatagramPacket(buf, buf.length,address,port);
		sock.send(packet);
	}

	public void downloadFiles() throws UnknownHostException, IOException {
        // Do all the heavy lifting here.
        // This should
        //   * Connect to the server
        //   * Download packets in some sort of loop
        //   * Handle the packets as they come in by, e.g.,
        //     handing them to some PacketManager class
        // Your loop will need to be able to ask someone
        // if you've received all the packets, and can thus
        // terminate. You might have a method like
        // PacketManager.allPacketsReceived() that you could
        // call for that, but there are a bunch of possible
        // ways.
		boolean allPacketsReceived = false;
		connectToServer(socket);
		PacketMan packetMan = new PacketMan();
		int timer = 1;
		while(filesCompleted < 3){

			byte[] buffer = new byte[1028];
			DatagramPacket recieved = new DatagramPacket(buffer, buffer.length);
			socket.receive(recieved);
			byte[] newBuffer = Arrays.copyOfRange(recieved.getData(),0, recieved.getLength());
			Packet packet = packetMan.buildPacket(newBuffer);
			Integer key = (int) packet.id;
			if(rfiles.containsKey((int) packet.id)){
				packet.addToFile(rfiles.get((int) packet.id));
				
                if (rfiles.get((int) packet.id).allPacketsRecieved()){
					System.out.println("FILE COMPLETED");
                    filesCompleted++;
                    rfiles.remove((int) packet.id);
				}
			}else {
			System.out.println("ADDING NEW FILE");
			rfiles.put((int) packet.id,new RecievedFile());
			packet.addToFile(rfiles.get((int) packet.id));
		}

		/*while(allPacketsReceived == false){
			//System.out.println("Attempting to retrieve packet...");
			socket.receive(freshPacket);
			//System.out.println("Packet retrieved!");
			byte[] unpackedData = freshPacket.getData();
			//checking which file to pass this to
			if(unpackedData[1] == idList[0]){
				if(Byte.compare(idList[0],unpackedData[1]) == 0){
					if(files[0].fileID == idList[0]){
						packetMan.buildPacket(unpackedData,files[0]);
					}else if(files[1].fileID == idList[0]){
						packetMan.buildPacket(unpackedData, files[1]);
					}else if(files[2].fileID == idList[0]){
						packetMan.buildPacket(unpackedData,files[2]);
					}
				}
			} else if(unpackedData[1] == idList[1]){
				if(Byte.compare(idList[1], unpackedData[1]) == 0){
					if(files[0].fileID == idList[1]){
						packetMan.buildPacket(unpackedData,files[0]);
					}else if(files[1].fileID == idList[1]){
						packetMan.buildPacket(unpackedData, files[1]);
					}else if(files[2].fileID == idList[1]){
						packetMan.buildPacket(unpackedData,files[2]);
					}
				}
			} else if(unpackedData[1] == idList[2]){
				if(Byte.compare(idList[2],unpackedData[1]) == 0){
					if(files[0].fileID == idList[2]){
						packetMan.buildPacket(unpackedData,files[0]);
					}else if(files[1].fileID == idList[2]){
						packetMan.buildPacket(unpackedData, files[1]);
					}else if(files[2].fileID == idList[2]){
						packetMan.buildPacket(unpackedData,files[2]);
					}
				}
			} else{
				for(int i = 0; i<=2; i++){
					if(idList[i] == 0){
						System.out.println("Adding " + unpackedData[1] + " to idList[" + i + "]");
						idList[i] = unpackedData[1];
						files[i].fileID = idList[i];
						break;
					}
				}
			}

			if(timer%50 == 0) {
				System.out.println("Completeness check [file1,file2,file3]: " +
						files[0].getPercentCompleted() + ", " +
						files[1].getPercentCompleted() + ", " +
						files[2].getPercentCompleted());
				System.out.println("nullness check [file1,file2,file3]: " +
						(files[0].header != null) + ", " +
						(files[1].header != null) + ", " +
						(files[2].header != null));
			}
			timer++;

			if(files[0].allPacketsRecieved() && files[1].allPacketsRecieved() && files[2].allPacketsRecieved()){
				allPacketsReceived = true;
				System.out.println("All packets recieved.");
			}
*/
		}
	}
}
