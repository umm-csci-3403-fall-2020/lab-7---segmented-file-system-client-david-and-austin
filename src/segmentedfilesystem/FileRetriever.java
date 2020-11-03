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
		boolean allFilesDownloaded = false;
		InetAddress address = InetAddress.getByName(server);
		RecievedFile file1 = new RecievedFile();
		RecievedFile file2 = new RecievedFile();
		RecievedFile file3 = new RecievedFile();
		while(allFilesDownloaded == false){
			byte[] buf = new byte[1028];
			DatagramPacket freshPacket = new DatagramPacket(buf, buf.length);
			socket.receive(freshPacket);

			if(freshPacket.getData()[0] % 2 == 0){
				//it's a headerpacket
			}else{
				//it's a data packet
			}
			if()
		}

	}

}
