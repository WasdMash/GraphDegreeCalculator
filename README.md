This project takes a text file of a list of names per line, with the first name being the person to focus on and the consequent names being the people they follow.
This program will then, take this information to construct a graph and detail all sorts of information about the connections between these people, for instance, the minimum degree of separation between two people on this network.

You can run this program by invoking at the command line in a terminal:
java Analysis \<inputFile\>

For the input file, it's basically a directory, meaning that if you use only the name of the file, then it must be within the same folder as where you are running the code from, otherwise, include the folder names which house it beforehand

e.g 

MainDirectory
    --> test-socialnetworks
        --> social-network1.txt

can be read using the command Java Analysis test-socialnetworks/social-network1.txt

I've also attached the test files from the Moodle page which I have used to test the code
