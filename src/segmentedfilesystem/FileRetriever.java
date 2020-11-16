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
		InetAddress address = InetAddress.getByName(server);

		RecievedFile file1 = new RecievedFile();
		RecievedFile file2 = new RecievedFile();
		RecievedFile file3 = new RecievedFile();
		PacketMan packetMan = new PacketMan();
		byte[] idList = new byte[3];


		while(allPacketsReceived == false){
			byte[] buf = new byte[1028];
			DatagramPacket freshPacket = new DatagramPacket(buf, buf.length);
			socket.receive(freshPacket);
			byte[] unpackedData = freshPacket.getData();
			//checking which file to pass this to
			for(byte id:idList){
				if(Byte.compare(id,unpackedData[1]) == 0){
					if(file1.fileID == id){
						packetMan.buildPacket(unpackedData,file1);
					}else if(file2.fileID == id){
						packetMan.buildPacket(unpackedData, file2);
					}else if(file3.fileID == id){
						packetMan.buildPacket(unpackedData,file3);
					}
				}
			}

			if(file1.allPacketsRecieved() && file2.allPacketsRecieved() && file3.allPacketsRecieved()){
				allPacketsReceived = true;
				System.out.println("All packets recieved.");
			}

		}

	}

}
