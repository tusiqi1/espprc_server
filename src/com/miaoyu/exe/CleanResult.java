package com.miaoyu.exe;
import com.miaoyu.algorithm.ColumnGeneration;
import com.miaoyu.algorithm.ColumnGeneration_RC;
import com.miaoyu.helper.Data;
import static com.miaoyu.helper.Functions.*;

import java.util.*;
import java.io.*;


public class CleanResult {

    public static void main(String[] args){
        summary();
        summary_multi();
        summary("Pulse-multi");
        summary("Pulse-multi5");
        summary("Pulse-multi10");
        summary("Pulse-multi15");

    }

    public static void summary(){
        {
            File file = new File("Data/UCVRP6/RandCol/solomon_100/");
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
            writeDataCSV("Data/UCVRP6/RandCol/solomon_100/","summary1", summary);
        }
    }

    public static void summary_multi(){
        File file = new File("Data/UCVRP6/RandCol-multi/solomon_100/");
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
        writeDataCSV("Data/UCVRP6/RandCol-multi/solomon_100/","summary1", summary);
    }

    public static void summary(String type){
        File file = new File("Data/UCVRP6/"+type+"/solomon_100/");
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
        writeDataCSV("Data/UCVRP6/"+type+"/solomon_100/","summary1", summary);
    }
}
