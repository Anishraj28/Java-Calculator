package Tests;

import javax.swing.JFrame;

public class calculator_frame {
	public static void main(String args[]){
        JFrame frame =new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.add(new calculator());
	    frame.setSize(250,300);
	    frame.setLocation(200,200);
	    frame.setVisible(true);
    }

}
