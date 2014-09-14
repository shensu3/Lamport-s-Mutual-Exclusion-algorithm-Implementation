import java.io.*;
import java.net.*;
import java.util.*;
public class starter {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		newnode n=new newnode();
		n.connect();
		n.sendStart(); 
		System.out.println("Sent message to start all processes !");
	}
}
