package com.miaoyu.exe;
import java.util.*;
import java.io.*;
import com.miaoyu.algorithm.*;
import com.miaoyu.helper.*;
import com.miaoyu.helper.Functions.*;

public class Run {

    public static void main(String[] args)throws InterruptedException, NumberFormatException, IOException{
        File file = new File("solomon_100\\");
        System.out.println(Arrays.toString(file.listFiles()));
        int counter = 0;
        for(File f : file.listFiles()) {

            System.out.println(f.toString());
            Data data = new Data(f.toString());
            ColumnGeneration cg = new ColumnGeneration(data);
            long tic = System.currentTimeMillis();
            cg.solve();
            long toc = System.currentTimeMillis();

            ArrayList<String> output = new ArrayList<>();
            output.add(f.toString());
            output.add(Integer.toString(cg.nIter));
            output.add(Integer.toString(cg.nColumns));
            output.add(Double.toString(cg.lowerbound));
            output.add(Double.toString((toc - tic)/1000.0));
            Functions.writeDataCSV("result\\VRPTW\\ES\\", data.fileName, output);

            data = new Data(f.toString());
            ColumnGeneration_RC cgrc = new ColumnGeneration_RC(data);
            long tic2 = System.currentTimeMillis();
            cgrc.solve();
            long toc2 = System.currentTimeMillis();

            ArrayList<String> output2 = new ArrayList<>();
            output2.add(f.toString());
            output2.add(Integer.toString(cgrc.nIter));
            output2.add(Integer.toString(cgrc.nColumns));
            output2.add(Double.toString(cgrc.lowerbound));
            output2.add(Double.toString(cgrc.timerBeforeLast/1000.0));
            output2.add(Double.toString((toc2 - tic2)/1000.0));
            Functions.writeDataCSV("result\\VRPTW\\RC\\", data.fileName, output2);

        }
    }
}
