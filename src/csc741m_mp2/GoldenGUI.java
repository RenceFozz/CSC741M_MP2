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
    private final JComboBox cb_option, cb_thresh;
    private final JLabel lbl_imgPane, lbl_method, lbl_log, lbl_thresh;
    private final JScrollPane jsp_1, jsp_2;
    private JMenu file;
    private JMenuItem run;
    private String path;
    private JFileChooser jfc;
    private ArrayList<File> uni, _777, MJ;  //Image Databases
    private ArrayList<ImageGS> imageFrame;  //Contains the Grayscale Images
    private ArrayList<Integer> SD;    //SD between frames
    private Integer currOpt, thresh;
    private final String[] dir;
    
    public GoldenGUI(){
        super("FinVidSeg Video Segmentation System");
        super.setJMenuBar(setMenuBar());
        
        //TextArea
        ta_log = new JTextArea(8,15);
        ta_log.setEditable(false);
        ta_log.setWrapStyleWord(true);
        ta_log.setLineWrap(true);
        
        //JLabels
        lbl_imgPane = new JLabel("IMAGE");
        lbl_method = new JLabel("DATABASE");
        lbl_thresh = new JLabel("THRESHOLD");
        lbl_log = new JLabel("LOG");
        
        lbl_imgPane.setFont(new Font("Calibri", Font.PLAIN, 14));
        lbl_method.setFont(new Font("Calibri", Font.PLAIN, 14));
        lbl_thresh.setFont(new Font("Calibri", Font.PLAIN, 14));
        lbl_log.setFont(new Font("Calibri", Font.PLAIN, 14));
        
        //JComboBox #1
        dir = new String[]{"uni","777","mjack"};    //Image Database Combo Box
        cb_option = new JComboBox(dir);
        cb_option.addItemListener(this);
        cb_option.setName("cb_option");
        cb_option.setFont(new Font("Calibri", Font.PLAIN, 14));
        
        //JComboBox #2
        cb_thresh = new JComboBox(new String[]{"4000","4500","5000"});  //Threshold ComboBox
        cb_thresh.addItemListener(this);
        cb_thresh.setName("cb_thresh");
        cb_thresh.setFont(new Font("Calibri", Font.PLAIN, 14));
        
        //Variable Declaration
        //imageFrame = new ArrayList<>();
        //SD = new ArrayList<>();
        currOpt = 0;
        thresh = Integer.valueOf(cb_thresh.getSelectedItem().toString());
        
        //Panels
        p_main = new JPanel();
        p_video = new JPanel();
        jsp_1 = new JScrollPane(p_video, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp_2 = new JScrollPane(ta_log, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp_1.setPreferredSize(new Dimension(250, 300));
        jsp_2.setPreferredSize(new Dimension(200, 130));
        p_main.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.5;
        
        //First Line
	c.gridy = 0;
	c.insets = new Insets(5,10,0,10);
        
	c.gridx = 0;
	this.p_main.add(lbl_imgPane, c);
        
	c.gridx = 1;
	this.p_main.add(lbl_method, c);
        
	c.gridx = 2;
	this.p_main.add(lbl_thresh, c);
        
        //Second Line
        c.gridy = 1;
	c.insets = new Insets(10,10,10,10);
        
	c.gridx = 0;
        c.gridheight = 3;
	this.p_main.add(jsp_1, c);
        
	c.gridx = 1;
        c.gridheight = 1;
	this.p_main.add(cb_option, c);
        
        c.gridx = 2;
	this.p_main.add(cb_thresh, c);
        
        //Third Line
	c.gridy = 2;
	c.insets = new Insets(10,10,10,10);
        
	c.gridx = 1;
	this.p_main.add(lbl_log, c);
        
        //Fourth Line
	c.gridy = 3;
        
	c.gridx = 1;
        //c.gridheight = 1;
	c.gridwidth = 2;
	c.insets = new Insets(10,10,100,10);
	this.p_main.add(jsp_2, c);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setPreferredSize(new Dimension(600,400));
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
        ta_log.append("Initializing system...\n");
        for(int i=0;i<dir.length;i++){
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
            //System.out.println("Files in images Folder: "+folder.size());
        }
        //Integer[] toArrL = new Integer[] {5,5,19,5,8};
        //ArrayList<Integer> intArray = new ArrayList<>(Arrays.asList(toArrL));
        //getSTDev(intArray);
    }
    
    private void run(){
        ta_log.append("Running...\n");
        ArrayList<File> temp = null;
        imageFrame = new ArrayList<>();
        SD = new ArrayList<>();
        int SDi, size;
        
        ImageGS x, y, last;
        
        switch(currOpt){
            case 0: temp = uni; break;
            case 1: temp = _777; break;
            case 2: temp = MJ; break;
        }
        
        size = temp.size();
        
        for(int i=0; i<(temp.size()-1); i++){
            SDi = 0;
            x = rgbToGS(temp.get(i));
            y = rgbToGS(temp.get(i+1));
            for(int j=0; j<64; j++){
                int a = x.getQuantity(j);
                int b = y.getQuantity(j);
                SDi += Math.abs(a-b);
            }
            imageFrame.add(x);
            SD.add(SDi);
            /*if(SDi>thresh){ //threshold
                System.out.println("SD of "+temp.get(i).getName()+" & "+temp.get(i+1).getName()+" = " + SDi);
            }*/
        }
        last = rgbToGS(temp.get(size-1));
        imageFrame.add(last);
        ta_log.append("Grayscale images calculated and stored...\n");
        //System.out.println("Temp: "         +temp.size());
        //System.out.println("Image Frame: "  +imageFrame.size());
        //System.out.println("SD: "           +SD.size());
        ta_log.append("Obtained SDi\n");
        GT();
    }
    
    public ImageGS rgbToGS(File filename){
        BufferedImage bi1, bi2;
        int RGB1, i, j, A, R, G, B, newVal;
        i = j= 0;
        Color c;
        ImageGS test = new ImageGS();
        
        try{
            bi1 = ImageIO.read(filename);
            
            int imHeight = bi1.getHeight();
            int imWidth = bi1.getWidth();
            
            //bi2 = new BufferedImage(imWidth, imHeight, 1);
            
            for(i=0; i<imWidth; i++){
                for(j=0; j<imHeight;j++){
                    RGB1 = bi1.getRGB(i,j);
                    c = new Color(RGB1);
                    A = (RGB1 >> 24) & 0xFF;
                    R = c.getRed();
                    G = c.getGreen();
                    B = c.getBlue();
                    
                    newVal = (R + G + B) / 3; // grey by averaging the pixels
                    test.addPixel(newVal);
                    test.addAlpha(A);
                    //R = G = B = newVal;
                    //newVal = (A << 24) + (R << 16) + (G << 8) + B;
                    //bi2.setRGB(i, j, newVal);
                }
            }
            //JLabel picLabel = new JLabel(new ImageIcon(bi2));
            //p_video.add(picLabel);
            //test.viewPixels();
            //test.viewQuantities();
        } catch (Exception ex) {
            ta_log.append("Image does not exist - RGB : " + i + ", " + j + "\n");
        }
        return test;
    }
    
    public void GT(){
        ta_log.append("Searching for Transition Frames\n");
        //Find transitions and kill them all
        Double aver = getMean(SD);
        Double stdev = getSTDev(SD);
        ArrayList<Integer> temp1 = new ArrayList<>();
        Integer AC = 0;
        for(int i=0; i<SD.size(); i++){
            if(SD.get(i) > thresh){
                temp1.add(SD.get(i));
                AC += SD.get(i);
            }
        }
        Double Tb = aver + 5*stdev;
        System.out.println("Average: "+aver+"\nSt. Dev: "+stdev+"\nAC: "+AC+"\nTb: "+Tb);
    }
    
    public double getMean(ArrayList<Integer> total){
        double sum = 0.0;
        for(double a : total)
            sum += a;
        return sum/total.size();
    }
    
    public double getSTDev(ArrayList<Integer> total){
        double stdev = 0;
        int size = total.size();
        
        double mean = getMean(total);
        double temp = 0;
        for(double a :total)
            temp += (a-mean)*(a-mean);
        
        stdev = Math.sqrt(temp/size);
        //System.out.println(stdev);
        return stdev;
    }
    
    public void AveHisto(ArrayList<ImageGS> last){
        //Average Histogram Method then display to GUI
        Double ave;
        ArrayList<Double> overall = new ArrayList<>(); 
        for(int i=0; i<64; i++){
            ave = 0.0;
            for(int j=0; j<last.size(); j++){
                ave += last.get(j).getQuantity(i);
            }
            overall.add(ave/last.size());
        }
    }
    
    /*private void openFile() {
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
    
    private JMenuBar setMenuBar(){
        JMenuBar mb = new JMenuBar();
        
        file = new JMenu("File");
        run = new JMenuItem("Run");
        
        run.addActionListener((ActionEvent e) -> {
            run();
        });
        
        run.registerKeyboardAction((ActionEvent e) -> {run.doClick();},
                KeyStroke.getKeyStroke('R', Event.CTRL_MASK, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        file.add(run);
        mb.add(file);
        
        return mb;
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        JComboBox source = (JComboBox) e.getSource();
        switch(source.getName()) {
            case "cb_thresh":
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    thresh = Integer.valueOf(cb_thresh.getSelectedItem().toString());
                    System.out.println(thresh);
                }
            break;
            case "cb_option":
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    currOpt = cb_option.getSelectedIndex();
                    System.out.println(currOpt);
                }
            break;
            default: System.out.println("The ComboBox does not exist");
        }
    }
}
