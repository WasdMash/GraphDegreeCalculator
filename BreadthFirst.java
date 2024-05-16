import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirst {

    //StartuserNode is the user from which we shall test and find out how many users
        //we can advertise to through them
    
    //Returns all of the visited nodes, can then be compared in the main script
    public static List<Integer> BreadthFirstSearch(int[][] network, int startUserNode){
        Queue<Integer> queue = new LinkedList<Integer>();
        List<Integer> visited = new ArrayList<Integer>();
        //Pushing the starting node onto the visited list
        queue.add(startUserNode);
        //Marking our starting user node as visited - after all, they are the first one we visited technically
        visited.add(startUserNode);

        //While there are still yet users whose followers we must find
        while(!queue.isEmpty()){
            //Returns the user at the front of the queue - this is the current user whose followers we are looking for
            //Apparently, .poll() will return null (basically 0) if a head isn't found
            //If not dealt with properly, this could be an issue as at index 0, is the most alphabetical, not nothing
            int currentUser = queue.poll();
            //Find everyone who follows that original user
            //Search through the array, silly
            for(int i=0;i<network.length;i++){
                //Adds another unique target of advertising if their node hasn't already been found
                if(network[currentUser][i]==1){
                    //Manually checking if the visited list contains i
                    boolean alreadyVisited = false;
                    for (int follower : visited) {
                        if(follower == i) alreadyVisited = true;
                    }
                    if(!alreadyVisited){
                        //Found another follower of P, so let's also check their followers too
                        visited.add(i);
                        queue.add(i);
                    } 
                }

            }
        }

        return visited;
        //Could also use this for visualisation for that cheeky A*

    }
    
}
