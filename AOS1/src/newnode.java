import java.io.*;
import java.net.*;
import java.util.*;
//class which stores server and client variables. as well as methods
public class newnode 
{
public int count;	
public boolean ready;
public String[] queue=new String[10];
public String hosts[]={"net01.utdallas.edu","net02.utdallas.edu","net03.utdallas.edu","net04.utdallas.edu"
		,"net05.utdallas.edu","net06.utdallas.edu","net07.utdallas.edu",
		"net08.utdallas.edu","net09.utdallas.edu","net10.utdallas.edu"};
public long timeStamp=0;
public int d;
public boolean req;
public listenerThread ls;
public ServerSocket s1=null;
public int id;
public Socket clients[]=new Socket[10];
public ObjectOutputStream outs[]=new ObjectOutputStream[10];
public ObjectInputStream in[] = new ObjectInputStream[10];
public newnode(int id,int d) // initialize 
{
	ready=false;
	this.d=d;
	this.id=id;
	req=false;
	count=0;
	for(int i=0;i<queue.length;i++)
		queue[i]=null;
}

public newnode() {
	// TODO Auto-generated constructor stub
}

public void listen() // multithreaded approach.
{
	ls = new listenerThread(s1,this);
	ls.start();
}
public void connect() // connect clients to server
{
	Scanner input= new Scanner(System.in);
	for(int i=0;i<this.clients.length;i++)
	{
	  try 
	  {
		clients[i]=new Socket(hosts[i],2000);
	  }
	  catch (Exception e) 
	  {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	}
	for(int i=0;i<clients.length;i++)
	{
		try {
			outs[i]=new ObjectOutputStream(clients[i].getOutputStream());
			in[i]=new ObjectInputStream(clients[i].getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
public void sendStart() //n+1th node sends start message
{
	try
	{
	for(int i=0;i<clients.length;i++)
	{
			outs[i].writeUTF("START");
			outs[i].flush();
			outs[i].close();
	}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
public void applicationMessage() // application message sending code
{
	try
	{
	Random rand = new Random();
	int  selection = rand.nextInt(clients.length);
	timeStamp=timeStamp+d;
	String message= "APPLICATION "+timeStamp;
        outs[selection].writeUTF(message);
        outs[selection].flush();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}

public void sendREQUEST() // sending request and getting replies
{
	try
	{
	req=true;	
	timeStamp=timeStamp+d;
	String message = "REQUEST "+id+" "+timeStamp;
	for(int i=0;i<clients.length;i++)
	{
        outs[i].writeUTF(message);
        outs[i].flush();
	}
	message=null;
	for(int i=0;i<clients.length;i++)
	{
        message=in[i].readUTF();
        timeStamp=timeStamp+d;
		String str[] = message.split(" ");
		int x = Integer.parseInt(str[1]);
		if(timeStamp<x+d)
		{
			timeStamp=x+d;
		}
		System.out.println("Reply @ "+timeStamp);
	}
	String data[]=queue[0].split(" ");
	if(Integer.parseInt(data[0])==id && queue[0]!=null)
		{
		criticalSection();
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}

public void criticalSection() // code to be executed in critical section
{
	try
	{
	++count;
	System.out.println("critical section "+id+" "+timeStamp);
	PrintWriter pw = new PrintWriter(new FileWriter("log-file.txt",true));
	pw.println(id+"\t"+"Entering"+"\t"+timeStamp);
	Thread.sleep(20);
	timeStamp=timeStamp+d;
	String message = "RELEASE "+timeStamp;
	for(int i=0;i<clients.length;i++)
	{
			//outs[i].reset();
			outs[i].writeUTF(message);
			outs[i].flush();
	}
	pw.println(id+"\t"+"Leaving"+"\t"+timeStamp);
	pw.close();
	req=false;
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
} 


}
