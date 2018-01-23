import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class EchoServer {
  public static void main(String[] args) throws Exception {
    ServerSocket m_ServerSocket = new ServerSocket(2004,10);
    int id = 0;
    while (true) {
      Socket clientSocket = m_ServerSocket.accept();
      ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
      cliThread.start();
    }
  }
}

class ClientServiceThread extends Thread {
  Socket clientSocket;
  String message;
  int clientID = -1;
  boolean running = true;
  ObjectOutputStream out;
  ObjectInputStream in;
  
  //Account details variables
  private String name;
  private String address;
  private String PPSN;
  private String age;
  private String height;
  private String weight;
  
  //Variables for acc verification
  private boolean userNameFound = false;
  private boolean ageFound = false;


  ClientServiceThread(Socket s, int i) {
    clientSocket = s;
    clientID = i;
  }
  
  //Writes users PPSN and AGE for login verification
  void writeFile(String ppsn,String age,String path) {
	  
	  System.out.println("writing to " + path);
      BufferedWriter out = null;
      try {
          File file = new File(path);
          out = new BufferedWriter(
                new FileWriter(file,true));
              // Print each line to console.
              System.out.println(ppsn);
              // Write line to file.
              out.write(ppsn);
              out.write(" ");
              out.write(age);
              // Write newLine with BufferedReader method.
              out.newLine();
          
          out.close();
      } catch(IOException e) {
          System.out.println("IO error for " + path +
                             ": " + e.getMessage());
      }
	  
  }
  
  //Writes fitness record along with PPSN for look up
  void writeRecordFile(String ppsn,String type,String min,String path) {
	  System.out.println("writing to fitness " + path);
      BufferedWriter out = null;
      try {
          File file = new File(path);
          out = new BufferedWriter(
                new FileWriter(file,true));
          
              // Write records to file.
              out.write(ppsn);
              out.write(":");
              out.write(type);
              out.write(":");
              out.write(min);
              // Write newLine with BufferedReader method.
              out.newLine();
     
          out.close();
      } catch(IOException e) {
          System.out.println("IO error for " + path +
                             ": " + e.getMessage());
      }
	  
  }
  
//Writes meal record along with PPSN for look up
void writeMealFile(String ppsn,String type,String desc,String path) {
	  
	  System.out.println("writing to fitness " + path);
      BufferedWriter out = null;
      try {
          File file = new File(path);
          out = new BufferedWriter(
                new FileWriter(file,true));
          
              // Write records to file.
              out.write(ppsn);
              out.write(":");
              out.write(type);
              out.write(":");
              out.write(desc);
              // Write newLine with BufferedReader method.
              out.newLine();
          
          out.close();
      } catch(IOException e) {
          System.out.println("IO error for " + path +
                             ": " + e.getMessage());
      }
	  
  }

//Reads in the record files , using PPSN for look up
void readRecordFile(String ppsn) throws IOException, ClassNotFoundException {
	
	  boolean hasF = true;
	  
	  System.out.println("Reading from records.txt");
	  FileInputStream fis = null;
      BufferedReader reader = null;
      
      fis = new FileInputStream("records.txt");
      reader = new BufferedReader(new InputStreamReader(fis));
	  
	  String line = "";
	  
	  while((line = reader.readLine()) != null) {
		  
		  //If ppsn match send that record to user
		  if(line.contains(ppsn)) {
              System.out.println(line);
              sendMessage(line);
              message = (String)in.readObject();
              hasF = false;
		  }
		  
	  }
	  
	  //In case user doesn't have records
	  if(hasF) {
		  sendMessage("No records to display, Try creating one!");
          message = (String)in.readObject();
	  }
	  
	  reader.close();
}

//Reads in the Meal record files , using PPSN for look up
void readMealFile(String ppsn) throws IOException, ClassNotFoundException {
	
	boolean hasF = true;
	  
	  System.out.println("MealRecord.txt");
	  FileInputStream fis = null;
    BufferedReader reader = null;
    
    fis = new FileInputStream("MealRecord.txt");
    reader = new BufferedReader(new InputStreamReader(fis));
	  
	  String line = "";
	  
	  while((line = reader.readLine()) != null) {
		  
		  //If ppsn match send that record to user
		  if(line.contains(ppsn)) {
            System.out.println(line);
            sendMessage(line);
            message = (String)in.readObject();
            hasF = false;
		  }
		  
	  }
	  
	//In case user doesn't have records
	  if(hasF) {
		  sendMessage("No records to display, Try creating one!");
          message = (String)in.readObject();
	  }
	  
	  reader.close();
}

  void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
  
  
  //Start menu
  void startMenu() throws ClassNotFoundException, IOException{
	  
	  sendMessage("Press 1 to Register\nPress 2 to LogIn");
	  message = (String)in.readObject();
	  
	  if(message.compareToIgnoreCase("1")==0){
		  register();
	  }
	  if(message.compareToIgnoreCase("2")==0){
		  login();
	  }
  }
  
  
  //Register method
  void register() throws ClassNotFoundException, IOException{


          sendMessage("Please enter your Name: ");
          message = (String) in.readObject();
          name = message;


          sendMessage("Please Enter Address: ");
          message = (String) in.readObject();
          address = message;

          sendMessage("Please Enter PPSN: ");
          message = (String) in.readObject();
          PPSN = message;

          sendMessage("Please Enter Age: ");
          message = (String) in.readObject();
          age = message;
          
          sendMessage("Please Enter Height: ");
          message = (String) in.readObject();
          height = message;
          
          sendMessage("Please Enter weight: ");
          message = (String) in.readObject();
          weight = message;

          System.out.println("Account Created Successfully!");
          
          //Only write PPSN + AGE because thats only whats needed for login verifiaction
          writeFile(PPSN,age, "ppsn.txt");
          
          //Let user know acc created + send to log in page
          sendMessage("Account Created! Press any key to proceed to log in:");
          message = (String) in.readObject();
          login();
   
  }
  
  void login() throws ClassNotFoundException, IOException{
	 
      
      FileInputStream fis = null;
      BufferedReader reader = null;
      
      fis = new FileInputStream("ppsn.txt");
      reader = new BufferedReader(new InputStreamReader(fis));
	  
	  sendMessage("Press enter PPSN to login:");
	  message = (String)in.readObject();
	  
	  String ppsn = "";
	  
	  while((ppsn = reader.readLine()) != null) {
		  
		  System.out.println(ppsn);
		  System.out.println(message);
		  
		  //Splits each line into an array with 2 contents [PPSN] [AGE]
		  String[] strArr = ppsn.split(" ");
		  System.out.println(strArr[0] + " " + strArr[1]);
		  
		  //If username matches
		  if(strArr[0].equals(message)) {
			  
			  System.out.println("entered loop");
			  userNameFound = true;
			  
			  //Username found so check age
			  if(userNameFound) {
				  
				  sendMessage("Enter age to confirm:");
				  message = (String)in.readObject();
				  
				  if(strArr[1].equals(message)) {
					  ageFound = true;
					  
					  //Log in verified , set PPSN to the PPSN entered
					  //for the rest of the functions work else would be null
					  PPSN = strArr[0];
					  System.out.println("Found age");
					  System.out.println(PPSN);
				  }
				  
			  }
			  
		  }
		 
	  }
	  
	  fis.close();
	  
	  //Simple verification messages
	  if(!userNameFound) {
		  sendMessage("PPSN did not match any on our records, Try again or Register! (Press Any key)");
		  message = (String)in.readObject();
		  startMenu();
	  }
	  
	  if(userNameFound && ageFound) {
		  menu();
		 
	  }
	  if(userNameFound && !ageFound) {
		  sendMessage("Age did not match our records for your PPSN, Try again! (Press Any key)");
		  message = (String)in.readObject();
		  login();
	  }
	  else {
		  login();
	  }
	  
  }
  
  void menu() throws ClassNotFoundException, IOException{
	  
	  	sendMessage("Press 'ADD' to add fitness record\nPress 'MEAL' to add a meal record \nPress 'VIEWF' to view fitness record\nPress 'VIEWM' to view meal record\nPress 'EXIT' to terminate connection");
		message = (String)in.readObject();
		
		if(message.compareToIgnoreCase("ADD")==0)
		{
			
			sendMessage("Please enter type e.g Walking/Running:");
			String string1 = (String)in.readObject();
			
			
			sendMessage("Please enter duration (Mins):");
			String dur = (String) in.readObject();
			
			
			//Calls function WriteRecordFile 
			writeRecordFile(PPSN, string1, dur, "records.txt");
			sendMessage("Record created! Press any key to return to main menu!");
			message = (String) in.readObject();
			menu();

		}
		
		else if(message.compareToIgnoreCase("MEAL")==0)
		{
			
			sendMessage("Please enter type of meal e.g Walking/Running:");
			String string1 = (String)in.readObject();
			
			sendMessage("Please enter meal description:");
			String mealDesc = (String) in.readObject();
			
			//Calls function writeMealFile
			writeMealFile(PPSN, string1, mealDesc, "MealRecord.txt");
			sendMessage("Record created! Press any key to return to main menu!");
			message = (String) in.readObject();
			menu();
		}
		
		else if(message.compareToIgnoreCase("VIEWF")==0)
		{
			
			sendMessage("Previous records (Enter any character to display one more)");
			
			//Send PPSN to readRecordFile 
			readRecordFile(PPSN);
			message = (String) in.readObject();
			sendMessage("Press any key to return to menu)");
			message = (String) in.readObject();
			menu();
			
		}
		
		else if(message.compareToIgnoreCase("VIEWM")==0)
		{
			
			sendMessage("Previous Meal records (Enter any character to display one more)");
			
			//Send PPSN to readMealFile
			readMealFile(PPSN);
			message = (String) in.readObject();
			sendMessage("Press any key to return to menu)");
			message = (String) in.readObject();
			menu();
			
		}
	  
	  
  }
  
  
  public void run() {
    //System.out.println("Accepted Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());
    try 
    {
    	out = new ObjectOutputStream(clientSocket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(clientSocket.getInputStream());
		//System.out.println("Accepted Client : ID - " + clientID + " : Address - "+ clientSocket.getInetAddress().getHostName());
		
		do{
			try
			{
				
				startMenu();
				
			}
			catch(ClassNotFoundException classnot){
				System.err.println("Data received in unknown format");
			}
			
    	}while(!message.equals("EXIT"));
      
		System.out.println("Ending Client : ID - " + clientID + " : Address - "
		        + clientSocket.getInetAddress().getHostName());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
