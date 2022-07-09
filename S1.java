import java.util.*;
public class S1{
  public static void main(String [] args){
     Scanner kb = new Scanner(System.in); 
     
     int number = kb.nextInt();
     
     int key = number%4;
     int count = 0;
     for(int i = key; i<=number/5; i+=4){
       count += 1;
     }
     System.out.println(count);
  }
}
       
     
