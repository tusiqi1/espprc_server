package com.miaoyu.exe;
import com.miaoyu.algorithm.ColumnGeneration;
import com.miaoyu.algorithm.ColumnGeneration_RC;
import com.miaoyu.helper.Data;
import static com.miaoyu.helper.Functions.*;

import java.util.*;
import java.io.*;


public class CleanResult {

    public static void main(String[] args){
//        String[] types = {"Pulse", "Pulse-multi", "RandCol", "RandCol-multi"};
//        for(int i = 4; i < 8; i++){
//            for(String t : types){
//                summary(i,t);
//            }
//        }
        combine(7);
    }




    public static void summary(int l, String type){
        File file = new File("Data/UCVRP"+l+"/"+type+"/solomon_100/");
        System.out.println(Arrays.toString(file.listFiles()));
        ArrayList<String> summary = new ArrayList<>();
        for(File f : file.listFiles()) {
            if(!f.toString().endsWith(".csv"))
                continue;
            System.out.println(f.toString());

            ArrayList<String[]> temp = readDataCSV(f.toString());
            System.out.println(temp.size());
            String output="";

            for(int i = 0; i < temp.size(); i++){
                output = output + temp.get(i)[0] + ",";
            }
            System.out.println("!"+output);
            summary.add(output);

        }
        writeDataCSV("Data/UCVRP"+l+"/"+type+"/solomon_100/","summary1", summary);
    }

    public static void combine(int l){
        String[] types = {"Pulse", "Pulse-multi", "RandCol", "RandCol-multi"};
        ArrayList<String> summary = new ArrayList<>();

        for(String t : types){
            File file = new File("Data/UCVRP"+l+"/"+t+"/solomon_100/");
            System.out.println(Arrays.toString(file.listFiles()));
            for(File f : file.listFiles()) {
                if(!f.toString().endsWith("summary1.csv"))
                    continue;
                System.out.println(f.toString());

                ArrayList<String[]> temp = readDataCSV(f.toString());
                System.out.println(temp.size());

                for(int i = 0; i < temp.size(); i++){
                    String output="";
                    for(int j = 0 ; j < temp.get(i).length; j++)
                        output = output + temp.get(i)[j] + ",";
                    summary.add(output);
                }
                //System.out.println("!"+output);
                summary.add("");

            }
        }
        writeDataCSV("Data/UCVRP"+l+"/","summary1", summary);

    }

}
