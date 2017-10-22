package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Shared static methods to be used by both the {@link Client} and {@link Server} classes.
 */
public class Utils {
    /**
     * @return a {@link JAXBContext} initialized with the classes in the
     * com.cooksys.socket.assignment.model package
     */
    public static JAXBContext createJAXBContext() {
    	
    	JAXBContext context = null;
    	
    	try {
			context = JAXBContext.newInstance(Student.class, Config.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

        return context;
    }

    /**
     * Reads a {@link Config} object from the given file path.
     *
     * @param configFilePath the file path to the config.xml file
     * @param jaxb the JAXBContext to use
     * @return a {@link Config} object that was read from the config.xml file
     */
    public static Config loadConfig(String configFilePath, JAXBContext jaxb) {
    	
    	Unmarshaller unmarshal = null;
    	Config config = null;
    	
    	try {
	        unmarshal = jaxb.createUnmarshaller();
	        config = (Config) unmarshal.unmarshal(new File(configFilePath));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    		
        return config;
    }
    
    public static void main(String[] args) throws JAXBException{
    	
    	Config config = new Config();
    	LocalConfig local = new LocalConfig();
    	RemoteConfig remote = new RemoteConfig();
    	Student student = new Student();
    	FileOutputStream fout = null;
    	JAXBContext contextConfig = JAXBContext.newInstance(Config.class);
    	JAXBContext contextStudent = JAXBContext.newInstance(Student.class);
    	Marshaller marshall = null;
    	
    	local.setPort(8084);
    	remote.setHost("127.0.0.1");
    	remote.setPort(8084);
    	config.setLocal(local);
    	config.setRemote(remote);
    	config.setStudentFilePath("student.xml");
    	
    	student.setFavoriteIDE("Notepad++");
    	student.setFavoriteLanguage("Intelx86");
    	student.setFavoriteParadigm("Imperative");
    	student.setFirstName("Travis");
    	student.setLastName("Kovar");
    
    	try {
    		marshall = contextConfig.createMarshaller();
			fout = new FileOutputStream("config.xml");
			marshall.marshal(config, fout);
			fout.close();
			
			marshall = contextStudent.createMarshaller();
			fout = new FileOutputStream("student.xml");
			marshall.marshal(student, fout);
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
