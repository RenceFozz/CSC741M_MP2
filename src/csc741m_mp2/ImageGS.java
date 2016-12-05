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
        quantity = new Integer[64];
        Arrays.fill(quantity,0);
    }
    
    public ArrayList<Integer> getGSPixels(){
        return gs_pixels;
    }
    
    public ArrayList<Integer> getAlphaPixels(){
        return alpha_pixels;
    }
    
    public Integer getPixel(int i){
        return gs_pixels.get(i);
    }
    
    public Integer getQuantity(int i){
        return quantity[i];
    }
    
    public void addPixel(int i){
        gs_pixels.add(i);
        quantity[i/4]++;
    }
    
    public void addAlpha(int i){
        //if(!alpha_pixels.contains(i)){
            alpha_pixels.add(i);
        //}
    }
    
    public void viewPixels(){
        System.out.println(gs_pixels.size());
        for(int i=0;i<gs_pixels.size();i++){
            System.out.println("Pixel #"+(i+1)+":"+gs_pixels.get(i));
        }
    }
    
    public void viewAlpha(){
        for(int i=0;i<alpha_pixels.size();i++){
            System.out.println(alpha_pixels.get(i));
        }
    }
    
    public void viewQuantities(){
        Integer keeper = 0;
        for(int i=0;i<quantity.length;i++){
            System.out.println("Quantity of "+ i + ": " +quantity[i]);
            keeper += quantity[i];
        }
        System.out.println("Total of Quantity: "+keeper);
    }
    
}
