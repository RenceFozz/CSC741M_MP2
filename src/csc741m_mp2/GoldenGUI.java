package csc741m_mp2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Laurence Foz
 */
public class GoldenGUI extends JFrame implements ItemListener{
    private final JPanel p_main, p_video;
    private final JTextArea ta_log;
    private JComboBox cb_method, cb_option;
    private JScrollPane jsp_1;
    private JMenu file;
    private JMenuItem open;
    private String path;
    private JFileChooser jfc;
    private File baseFile;
    private ArrayList<File> uni, _777, MJ;
    private Integer currOpt;
    private String[] dir;
    
    public GoldenGUI(){
        super("Video Segmentation System");
        
        ta_log = new JTextArea(10,15);
        
        currOpt = 0;
        
        dir = new String[]{"uni","777","mjack"};
        cb_option = new JComboBox(dir);
        cb_option.addItemListener(this);
        cb_option.setName("cb_option");
        cb_option.setFont(new Font("Calibri", Font.PLAIN, 14));
        
        p_main = new JPanel();
        p_video = new JPanel();
        jsp_1 = new JScrollPane(p_video, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp_1.setPreferredSize(new Dimension(100, 300));
        p_main.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        //First Line
	c.gridy = 0;
        
        c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.5;
	c.gridx = 0;
        c.gridwidth = 2;
	c.insets = new Insets(10,10,10,10);
	this.p_main.add(jsp_1, c);
        
        //Second Line
	c.gridy = 1;
        c.gridwidth = 1;
        
        c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.5;
	c.gridx = 0;
	c.insets = new Insets(10,10,10,10);
	this.p_main.add(cb_option, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.5;
	c.gridx = 1;
	c.insets = new Insets(10,10,10,10);
	this.p_main.add(ta_log, c);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setPreferredSize(new Dimension(800,600));
	pack();
	setLayout(new BorderLayout());
	setLocationRelativeTo(null);
        setResizable(false);
	add(p_main, BorderLayout.CENTER);
	setVisible(true);
        
        init();
        ta_log.append("Video Segmentation System Online!\n");
    }
    
    public final void init(){
        ta_log.append("Initializing systems...\n");
        for(int i=0;i<dir.length;i++){
            System.out.println(i);
            path = System.getProperty("user.dir")+File.separatorChar+"images"+File.separatorChar+dir[i]+File.separatorChar;
            File file = new File(path);
            ArrayList<File> folder = new ArrayList<>(Arrays.asList(file.listFiles()));

            for (int j=0;j<folder.size();j++) {
                if(!(folder.get(j).getName().endsWith(".jpg") || folder.get(j).getName().endsWith(".jpeg")) ) {
                    folder.remove(j);
                }
            }
            /*for (int i=0;i<folder.size();i++) {
                System.out.println( folder.get(i).getName() );
            }*/
            switch(i){
                case 0: uni     = folder; break;
                case 1: _777    = folder; break;
                case 2: MJ      = folder; break;
                default: System.err.println("That's not supposed to happen");
            }
            System.out.println("Files in images Folder: "+folder.size());
        }
        rgbToGS(uni.get(0));
    }
    
    public void rgbToGS(File filename){
        BufferedImage bi1, bi2;
        int RGB1, i, j, A, R, G, B, newVal;
        i = j= 0;
        //double ;
        Color c;
        ImageGS test = new ImageGS();
        
        try{
            bi1 = ImageIO.read(filename);
            
            int imHeight = bi1.getHeight();
            int imWidth = bi1.getWidth();
            
            bi2 = new BufferedImage(imWidth, imHeight, 1);
            
            for(i=0; i<imWidth; i++){
                for(j=0; j<imHeight;j++){
                    RGB1 = bi1.getRGB(i,j);
                    c = new Color(RGB1);
                    A = getAlpha(RGB1);
                    R = c.getRed();
                    G = c.getGreen();
                    B = c.getBlue();
                    
                    newVal = (R + G + B) / 3; // grey by averaging the pixels
                    test.addPixel(newVal);
                    test.addAlpha(A);
                    R = G = B = newVal;
                    newVal = createRGB(A, R, G, B);
                    bi2.setRGB(i, j, newVal);
                }
            }
            
            JLabel picLabel = new JLabel(new ImageIcon(bi2));
            p_video.add(picLabel);
            test.viewAlpha();
        } catch (Exception ex) {
            ta_log.append("Image does not exist - RGB : " + i + ", " + j + "\n");
        }
    }
    
    public static int createRGB(int alpha, int r, int g, int b) {
        int rgb = (alpha << 24) + (r << 16) + (g << 8) + b;
        return rgb;
    }
    
    public static int getAlpha(int rgb) {
        return (rgb >> 24) & 0xFF;
        // return rgb & 0xFF000000;
    }
    
    /*private JMenuBar setMenuBar(){
        JMenuBar mb = new JMenuBar();
        
        file = new JMenu("File");
        open = new JMenuItem("Open");
        
        open.addActionListener((ActionEvent e) -> {
            
        });
        
        open.registerKeyboardAction((ActionEvent e) -> {open.doClick();},
                KeyStroke.getKeyStroke('O', Event.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        file.add(open);
        mb.add(file);
        
        return mb;
    }
    
    private void openFile() {
        jfc = new JFileChooser(path);
        FileFilter filter = new FileNameExtensionFilter("JPG/JPEG", "jpg", "jpeg");
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.addChoosableFileFilter(filter);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = jfc.showDialog(this, "Select JPG/JPEG image");
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            ta_log.setText("");
            baseFile = jfc.getSelectedFile();
            ta_log.append("Base Image Selected\n");
            tf_main.setText(baseFile.getName());
        } else {
            ta_log.append("Open command cancelled by user\n");
        }
    }*/
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        JComboBox source = (JComboBox) e.getSource();
        switch(source.getName()) {
            case "cb_method":
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    //System.out.println(method);
                    ta_log.setText("Method Combo Box changed");
                }
            break;
            case "cb_option":
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    currOpt = cb_option.getSelectedIndex();
                    System.out.println(currOpt);
                    ta_log.setText("Option Combo Box changed");
                }
            break;
            default: System.out.println("The ComboBox does not exist");
        }
    }
}