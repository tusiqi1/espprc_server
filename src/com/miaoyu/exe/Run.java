package com.miaoyu.exe;
import java.util.*;
import java.io.*;
import com.miaoyu.algorithm.*;
import com.miaoyu.helper.*;
import com.miaoyu.helper.Functions.*;
import java.lang.Runtime;
public class Run {
    public static Runtime rt = Runtime.getRuntime();

    public static void main(String[] args)throws InterruptedException, NumberFormatException, IOException{
        //record_pulse();
        record_pulse_m();
        //record_multi();
        //record();
    }

    public static void record(){
        {
            File file = new File("solomon_100/");
            System.out.println(Arrays.toString(file.listFiles()));
            int counter = 0;
            for(File f : file.listFiles()) {
                System.out.println(f.toString());
                if(!f.toString().endsWith(".txt")){
                    continue;
                }
                for(int l = 4; l < 6; l++){
                    for(int n = 161; n <=201; n=n+10){
                        Data data = new Data(f.toString(), n,l);
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
                        Functions.writeDataCSV("Data/UCVRP"+l+"/RandCol/", data.fileName+"-"+n, output);
                        //Functions.writeDataCSV("Data/UCVRP4/Pulse-multi/", data.fileName+"-"+n, output);
                        rt.gc();

                    }
                }

//            break;
            }
        }
    }

    public static void record_multi(){
        {
            File file = new File("solomon_100/");
            System.out.println(Arrays.toString(file.listFiles()));
            int counter = 0;
            for(File f : file.listFiles()) {
                System.out.println(f.toString());
                if(!f.toString().endsWith(".txt")){
                    continue;
                }
                for(int l = 4; l < 5; l++){
                    for(int n = 51; n <=101; n=n+10){
                        Data data = new Data(f.toString(), n,l);
                        ColumnGeneration_RCM cg = new ColumnGeneration_RCM(data);

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
                        output.add(Double.toString(cg.upperbound));

                        output.add(Double.toString(cg.timerBeforeLast/1000.0));
                        output.add(Double.toString((toc - tic)/1000.0));
                        Functions.writeDataCSV("Data/UCVRP"+l+"/RandCol-multi/", data.fileName+"-"+n, output);
                        //Functions.writeDataCSV("Data/UCVRP4/Pulse-multi/", data.fileName+"-"+n, output);
                        rt.gc();

                    }
                }

//            break;
            }
        }
    }

    public static void record_pulse(){
        {
            File file = new File("solomon_100/");
            System.out.println(Arrays.toString(file.listFiles()));
            int counter = 0;
            for(File f : file.listFiles()) {
                System.out.println(f.toString());
                if(!f.toString().endsWith(".txt")){
                    continue;
                }
                for(int l = 4; l < 6; l++){
                    for(int n = 161; n <=201; n=n+10){
                        Data data = new Data(f.toString(), n,l);
                        ColumnGeneration cg = new ColumnGeneration(data);

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
                        //output.add(Double.toString(cg.timerBeforeLast/1000.0));
                        output.add(Double.toString((toc - tic)/1000.0));
                        Functions.writeDataCSV("Data/UCVRP"+l+"/Pulse/", data.fileName+"-"+n, output);
                        //Functions.writeDataCSV("Data/UCVRP4/Pulse-multi/", data.fileName+"-"+n, output);
                        rt.gc();

                    }
                }

//            break;
            }
        }
    }

    public static void record_pulse_m(){
        {
            File file = new File("solomon_100/");
            System.out.println(Arrays.toString(file.listFiles()));
            int counter = 0;
            for(File f : file.listFiles()) {
                System.out.println(f.toString());
                if(!f.toString().endsWith(".txt")){
                    continue;
                }
                for(int l = 4; l < 5; l++){
                    for(int n = 101; n <=101; n=n+50){
                        Data data = new Data(f.toString(), n,l);
                        ColumnGeneration_multi cg = new ColumnGeneration_multi(data);

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
                        output.add(Double.toString(cg.upperbound));

                        //output.add(Double.toString(cg.timerBeforeLast/1000.0));
                        output.add(Double.toString((toc - tic)/1000.0));
                        Functions.writeDataCSV("Data/UCVRP"+l+"/Pulse-multi/", data.fileName+"-"+n, output);
                        //Functions.writeDataCSV("Data/UCVRP4/Pulse-multi/", data.fileName+"-"+n, output);
                        rt.gc();

                    }
                }

//            break;
            }
        }
    }

}
