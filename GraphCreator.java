import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

//Extra class used to store and return two arrays from the same function
class GraphData{
    String[] usersArray;
    String[] userConnections;
    String OGfirstUser;
    boolean printFollows;

    GraphData(boolean printMostFollowing){
        printFollows = printMostFollowing;
    }
    
    //Setters / mutators
    public void setUsersArray(List<String> users){
        usersArray = users.toArray(new String[users.size()]);
    }
    
    public void setUserConnections(List<String> followers){
        userConnections = followers.toArray(new String[followers.size()]);
    }

    public void SetOGUser(String firstUser){
        OGfirstUser = firstUser;
    }

    void calculateDensity(int edges, int nodes){
        //Accomplishing task 1
          //This should give all of the edges excluding the ability for a user to follow themselves
          float density = (float) edges / (nodes * (nodes - 1));
          System.out.println("\nThe density of the graph is: " + String.format("%s", density));
    }

    //Getters / Accessors
    public String[] getUsersArray(){ return usersArray;}
    public String[] getUserConnections(){ return userConnections;}
    public String getOGuser(){return OGfirstUser;}
}

public class GraphCreator {

    int[][] createGraph(String fileName){
        GraphData users = collateAllUsers(fileName, true);
        //Creates the 2D array as soon as we determine how many users we have
        int[][] adjacencyMatrix =  new int[users.usersArray.length][users.usersArray.length];

        //This runs for each user who is to be followed
        for(int i=0; i<users.usersArray.length; i++){
            adjacencyMatrix[i] = new int[users.usersArray.length];
            //This runs for defining which users follow this individual
            for(int j=0; j<users.usersArray.length; j++){
                adjacencyMatrix[i][j] = 0;
                //The above is the default unless if we find that user j followers user i
                if(i != j){
                    if(users.getUserConnections()[j].contains(users.getUsersArray()[i])){
                        adjacencyMatrix[i][j] = 1;
                    }
                }
            }
        }
        return adjacencyMatrix;
    }

    //This function just returns all of the unique users in the array
    GraphData collateAllUsers(String fileName, boolean printMostFollowing){
        GraphData graphEdgeData = new GraphData(printMostFollowing);
        List<String> allUsers = new ArrayList<String>();
        List<String> allConnections = new ArrayList<String>();

        String followsTheMost = ""; //User who follows the most people
        int mostFollowers = 0;
        int edges = 0;


        try{
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);

            //First pass for accomplishing tasks 1 and 3
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                allConnections.add(data);
                //I've set the limit to be negative to allow for any size of followers
                String[] names = data.split(" ", -2); //pass into graphCreator

                //This is the current number of follower
                int numFollowers = 0;

                //This is the current number of follower
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

                //Code to determine who has the most followers

                numFollowers--; //Users aren't necessarily following themselves            
                if(numFollowers > mostFollowers){
                followsTheMost = names[0]; //This should be the most followed
                mostFollowers = numFollowers;
                }
                else if(numFollowers == mostFollowers){
                if(followsTheMost.compareTo(names[0]) > 0) followsTheMost = names[0];
                //Replace the previous one with the preceding alphabetical user
                }
            }

            //Closing the file and ensuring that all user lists are sorted alphabetically
            myReader.close();
            //This task in particular is used for task 4 which requires that I can find the OG first user
            //This is instead of alphabetically ordering the list myself to make things pretty
            graphEdgeData.SetOGUser(allUsers.get(0));
            Collections.sort(allUsers);
            Collections.sort(allConnections);

            //These variables are then used to calculate the density of the graph
            if(graphEdgeData.printFollows){
                edges -= allUsers.size();
                graphEdgeData.calculateDensity(edges, allUsers.size());
                System.out.println("The user who follows the most people is " + followsTheMost); 
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        graphEdgeData.setUsersArray(allUsers);
        graphEdgeData.setUserConnections(allConnections);
        return graphEdgeData;
    }
}
