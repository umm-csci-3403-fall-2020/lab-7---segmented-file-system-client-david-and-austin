package segmentedfilesystem;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.*;
import java.util.Stack;

public class FileRetriever {
	Stack<DataPacket> data = new Stack<>();
	int port;
	String server;
	DatagramSocket socket;

	public FileRetriever(String server, int port) throws SocketException{
        this.port = port;
		this.server = server;
		socket = new DatagramSocket(port);
	}

	public void downloadFiles(RecievedFile[] files) throws UnknownHostException, IOException {
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
		InetAddress address = InetAddress.getByName(server);
		PacketMan packetMan = new PacketMan();
		byte[] idList = new byte[3];
		System.out.println("This is what's in idList: " + idList[0] );
		byte[] buf = new byte[1028];
		System.out.println("Requesting packets...");
		DatagramPacket freshPacket = new DatagramPacket(buf, buf.length,address,port);
		socket.send(freshPacket);

		int timer = 1;
		while(allPacketsReceived == false){
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

		}
	}

}
