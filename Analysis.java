package SummerProject2024;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files

public class Analysis {
    
    /** 
     * @param mostPopular  - this array contains all of those with the most followers
     * @param mostFollowers - this is the largest number of followers on the network
     * @param allUsers - this list will store all of the unique users of Doppler
     * @param names Splits each line in the .txt file into respective users 
     */
    public static void main(String[] args) {
        String mostPopular = "";
        int mostFollowers = 0;
        List<String> allUsers = new ArrayList<String>();
        
        //Exception handling - prevents the code from crashing if the file isn't found
        try {
          File myObj = new File(args[0]);
          Scanner myReader = new Scanner(myObj);

          while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            //I've set the limit to be negative to allow for any size of followers
            String[] names = data.split(" ", -2);
            //This is the current number of follower
            int numFollowers = 0;
            for(String user : names){
                //This integer will act as a boolean - 0 for false and 1 for true
                numFollowers++;
                int unique = 1;
                for(int i=0;i<allUsers.size();i++){
                    //Now, let's check if this user already exists in allUsers
                    if(user.equals(allUsers.get(i))) unique = 0;
                }
                //I will only add the unique user to allUsers if they don't already exist
                if(unique==1) allUsers.add(user);
            }

            numFollowers--; //Users aren't necessarily following themselves
            if(numFollowers > mostFollowers){
              mostPopular = names[0]; //This should be the most followed
            }
            else if(numFollowers == mostFollowers){
              if(mostPopular.compareTo(names[0]) > 0) mostPopular = names[0];
              //Replace the previous one with the preceding alphabetical user
            }
          }
          myReader.close();

          //Just quickly sorting allUsers in alphabetical order for later use
          Collections.sort(allUsers);
          //This would tehn allow us to pick the first index for task 2
          //Now, I want to create an adjacency matrix for all of the users

        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
      }
}
