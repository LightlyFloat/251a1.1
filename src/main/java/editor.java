import javax.swing.*; //Import swing and AWT packages to implement GUI
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class editor {
    public static void main(String[] args) {
        windowEditor textEditor = new windowEditor();
    }
}

class windowEditor extends JFrame implements ActionListener {
    JTextArea text1; //Initialize text area
    JMenuBar menuBar; //menu bar
    JMenu menu1, menu2, menu3, menu4; //Four buttons in the menu bar (drop-down menu)
    JMenuItem itemNew, itemNewWindow, itemSave, itemSaveAs, itemPrint, itemCut, itemCopy, itemPaste, itemFind, itemReplace,
            itemOpen, itemFontFormat, itemAbout, mouseCut, mouseCopy, mousePaste, mouseDelete, mouseSelectAll; //Initialization function key
    JScrollPane scrollPane; //scroll bar
    JPopupMenu mouseMenu;
    JFileChooser fileChooser = new JFileChooser();

    windowEditor() {
        setTitle("Text Editor"); //Sets the title for this window
        setSize(640, 360); //Sets the width and height for this window
        setLocation(320, 180); //Sets the location of this window
        setVisible(true); //Set window visible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Set to close the process when the exit key is clicked

        menuBar = new JMenuBar(); //Instantiate menu bar

        menu1 = new JMenu("File"); //Instantiate the four buttons of the menu bar (drop-down menu)
        menu2 = new JMenu("Edit");
        menu3 = new JMenu("Format");
        menu4 = new JMenu("Help");

        //File
        itemNew = new JMenuItem("New"); //Instantiate button
        itemNewWindow = new JMenuItem("New window");
        itemOpen = new JMenuItem("Open");
        itemSave = new JMenuItem("Save");
        itemSaveAs = new JMenuItem("Save as");
        itemPrint = new JMenuItem("Print");
        //Edit
        itemCut = new JMenuItem("Cut");
        itemCopy = new JMenuItem("Copy");
        itemPaste = new JMenuItem("Paste");
        itemFind = new JMenuItem("Find");
        itemReplace = new JMenuItem("Replace");
        //Format
        itemFontFormat = new JMenuItem("Font format");
        //Help
        itemAbout = new JMenuItem("About");

        menu1.add(itemNew); //add buttons to menu.
        menu1.add(itemNewWindow);
        menu1.add(itemOpen);
        menu1.add(itemSave);
        menu1.add(itemSaveAs);
        menu1.add(itemPrint);
        menu2.add(itemCut);
        menu2.add(itemCopy);
        menu2.add(itemPaste);
        menu2.add(itemFind);
        menu2.add(itemReplace);
        menu3.add(itemFontFormat);
        menu4.add(itemAbout);

        menuBar.add(menu1); //add menu to menuBar
        menuBar.add(menu2);
        menuBar.add(menu3);
        menuBar.add(menu4);

        setJMenuBar(menuBar); //Sets the menuBar for this window
        validate(); //Validates this container and all of its subcomponents.

        //Add ActionListeners to buttons
        itemNew.addActionListener(this);
        itemNewWindow.addActionListener(this);
        itemSave.addActionListener(this);
        itemSaveAs.addActionListener(this);
        itemPrint.addActionListener(this);
        itemOpen.addActionListener(this);
        itemCut.addActionListener(this);
        itemCopy.addActionListener(this);
        itemPaste.addActionListener(this);
        itemFind.addActionListener(this);
        itemReplace.addActionListener(this);
        itemFontFormat.addActionListener(this);
        itemAbout.addActionListener(this);

        text1 = new JTextArea();
        text1.setLineWrap(true); //Set word wrap

        scrollPane = new JScrollPane(text1); //Add a new scroll bar in text1 text area
        add(scrollPane); //Appends the scrollPane to the end of this container.
        validate();

        //Right click menu settings
        mouseMenu = new JPopupMenu();
        mouseCut = new JMenuItem("Cut");
        mouseCopy = new JMenuItem("Copy");
        mousePaste = new JMenuItem("Paste");
        mouseDelete = new JMenuItem("Delete");
        mouseSelectAll = new JMenuItem("Select all");

        //set mouse listener
        text1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK)
                    mouseMenu.show(text1, e.getX(), e.getY()); //Set to display the right-click menu where the right mouse button is clicked
            }
        });

        //Add ActionListeners to buttons
        mouseCut.addActionListener(this);
        mouseCopy.addActionListener(this);
        mousePaste.addActionListener(this);
        mouseDelete.addActionListener(this);
        mouseSelectAll.addActionListener(this);

        mouseMenu.add(mouseCut);
        mouseMenu.add(mouseCopy);
        mouseMenu.add(mousePaste);
        mouseMenu.add(mouseDelete);
        mouseMenu.add(mouseSelectAll);

        validate();
    }

    public void actionPerformed(ActionEvent e) //Sets the behavior when the button is pressed
    {
        //New button implement
        if (e.getSource() == itemNew) {
            text1.replaceRange("", 0, text1.getText().length()); //Replacing all text with an empty string， which has the same effect as emptying the text area
        }
        //New Window button implement
        else if (e.getSource() == itemNewWindow) {
            windowEditor NewWindow = new windowEditor();
        }
        //Open button implement
        else if (e.getSource() == itemOpen) {
            FileDialog openFile = new FileDialog(this, "Form..", FileDialog.LOAD);  //Create a new window to select the file path
            openFile.setVisible(true);
            openFile.setLocation(800, 400); //Set the location of the FileDialog window
            String filePath = openFile.getDirectory() + openFile.getFile(); //Get file path
            try {
                FileInputStream inputStream = new FileInputStream(filePath);
                byte[] content = new byte[inputStream.available()]; //The array is established by the length of the file input stream
                inputStream.read(content);
                text1.setText(new String(content));
                text1.setCaretPosition(0); //Set the starting position of the text
                if (openFile.getFile() != null) {
                    this.setTitle(openFile.getFile()); //Set the window name to file name
                }
                inputStream.close(); //Close file input stream
            } catch (Exception ex) {
                ex.printStackTrace(); //print Location and causes of errors in command line
            }
        }
        //Save button implement
        else if (e.getSource() == itemSave) {
            int i = fileChooser.showSaveDialog(windowEditor.this); //Display The window for saving the file, and the return value is:APPROVE_OPTION 0 CANCEL_OPTION ERROR_OPTION -1
            if (i == JFileChooser.APPROVE_OPTION) //Determine whether the window was successfully created
            {
                File selectedFile = fileChooser.getSelectedFile();//Create a file using the path selected in the previous window
                try {
                    FileOutputStream out = new FileOutputStream(selectedFile);
                    out.write(text1.getText().getBytes());//Write the text in textarea to file
                } catch (Exception ex) {
                    ex.printStackTrace(); //print Location and causes of errors in command line
                }
            }
        }
        //Save as button implement
        else if (e.getSource() == itemSaveAs) {
            int i = fileChooser.showSaveDialog(windowEditor.this); //Display The window for saving the file, and the return value is:APPROVE_OPTION 0 CANCEL_OPTION ERROR_OPTION -1
            if (i == JFileChooser.APPROVE_OPTION) //Determine whether the window was successfully created
            {
                File selectedFile = fileChooser.getSelectedFile(); //Create a file using the path selected in the previous window
                try {
                    FileOutputStream out = new FileOutputStream(selectedFile);
                    out.write(text1.getText().getBytes()); //Writing text from textarea to file
                } catch (Exception ex) {
                    ex.printStackTrace(); //print Location and causes of errors in command line
                }
            }
        }
        //Print button implement
        else if (e.getSource() == itemPrint) {
            new functionPrint();
        }
        //Copy button implement
        else if (e.getSource() == itemCopy || e.getSource() == mouseCopy) {
            text1.copy();//Transfer the currently selected range in the associated text model to the system clipboard without changing the template
        }
        //Cut button implement
        else if (e.getSource() == itemCut || e.getSource() == mouseCut) {
            text1.cut(); //Transfer the currently selected range in the associated text model to the system clipboard and delete the selected text in the template
        }
        //Paste button implement
        else if (e.getSource() == itemPaste || e.getSource() == mousePaste) {
            text1.paste(); //Transfers the contents of the system clipboard to the associated text model
        }
        //Delete button implement
        else if (e.getSource() == mouseDelete) {
            text1.replaceRange("", 0, text1.getText().length());
        }
        //Select all button implement
        else if (e.getSource() == mouseSelectAll) {
            text1.selectAll(); //Select all text in textarea
        }
        //Find button implement
        else if (e.getSource() == itemFind) {
            new functionFind();
        }
        //Replace button implement
        else if (e.getSource() == itemReplace) {
            new functionReplace();
        }
        //Font format button implement
        else if (e.getSource() == itemFontFormat) {
            new functionFontFormat();
        }
        //About button implement
        else if (e.getSource() == itemAbout) {
            JOptionPane.showMessageDialog(windowEditor.this, "159251 assignment1 write by Zhuang Yan and Zhang QingYang"); //Brings up an information-message dialog titled "Message".
        }
    }
    class functionFind extends JFrame{}
    class functionPrint extends JFrame{}
    class functionReplace extends JFrame{}
    class functionFontFormat extends JFrame{}
}

