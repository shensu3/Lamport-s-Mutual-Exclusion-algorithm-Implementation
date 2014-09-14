import java.io.*;
import java.util.*;
//class that handles all the queue operations
public class queueOP 
{
public static void insert(String str,newnode n) // code to insert element into the queue
{	
String msg[]=str.split(" ");
int times=Integer.parseInt(msg[1]);
int id=Integer.parseInt(msg[0]);
for(int i=0;i<n.queue.length;i++)
{
	if(n.queue[i]==null)
	{
		n.queue[i]=str;
		break;
	}
	else
	{
		String tss[] = n.queue[i].split(" ");
		int ts= Integer.parseInt(tss[1]);
		int qid=Integer.parseInt(tss[0]);
		if(ts>times)
		{
			for(int ix=n.queue.length-1;ix>i;ix--)
			{
				n.queue[ix]=n.queue[ix-1];
			}
			n.queue[i]=str;
			break;
		}
		else if(ts==times)
		{
			if(id<qid)
			{
				for(int ix=n.queue.length-1;ix>i;ix--)
				{
					n.queue[ix]=n.queue[ix-1];
				}
				n.queue[i]=str;
				break;
			}
		}
	}
}
}

public static void delete(newnode n) // code to delete element from the queue
{
	for(int i=0;i<n.queue.length-2;i++)
	{
		n.queue[i]=n.queue[i+1];	
	}
	n.queue[n.queue.length-1]=null;
}
	
}
