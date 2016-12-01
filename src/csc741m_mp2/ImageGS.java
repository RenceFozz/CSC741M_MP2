package csc741m_mp2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Laurence Foz
 */
public class ImageGS {
    private ArrayList<Integer> gs_pixels;
    private ArrayList<Integer> alpha_pixels;
    private Integer[] quantity;
    
    public ImageGS(){
        gs_pixels = new ArrayList<>();
        alpha_pixels = new ArrayList<>();
        quantity = new Integer[256];
        Arrays.fill(quantity,0);
    }
    
    public ArrayList<Integer> getGSPixels(){
        return gs_pixels;
    }
    
    public ArrayList<Integer> getAlphaPixels(){
        return alpha_pixels;
    }
    
    public void addPixel(int i){
        gs_pixels.add(i);
        quantity[i]++;
    }
    
    public void addAlpha(int i){
        if(!alpha_pixels.contains(i)){
            alpha_pixels.add(i);
        }
    }
    
    public void viewPixels(){
        for(int i=0;i<gs_pixels.size();i++){
            System.out.println(gs_pixels.get(i));
        }
    }
    
    public void viewAlpha(){
        for(int i=0;i<alpha_pixels.size();i++){
            System.out.println(alpha_pixels.get(i));
        }
    }
    
    public void viewQuantities(){
        for(int i=0;i<quantity.length;i++){
            System.out.println("Quantity of "+ i + ": " +quantity[i]);
        }
    }
    
}
