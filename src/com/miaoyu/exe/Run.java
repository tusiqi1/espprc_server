package com.miaoyu.exe;
import java.util.*;
import java.io.*;
import com.miaoyu.algorithm.*;
import com.miaoyu.helper.*;
import com.miaoyu.helper.Functions.*;

public class Run {

    public static void main(String[] args)throws InterruptedException, NumberFormatException, IOException{
        File file = new File("solomon_100/");
        System.out.println(Arrays.toString(file.listFiles()));
        int counter = 0;
        for(File f : file.listFiles()) {
            System.out.println(f.toString());
            if(!f.toString().endsWith(".txt")){
                continue;
            }
            for(int n = 51; n <=101; n=n+10){
                Data data = new Data(f.toString(), n);
                ColumnGeneration_RC cg = new ColumnGeneration_RC(data);
                //ColumnGeneration cg = new ColumnGeneration(data);

                long tic = System.currentTimeMillis();
                cg.solve();
                long toc = System.currentTimeMillis();

                ArrayList<String> output = new ArrayList<>();
                output.add(f.toString());
                output.add(Integer.toString(cg.nIter));
                output.add(Integer.toString(cg.nColumns));
                output.add(Double.toString(cg.lowerbound));
                output.add(Double.toString(cg.timerBeforeLast/1000.0));
                output.add(Double.toString((toc - tic)/1000.0));
                Functions.writeDataCSV("Data/UCVRP7/RandCol/", data.fileName+"-"+n, output);
                //Functions.writeDataCSV("Data/UCVRP6/Pulse/", data.fileName+"-"+n, output);

            }

//            break;
        }
    }
}
