package csc741m_mp2;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Laurence Foz
 */
public class Scene {
    private ArrayList<ImageGS> frames;
    
    public Scene(){
        frames = new ArrayList<>();
    }
    
    public Scene(ArrayList<ImageGS> go){
        frames = go;
    }
    
    public void setFrames(ArrayList<ImageGS> in){
        frames = in;
    }
    
    public ArrayList<ImageGS> getFrames(){
        return frames;
    }
}
