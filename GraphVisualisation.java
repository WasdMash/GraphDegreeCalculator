import java.awt.*;
import java.awt.Panel;
import javax.swing.*;

public class GraphVisualisation {
    //Exoerimentation
    //Graphics.drawline(x1,y1,x2,y2) essentially draws a line from (x1,y1) to (x2,y2)

    JFrame CreateWindow(){
        JFrame frame = new JFrame("Graph visualisation");
        //Creating a frame which is 800x600 in the x-y plane
        frame.setSize(800, 600);
        frame.setLocation(0,0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //This Panel is the panel class which I created for this
        frame.setContentPane(new Panel());
        frame.setVisible(true);


        return frame;
    }
}
