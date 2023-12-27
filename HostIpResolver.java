import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  
public class HostIpResolver {
    public static void main(String[] args) {
               try{
            ArrayList list;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now;
            String Time;
            FileWriter writer =new FileWriter("log.txt",true);
            PrintWriter pwriter =new PrintWriter(writer);
            
            
            try{
                        FileInputStream in =new FileInputStream("ip.out");
                        ObjectInputStream InAsObj =new ObjectInputStream(in);
                        Object InetAdd = InAsObj.readObject();
                        list=new ArrayList();
                        int size =(int)InetAdd ;
                        
                            for(int i=0 ;i<size;i++){
                            Object InetAdd0 = InAsObj.readObject();
                            Object o =(Object)InetAdd0 ;
                            list.add(o);}
            }        
                 catch (ClassCastException | ClassNotFoundException cce){
                    // Can't read it, create a blank one
                    list = new ArrayList();
                }
                 catch (FileNotFoundException fnfe){
                // Create a blank vector
                list = new ArrayList();
                
            }
         
             for (;;){
                System.out.println ("Menu :-");
                System.out.println ("1..Enter a new host name to be resolved");
                System.out.println ("2..Enter a new IP to be resolved");
                System.out.println ("3..Read host names to be resolved from file");
                System.out.println ("4..Display all IP addresses");
                System.out.println ("5..Save and quit");
                
               String response = reader.readLine();
              
               int choice = Integer.parseInt (response);
               
               switch (choice){
                   case 1:  
                       Time= LocalDateTime.now().format(dtf);  
                       System.out.println ("Enter a host name :-");
                       String name=reader.readLine();
                       for (int flag=0 ; flag < 3 ; ) {
                       int noError = 0;
                       try{
                       InetAddress address = InetAddress.getByName(name);
                       address.getHostName();//This revers look up just for if someone onter an ip address insted of hostname 
                                             //No indication will given If this lookup fails and the address will saved as IP/IP
                       list.add(address);
                       pwriter.println(Time+"  "+address);
                       pwriter.flush();
 
                       }
                       catch (UnknownHostException e) {
                       flag = flag+1;
                       noError =17;
                        }
                       if(noError != 17)break;
                       if(flag<3)System.out.println ("please waite .... something happened at resolving");
                       if(flag==3)System.out.println ("Sorry could not resolve "+name);//System.out.println ("Sorry could not resolve_hostname");
                      //list.add(Time+"  "+name+":"+"Could not be resolvef!");
                       pwriter.println(Time+"  "+name+":"+"Could not be resolvef!");
                       pwriter.flush();
                       
               }//for the for loop in case1
                       break;
                  case 2: 
                  {  
                     System.out.println ("Please Enter IP address :-");
                     System.out.println ("Either as a Dotted Decimal Notation in the following format :x.x.x.x");
                     System.out.println ("Or as Numbers in Following format format :x x x x");
                     
                     String IP=reader.readLine();
                System.out.println ("Which type did you use ?");
                System.out.println ("1..Dotted Decimal Notation");
                System.out.println ("2..Numbers");
               String options = reader.readLine();
               int cho = Integer.parseInt (options);
               Time= LocalDateTime.now().format(dtf); 
               InetAddress IPaddress ;
               switch (cho){
                   case 1:
                   try{
                       
                IPaddress = InetAddress.getByName(IP);
                String hostname =IPaddress.getHostName();
                //System.out.println(hostname);
                if(hostname.equals(IPaddress.getHostAddress())){
                    System.out.println("cant resolve");
                    //list.add(Time+"  "+IPaddress.getHostAddress()+" :"+"Could not be resolved!");
                    pwriter.println(Time+"  "+IPaddress.getHostAddress()+" :"+"Could not be resolved!");
                    pwriter.flush();
                }
                else list.add(IPaddress);
                pwriter.println(Time+"  "+IPaddress);
                pwriter.flush();
                   }
                   catch (UnknownHostException uhe){
                    System.out.println("Wrong IP");} 
                    break;
                    
                   case 2:
                      try{ 
                   // Convert the string that repersent the numbers of ip into array of bytes
                   String[] parts = IP.split("\\s+");
                   byte[] doteddIP = new byte[parts.length];
                    for (int i = 0; i < parts.length; i++) {
                    //convert the numbers that are larger than 127 into negative byte version 
                    if(Integer.parseInt(parts[i].trim())>127)parts[i]=String.valueOf(Integer.parseInt(parts[i].trim()) - 256);
                     byte  number = Byte.parseByte(parts[i].trim());
                      doteddIP[i] = number;}
                    
                IPaddress = InetAddress.getByAddress(doteddIP);
                String hostname =IPaddress.getHostName();
                if(hostname.equals(IPaddress.getHostAddress())){
                    System.out.println("cant resolve");
                    //list.add(Time+"  "+IPaddress.getHostAddress()+" :"+"Could not be resolved!");
                    pwriter.println(Time+"  "+IPaddress.getHostAddress()+" :"+"Could not be resolved!");
                    pwriter.flush();
                }
                else list.add(IPaddress);
                pwriter.println(Time+"  "+IPaddress);
                pwriter.flush();
                     }
                      //this Error will throw if the usre enter number larger than 127
                       catch (IOException |NumberFormatException ioe){
                    System.out.println("Wrong IP"); 
                    }
                      break;
                      
                      
               }//the end of switch in case 2
               break;}
                case 3:
                {
                    try{
                System.out.println ("Please enter the file name");
                String file = reader.readLine();
                BufferedReader read=new BufferedReader(new FileReader(file));
                    
                 Time= LocalDateTime.now().format(dtf);                      
                 while(read.ready()){ 
                       String add=read.readLine();
                       
                       for (int flag=0 ; flag < 3 ; ) {
                       int noError =0;
                       try{
                       InetAddress address = InetAddress.getByName(add);
                       list.add(address);
                       pwriter.println(Time+"  "+address);
                       pwriter.flush();
                       

                       }
                       catch (UnknownHostException e) {
                       flag = flag+1;
                       noError =17;
                        }
                       if(noError != 17)break;
                       if(flag<3)System.out.println ("please waite .... something happened at resolving ");
                       if(flag==3)System.out.println ("Sorry could not resolve "+add);//System.out.println ("Sorry could not resolve_hostname");
                       //list.add(Time+"  "+add+":"+"Could not be resolvef!");
                       pwriter.println(Time+"  "+add+":"+"Could not be resolve !");
                       pwriter.flush();
                       
                       }
                 }
                }
                 catch(FileNotFoundException o){
                     System.out.println("could not find the file");
                    }
                break;}
                case 4:
                {
                    for (int i=0 ; i < list.size() ; i++) {
                        System.out.println(list.get(i));
                    }
                    break;}
                case 5:
                    FileOutputStream fout =new FileOutputStream( "ip.out" );
                    ObjectOutputStream oout = new ObjectOutputStream ( fout );  
                        oout.writeObject (list.size());
                        for(int i=0 ;i<list.size();i++){
                        oout.writeObject (list.get(i));
                }
                        fout.close();
                        System.exit(0);
              
               }//for the switch
             }
               }
            catch (IOException ioe){
            System.err.println ("I/O error");
        }
        
        
    }
    
}
