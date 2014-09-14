
import java.io.*;
import java.util.*;
import java.net.*;
@SuppressWarnings("unused")
/*this class is used to listen for each connection in the server 
 */
public class listenThread extends Thread {
private newnode n;
private Socket s;
 public listenThread(Socket s1,newnode n1)
 {
	 System.out.println("each connection constructor");
	 n=n1;
	 s=s1;
 }
public void run()
 {
	    int i=0;
		System.out.println("each connection listen");
		try (
				ObjectInputStream in = new ObjectInputStream(s.getInputStream());
				ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			)
		{
			while(true)
			{
			String message=null;
			message = in.readUTF();
			if(message.startsWith("START"))
			{
				n.ready=true;
				//System.out.println("HERE !! ");
			}
			if(message.startsWith("APPLICATION"))
			{
				n.timeStamp=n.timeStamp+n.d;
				String str[] = message.split(" ");
				int x = Integer.parseInt(str[1]);
				if(n.timeStamp<x+n.d)
				{
					n.timeStamp=x+n.d;
				}
				System.out.println("new timestamp after application message = "+n.timeStamp);
			}
			if(message.startsWith("REQUEST"))
			{
				n.timeStamp=n.timeStamp+n.d;
				String str[] = message.split(" ");
				int x=Integer.parseInt(str[2]);
				queueOP.insert(str[1]+" "+str[2],n);
				//for(int j=0;j<n.queue.length && n.queue[j]!=null;j++)
					//System.out.println("Queue @ "+j+"th pos= "+n.queue[j]);
				if(n.timeStamp<x+n.d)
				{
					n.timeStamp=x+n.d;
				}
				System.out.println("new timestamp after REQUEST = "+n.timeStamp);
				n.timeStamp=n.timeStamp+n.d;//------------------------
				out.writeUTF("REPLY "+n.timeStamp);
				out.flush();
			}
			if(message.startsWith("RELEASE"))
			{
				n.timeStamp=n.timeStamp+n.d;
				String str[] = message.split(" ");
				int x = Integer.parseInt(str[1]);
				if(n.timeStamp<x+n.d)
				{
					n.timeStamp=x+n.d;
				}
				queueOP.delete(n);
				if(n.queue[0]!=null)
				{
				//for(int j=0;j<n.queue.length && n.queue[j]!=null;j++)
						//System.out.println("Queue after delete "+j+"th pos= "+n.queue[j]);
				String data[]=n.queue[0].split(" ");
				if(Integer.parseInt(data[0])==n.id)
					{
					n.criticalSection();
					}
				}
			}
		}
		}
		catch(EOFException e)
		{
		   
		}
		catch (Exception e) 
		{	
			e.printStackTrace();
		}
 
  }
 
}
