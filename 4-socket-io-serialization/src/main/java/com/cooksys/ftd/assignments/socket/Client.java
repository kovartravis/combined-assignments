package com.cooksys.ftd.assignments.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Client {

    /**
     * The client should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" and "host" properties of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.RemoteConfig} object to create a socket that connects to
     * a {@link Server} listening on the given host and port.
     *
     * The client should expect the server to send a {@link com.cooksys.ftd.assignments.socket.model.Student} object
     * over the socket as xml, and should unmarshal that object before printing its details to the console.
     * @throws JAXBException 
     * @throws IOException 
     */
    public static void main(String[] args) throws JAXBException, IOException {
    	
    	Socket socket = null;
		JAXBContext context = Utils.createJAXBContext();
		Unmarshaller unmarshall = null;
		InputStream in = null;
		Student student = null;
		Integer port = null;
		String host = null;
		XMLInputFactory input = XMLInputFactory.newInstance();
		XMLStreamReader streamreader = null;
		Config config = Utils.loadConfig("config.xml", context);

		port = config.getRemote().getPort();
		host = config.getRemote().getHost();
		
		System.out.println("Connecting...");
		try {
			socket = new Socket(host, port);
			in =  socket.getInputStream();
			streamreader = input.createXMLStreamReader(in);
		} catch (UnknownHostException e) {
			socket.close();
			e.printStackTrace();
		} catch (IOException e) {
			socket.close();
			e.printStackTrace();
		} catch (XMLStreamException e) {
			socket.close();
			in.close();
			e.printStackTrace();
		}
		
		System.out.println("Receiving File...");
		try {
			unmarshall = context.createUnmarshaller();
			student = (Student) unmarshall.unmarshal(streamreader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		System.out.println(student);
		
        try {
			in.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
