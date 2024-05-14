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
        findTwoDegrees(users);

        //This function will complete task 5
        System.out.println("This network has a median of " + String.format("%s", medianFollowers(followerInfo) + " followers."));
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
    static void findTwoDegrees(GraphData users){
      //This integer stores the number of users found at 2 degrees of separation
      int twoDegrees = 0;
      int OGUserIndex = 0;

      //finding the index of the original OG user at the start of the file
      for(int i=0;i<users.getUsersArray().length;i++){
        if(users.getUsersArray()[i] == users.getOGuser()){
          OGUserIndex = i;
          break;
        }
      }

      String[] followers = users.getUserConnections()[OGUserIndex].split(" ", -2);
      for(int i=1;i<followers.length;i++){

        int pFollowIndex = 0;
        //finding the index of the original OG user at the start of the file
        for(int j=0;j<users.getUsersArray().length;j++){

          if(users.getUsersArray()[j].equals(followers[i])){
            pFollowIndex = j;
            break;
          }
        }

        //These followers do not directly follow OgUser, therefore are 2nd degree followers
        String secondDegFollowers = users.getUserConnections()[pFollowIndex];
        
        
        if(!secondDegFollowers.contains(users.getOGuser())) {
          //Must also check to make sure that OGUser doesn't follow them either
          String followersString = users.getUserConnections()[OGUserIndex];
          if(!followersString.contains(users.getUsersArray()[pFollowIndex])){
            twoDegrees++;
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
}
