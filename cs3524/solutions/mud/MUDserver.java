package cs3524.solutions.mud;

import java.rmi.Naming;
import java.lang.SecurityManager;
import java.net.InetAddress;
import java.rmi.server.UnicastRemoteObject;


public class MUDserver{
	public static void main(String args[]){
		if(args.length < 2){
			System.err.println("Usage:\n java mudserver <registryport> <serverport>");
			return;
		}

		try{
			String hostName = (InetAddress.getLocalHost()).getCanonicalHostName();
			int registryPort = Integer.parseInt(args[0]);
			int serverPort = Integer.parseInt(args[1]);
			
			System.out.println("server running on port "+ Integer.toString(registryPort));

			System.setProperty("java.security.policy", "mud.policy");
			System.setSecurityManager(new SecurityManager());

			MUDserverImpl MUDServer = new MUDserverImpl();

			MUDserverInterface stub = (MUDserverInterface)UnicastRemoteObject.exportObject(MUDServer, serverPort);


			Naming.rebind("rmi://" + hostName + ":" + registryPort + "/MUDserver", stub);

			System.out.println("Host Name= " + hostName);
			System.out.println("Server port= " + serverPort);
			System.out.println("Registry port= " + registryPort);

			MUDServer.makeMUD("default");

		}catch(java.net.UnknownHostException e){
			System.err.println( "Java can't find the localhost");
		}catch(java.io.IOException e){
			System.out.println("failed to register");
			System.err.println(e.getMessage());
		}
	}
}
