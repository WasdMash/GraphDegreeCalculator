import java.util.ArrayList;
import java.util.List;

class FollowerData{
  int[] numberOfFollowers;
  String mostFollowed = "";
  FollowerData(int[] numFollowers, String mostPopular){
    numberOfFollowers = numFollowers;
    mostFollowed = mostPopular;
  }

  //Getters / Accessors
  public int[] getNumFollowersArray(){return numberOfFollowers;}
  public String getMostFollowed(){return mostFollowed;}
}

public class Analysis {
    
    /** 
     * @param mostPopular  - this array contains all of those with the most followers
     * @param mostFollowers - this is the largest number of followers on the network
     * @param allUsers - this list will store all of the unique users of Doppler
     * @param names Splits each line iY﻿our Text Here!n the .txt file into respective users 
     */

    public static void main(String[] args) {
        GraphCreator network = new GraphCreator();
        GraphData users = network.collateAllUsers(args[0], false);
        //args[0] will be the first argument passed into the code, which should be the filename to open
        //The adjacency matrix will be the data structure used to search through the graph for easier traversal throughout the code
        int[][] adjacencyMatrix = network.createGraph(args[0]);

        //This object will easily store data about who has how many followers for easy access later
        FollowerData followerInfo = findMostFollowers(users, adjacencyMatrix);
        System.out.println("The user who has the most followers is: " + followerInfo.getMostFollowed());
        //This code should complete task 4
        findTwoDegrees(users, adjacencyMatrix);

        //This function will complete task 5
        System.out.println("This network has a median of " + String.format("%s", medianFollowers(followerInfo) + " followers."));

        //This function will complete task 6
          //Just use a breadth-first search and then return the user with the shortest distance
        System.out.println("The best person to enrol to spread information is " + bestPropagator(adjacencyMatrix, users));
        
      } 

    static FollowerData findMostFollowers(GraphData users, int[][] adjacencyMatrix){
      int[] numFollowers = new int[users.getUsersArray().length];
      for(int i=0;i<numFollowers.length;i++){
        for(int j=0;j<numFollowers.length;j++){
            if(adjacencyMatrix[i][j] == 1){
              //If there's a match, then that means that this user has another follower
              numFollowers[i]++;
            }
        }
      }
      int maxFollowers = 0;
      int maxFollowIndex = 0;
      for(int i=0;i<numFollowers.length;i++){
        if(numFollowers[i] > maxFollowers){
          //This is the index of the user with the most followers
          maxFollowers = numFollowers[i];
          maxFollowIndex = i;
        }
      }

      //Don't forget to assign the numFollowers array to FollowerData, which can then be used to complete task 5 efficiently
      FollowerData followerInfo = new FollowerData(numFollowers, users.getUsersArray()[maxFollowIndex]);
      return followerInfo;
    }

    //This function should return the number of users who are at 2 degrees of separation
    //Away from the OG user in the text file
    //Can later be optimised to complete task 6 as well but can't be bothered to do that yet
    static void findTwoDegrees(GraphData users, int[][] network){
      //This integer stores the number of users found at 2 degrees of separation
      int twoDegrees = 0;
      int maxFollowersIndex = 0;
      int[] followerIndices = new int[users.getUsersArray().length];

      //Finding the index of the original OG user at the start of the file
      int OGUserIndex = 0;
      for(int i=0;i<users.getUsersArray().length;i++){
        if(users.getUsersArray()[i] == users.getOGuser()){
          OGUserIndex = i;
          break;
        }
      }

      //Find everyone who follows that original user
      //Search through the array, silly
      for(int i=0;i<followerIndices.length;i++){
        if(network[OGUserIndex][i]==1){
          //Found another follower of P
          followerIndices[maxFollowersIndex++] = i;
        }
      }

      //Stores all of the unique second degree followers
      //Might be helpful to return in the output of the function as well for visualisationn
      List<String> uniqueSecondDegFollowers = new ArrayList<String>();


      //These followers do not directly follow OgUser, therefore are 2nd degree followers
      for(int i=0;i<maxFollowersIndex;i++){
          for(int j=0;j<followerIndices.length;j++){
            if(network[followerIndices[i]][j]==1){
              //This is a follower of P's followers - now check to make sure that they also don't follow OGfirstUser
              String secondDegFollowers = users.getUserConnections()[j];
              //Must also check to make sure that OGUser doesn't follow them either
              if(!secondDegFollowers.contains(users.getOGuser())){
                //Just here to make sure that there are no duplicates
                if(!uniqueSecondDegFollowers.contains(users.getUsersArray()[j])){
                  uniqueSecondDegFollowers.add(users.getUsersArray()[j]);
                  twoDegrees++;
                }
              } 
            }
          }
          
      }
      System.out.println("The first user in the file, " + users.getOGuser() + ", has a grand number of " + String.format("%s", twoDegrees) + " users at 2 degrees of separation");
        
      }    

    //This function will complete task 5 by returning the median number of followers in the network
    static int medianFollowers(FollowerData followerInfo){
      //Might just bubble sort the array to get it in order
      int[] sortedNumFollowersArray = followerInfo.getNumFollowersArray();
      boolean swapped = true;
      int n = sortedNumFollowersArray.length;
      while(swapped && n > 0){
        swapped = false;
        n--;
        //A single pass in the bubble sort algorithm
        for(int i=0; i<n;i++){
          if(sortedNumFollowersArray[i] > sortedNumFollowersArray[i+1]){
            //Basically swap these two numbers around
            int temp = sortedNumFollowersArray[i+1];
            sortedNumFollowersArray[i+1] = sortedNumFollowersArray[i];
            sortedNumFollowersArray[i] = temp;
            swapped = true;
          }
        }
      }

      float middle = sortedNumFollowersArray.length / 2;
      if(middle != (float)Math.round(middle)){
        //Basically if middle has a .5 in it, then average the numbers of both sides of it
        int low = (int)Math.floor(middle);
        int high = (int)Math.ceil(middle);
        return (sortedNumFollowersArray[low] + sortedNumFollowersArray[high]) / 2;

      }
      else{
        //Middle lands on a integer value and this must be our median
        return sortedNumFollowersArray[(int)middle];
      }
    }

    //Perfect - now can also return an array of how many followers each user can reach for graphic purposes
    static String bestPropagator(int[][] network, GraphData users){
      //Set to this so that we don't have an issue when comparing any reasonable name alphabetically with
        //bestAdvertiser as opposed to comparing a name with an empty string
      String bestAdvertiser = "ZZZZZZZZ";
      int maxReach = 0;

      for(int i=0;i<network.length;i++){
        //Using the breadth-first search algorithm to find out how many followers a user has
        int reach = BreadthFirst.BreadthFirstSearch(network, i).size();
        if(reach > maxReach ){
          bestAdvertiser = users.getUsersArray()[i];
          maxReach = reach;
        }
        else if(reach == maxReach && bestAdvertiser.compareToIgnoreCase(users.getUsersArray()[i]) > 0){
          bestAdvertiser = users.getUsersArray()[i];
        }
      }

      return bestAdvertiser;
    }
}
