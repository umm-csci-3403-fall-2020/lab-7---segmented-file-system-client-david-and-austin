package segmentedfilesystem;

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
	private HashMap<Integer,RecievedFile> rfiles = new HashMap<>();
	int filesCompleted;

	public FileRetriever(String server, int port) throws SocketException{
        this.port = port;
		this.server = server;
		socket = new DatagramSocket();
		filesCompleted = 0;
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
		connectToServer(socket);
		PacketMan packetMan = new PacketMan();
		while(filesCompleted < 3){

			byte[] buffer = new byte[1028];
			DatagramPacket recieved = new DatagramPacket(buffer, buffer.length);
			socket.receive(recieved);
			byte[] newBuffer = Arrays.copyOfRange(recieved.getData(),0, recieved.getLength());
			Packet packet = packetMan.buildPacket(newBuffer);
			Integer key = (int) packet.getFileID();
			RecievedFile fileToAdd = rfiles.get(key);
			addPacketToCorrectFile(packet, key, fileToAdd);
		}
	}

	private void addPacketToCorrectFile(Packet packet, Integer key, RecievedFile fileToAdd) {
		if(rfiles.containsKey(key)){
			packet.addToFile(fileToAdd);
		    if (fileToAdd.allPacketsRecieved()){
				System.out.println("FILE COMPLETED");
		        filesCompleted++;
		        rfiles.remove(key);
			}
		}else {
			//If a packet contains an unknown file ID it will create a new recievedFile
		createNewFile(packet, key);
}
	}

	private void createNewFile(Packet packet, Integer key) {
		System.out.println("ADDING NEW FILE");
		rfiles.put(key,new RecievedFile());
		packet.addToFile(rfiles.get(key));// cannot use fileToAdd since it technically does not exist yet
	}
	private void connectToServer(DatagramSocket sock) throws IOException{
		byte[] buf = new byte[1028];
		InetAddress address = InetAddress.getByName(server);
		DatagramPacket packet = new DatagramPacket(buf, buf.length,address,port);
		sock.send(packet);
	}

}
