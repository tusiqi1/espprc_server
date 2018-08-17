package com.miaoyu.exe;
import java.util.*;
import java.io.*;
import com.miaoyu.algorithm.*;
import com.miaoyu.helper.*;
import com.miaoyu.helper.Functions.*;

public class Run {

    public static void main(String[] args)throws InterruptedException, NumberFormatException, IOException{
        record();
    }

    public static void record(){
        {
            File file = new File("solomon_100/");
            System.out.println(Arrays.toString(file.listFiles()));
            int counter = 0;
            for(File f : file.listFiles()) {
                System.out.println(f.toString());
                if(!f.toString().endsWith("R500.txt")){
                    continue;
                }
                for(int n = 201; n <=501; n=n+100){
                    Data data = new Data(f.toString(), n,4);
                    ColumnGeneration_RC cg = new ColumnGeneration_RC(data);

                    //ColumnGeneration cg = new ColumnGeneration(data);

                    long tic = System.currentTimeMillis();
                    try{
                        cg.solve();
                    }catch (Exception e){

                    }
                    long toc = System.currentTimeMillis();

                    ArrayList<String> output = new ArrayList<>();
                    output.add(data.fileName);
                    output.add(Integer.toString(data.nNode));
                    output.add(Integer.toString(cg.nIter));
                    output.add(Integer.toString(cg.nColumns));
                    output.add(Double.toString(cg.lowerbound));
                    output.add(Double.toString(cg.timerBeforeLast/1000.0));
                    output.add(Double.toString((toc - tic)/1000.0));
                    Functions.writeDataCSV("Data/UCVRP4/RandCol/", data.fileName+"-"+n, output);
                    //Functions.writeDataCSV("Data/UCVRP4/Pulse-multi/", data.fileName+"-"+n, output);

                }

//            break;
            }
        }
    }


}
