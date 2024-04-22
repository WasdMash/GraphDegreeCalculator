package SummerProject2024;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files

public class Analysis {
    
    /** 
     * @param mostPopular  - this array contains all of those with the most followers
     * @param mostFollowers - this is the largest number of followers on the network
     * @param allUsers - this list will store all of the unique users of Doppler
     * @param names Splits each line iY﻿our Text Here!n the .txt file into respective users 
     */
    public static void main(String[] args) {
        String mostPopular = "";
        String followsTheMost = ""; //User who follows the most people
        int mostFollowers = 0;
        int edges = 0;
  
        List<String> allUsers = new ArrayList<String>();
        List<String> allConnections = new ArrayList<String>();

        //Perhaps create a dictionary for each user - key is the name and the data is the 
        //number of people who follow them
        
        //Exception handling - prevents the code from crashing if the file isn't found
        try {
          File myObj = new File(args[0]);
          Scanner myReader = new Scanner(myObj);

          //First pass for accomplishing tasks 1 and 3
          while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            allConnections.add(data);
            //I've set the limit to be negative to allow for any size of followers
            String[] names = data.split(" ", -2);
            //This is the current number of follower
            int numFollowers = 0;
            for(String user : names){
                //This integer will act as a boolean - 0 for false and 1 for true
                numFollowers++;
                edges++;
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
              followsTheMost = names[0]; //This should be the most followed
            }
            else if(numFollowers == mostFollowers){
              if(followsTheMost.compareTo(names[0]) > 0) followsTheMost = names[0];
              //Replace the previous one with the preceding alphabetical user
            }
          }
          myReader.close();

          edges -= allUsers.size();
          calculateDensity(edges, allUsers.size());
          //Just quickly sorting allUsers in alphabetical order for later use
          Collections.sort(allUsers);
          mostPopular = findMostFollowers(allUsers, allConnections);
          System.out.println("The user with the most followers is " + mostPopular);
          System.out.println("The user who follows the most people is" + followsTheMost); 
          

        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
      }

    static void calculateDensity(int edges, int nodes){
          //Accomplishing task 1
          //This should give all of the edges excluding the ability for a user to follow themselves
          float density = edges / (nodes * (nodes - 1));
          System.out.println("\nThe density of the graph is: " + String.format("%s", density));
    }

    static String findMostFollowers(List<String> allUsers, List<String> allConnections){
      int maxFollowers = 0;
      String mostPopular = "";
      Dictionary<String, Integer> numberOfFollowers= new Hashtable<>();
      //This would then allow us to pick the first index for task 3
        //Now, let's search through allConnections for each user in allUsers
        for(int i=0; i<allUsers.size();i++){
          int numFollowers = 0;
          for(int j=0; j<allConnections.size();j++){
            //This user clearly follows our allUsers(j) - could also store their name in a linked list
              if(allConnections.get(j).contains(allUsers.get(i))) numFollowers++;
          }
          numFollowers --;
          //Assign the dicitonary value for how many followers that a single user has
          numberOfFollowers.put(allUsers.get(i), numFollowers);
        }
        //Manually iterating across the dictionary to get the user with the most followers
        Enumeration<String> userNames = numberOfFollowers.keys();
        while(userNames.hasMoreElements()){
          String user = userNames.nextElement();
          int followers = numberOfFollowers.get(user);
          //Obviously, if they have more followers, choose them
          if(followers > maxFollowers){
            maxFollowers = followers;
            mostPopular = user;
          }
          else if(followers == maxFollowers){
            //If two users have the same number of followers, then choose the one who is more alphabetical
            if(mostPopular.compareTo(user) > 0) mostPopular = user;
          } 
        }      
      return "";
    }
}
