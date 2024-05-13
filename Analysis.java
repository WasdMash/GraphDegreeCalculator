class FollowerData{
  int[] numberOfFollowers;
  String mostFollowed = "";
  FollowerData(int[] numFollowers, String mostPopular){
    numberOfFollowers = numFollowers;
    mostFollowed = mostPopular;
  }
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
        String mostFollowers = findMostFollowers(users, adjacencyMatrix);
        System.out.println("The user who has the most followers is: " + mostFollowers);
      } 

    static String findMostFollowers(GraphData users, int[][] adjacencyMatrix){
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

      return users.getUsersArray()[maxFollowIndex];
    }
}
