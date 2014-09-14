import java.io.*;
import java.net.*;
import java.util.*;
public class node {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Enter the ID & D value");
		Scanner in= new Scanner(System.in);
		newnode n=new newnode(in.nextInt(),in.nextInt());
		//System.out.println("Enter the server port number");
		try {
		n.s1=new ServerSocket(2000); //starting socket at port 2000. common for all.
		n.listen();
		System.out.println("waiting...");
		while(true) //wait until we receive the start message from n+1th process. trying to get all of them to start together.
		{
			if(n.ready==true)
				break;
			Thread.sleep(20);
		}
		n.connect();
		while(true) // do until count reaches 20 - that is : 20 times each process executes critical section.
		{
			Random rand = new Random();
			int x = rand.nextInt(91)+10;
			Thread.sleep(x);
			if(n.count>=20)
				break;
			int  w = rand.nextInt(100)+1;
			if(!n.req)
			{
			if(w<=90)
			{
				n.applicationMessage();
			}
			else
			{
				n.sendREQUEST();
			}
			}
		}
		System.out.println("E-N-D"); // end of message passing
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
