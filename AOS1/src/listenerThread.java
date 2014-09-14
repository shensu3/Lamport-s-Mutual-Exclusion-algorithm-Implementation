import java.io.*;
import java.util.*;
import java.net.*;
@SuppressWarnings("unused")
/*this Thread is used to create a number of threads for each connection
 * used for the concurrent serever model.
 */
public class listenerThread extends Thread {
	private ServerSocket s1;
	private Boolean flag; 
	private newnode n;
public listenerThread(ServerSocket s,newnode n1)
{
	s1=s;
	flag=true;
	n=n1;
	System.out.println("listen thread of process : "+n.id);
}
public void run()
{
	while(flag)
	{
			try {
			Socket clientsock = n.s1.accept();
			new listenThread(clientsock,n).start(); //start thread to listen for each client
	            }
			catch (Exception e) 
			{
				e.printStackTrace();
			}
	}
}

public void stopRunning()
{
	flag=false;
}

}
