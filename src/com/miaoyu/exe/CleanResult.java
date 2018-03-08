package com.miaoyu.exe;
import com.miaoyu.algorithm.ColumnGeneration;
import com.miaoyu.algorithm.ColumnGeneration_RC;
import com.miaoyu.helper.Data;
import static com.miaoyu.helper.Functions.*;

import java.util.*;
import java.io.*;


public class CleanResult {

    public static void main(String[] args){
        File file = new File("result\\100\\CVRPTW\\RC\\solomon_100\\");
        System.out.println(Arrays.toString(file.listFiles()));
        ArrayList<String> summary = new ArrayList<>();
        for(File f : file.listFiles()) {
            System.out.println(f.toString());
            ArrayList<String[]> temp = readDataCSV(f.toString());
            System.out.println(temp.size());
            String output="";

            for(int i = 0; i < temp.size(); i++){
                output = output + temp.get(i)[0] + ",";
            }
            summary.add(output);

        }
        writeDataCSV("result\\100\\CVRPTW\\RC\\solomon_100\\","summary", summary);
    }
}
