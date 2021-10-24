import javax.swing.*; //Import swing and AWT packages to implement GUI
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    JTextArea textArea ;
    JFrame f;
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
            new functionFind(f,textArea);
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
    class functionFind extends JFrame{
        private JFrame mainFrame;
        private JLabel headerLabel;
        private JLabel statusLabel;
        private JPanel controlPanel;

        //public static void main
        public functionFind(JFrame f,JTextArea textArea) {
            JDialog jdlg = new JDialog(f, "查找", true);
            jdlg.setSize(453, 150);
            jdlg.setLocationRelativeTo(null);
            jdlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            JPanel panel=new JPanel();
            panel.setLayout(null);
            JLabel label=new JLabel("查找内容：");
            JLabel count=new JLabel("找到0个");
            JTextArea ftext=new JTextArea();
            JButton findnext=new JButton("查找下一个");
            JButton no=new JButton("取消");
            JCheckBox matchcase=new JCheckBox("区分大小写");
            //创建两个单选按钮
            JRadioButton up = new JRadioButton("向上查找");
            JRadioButton down = new JRadioButton("向下查找");
            //创建按钮组，把两个单选按钮添加到该组
            ButtonGroup btnGroup = new ButtonGroup();
            btnGroup.add(up);
            btnGroup.add(down);
            // 设置第一个单选按钮选中
            up.setSelected(true);

            label.setBounds(20,20,93,22);
            count.setBounds(20, 55, 80, 35);
            ftext.setBounds(100,20,200,22);
            findnext.setBounds(320, 18, 105, 32);
            no.setBounds(320, 53, 105, 32);
            matchcase.setBounds(15, 90, 100, 25);
            up.setBounds(130, 90, 80, 25);
            down.setBounds(230, 90, 80, 25);

            //初始化查找按键
            findnext.setEnabled(false);
            //设置文本框和按钮的状态
            ftext.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(ftext.getText().equals(""))
                        findnext.setEnabled(false);
                }
                @Override
                public void insertUpdate(DocumentEvent e) {
                    findnext.setEnabled(true);
                }
                @Override
                public void changedUpdate(DocumentEvent e) {

                }
            });
            //查找下一个的监听器
            findnext.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int c=0;
                    int a = 0, b = 0;
                    int FindStartPos = textArea.getCaretPosition();
                    String strA,strB;
                    // 选中区分大小写,大小写转换

                    if (matchcase.isSelected()) {
                        strA = textArea.getText();
                        strB = ftext.getText();
                    }
                    else {
                        strA = textArea.getText().toLowerCase();
                        strB = ftext.getText().toLowerCase();
                    }
                    //向上查找，否则向下查找
                    if (up.isSelected()) {
                        a = strA.lastIndexOf(strB, FindStartPos - ftext.getText().length() - 1);
                    }
                    else if (down.isSelected()) {
                        a = strA.indexOf(strB, FindStartPos - ftext.getText().length() + 1);
                    }
                    //查找到边界
                    if (a > -1) {
                        if (up.isSelected()) {
                            textArea.setCaretPosition(a);
                            b = ftext.getText().length();
                            textArea.select(a, a + b);
                        } else if (down.isSelected()) {
                            textArea.setCaretPosition(a);
                            b = ftext.getText().length();
                            textArea.select(a, a + b);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "找不到查找的内容",
                                "查找", JOptionPane.INFORMATION_MESSAGE);
                    }
                    //显示关键字的总数量
                    Pattern p=Pattern.compile(ftext.getText());
                    Matcher m=p.matcher(textArea.getText());
                    while(m.find()) {
                        c++;
                    }
                    count.setText("找到"+c+"个");
                }
            });
            //取消的监听器
            no.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jdlg.setVisible(false);
                }
            });

            label.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
            count.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
            ftext.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
            findnext.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
            no.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));

            panel.add(label);
            panel.add(count);
            panel.add(ftext);
            panel.add(findnext);
            panel.add(no);
            panel.add(matchcase);
            panel.add(up);
            panel.add(down);

            jdlg.setResizable(false);
            jdlg.setContentPane(panel);
            jdlg.setVisible(true);
        }
    }
    class functionPrint extends JFrame{}
    class functionReplace extends JFrame{}
    class functionFontFormat extends JFrame{}
}

