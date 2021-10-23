import javax.swing.*; //Import swing and AWT packages to implement GUI
import java.awt.event.*;



public class editor {
    public static void main(String[] args)
    {
        windowEditor textEditor=new windowEditor();
    }
}

class windowEditor extends JFrame implements ActionListener
{
    windowEditor()
    {

    }

    public void actionPerformed(ActionEvent e) //Sets the behavior when the button is pressed
    {

    }
}
