package com.miaoyu.exe;
import java.util.*;
import java.io.*;
import com.miaoyu.algorithm.*;
import com.miaoyu.helper.*;
import com.miaoyu.helper.Functions.*;

public class Run {

    public static void main(String[] args)throws InterruptedException, NumberFormatException, IOException{
       //record();
        record_multi();
//        record_pulse();
//        record_pulse5();
//        record_pulse10();
//        record_pulse15();
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
                    Data data = new Data(f.toString(), n);
                    ColumnGeneration_RC cg = new ColumnGeneration_RC(data);

                    //ColumnGeneration cg = new ColumnGeneration(data);

                    long tic = System.currentTimeMillis();
                    try{
                        cg.solve();
                    }catch (Exception e){

                    }
                    long toc = System.currentTimeMillis();

                    ArrayList<String> output = new ArrayList<>();
                    output.add(f.toString());
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

    public static  void record_multi(){
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
                    Data data = new Data(f.toString(), n);
                    ColumnGeneration_RCM cg = new ColumnGeneration_RCM(data);

                    //ColumnGeneration cg = new ColumnGeneration(data);

                    long tic = System.currentTimeMillis();
                    try{
                        cg.solve();
                    }catch (Exception e){

                    }
                    long toc = System.currentTimeMillis();

                    ArrayList<String> output = new ArrayList<>();
                    output.add(f.toString());
                    output.add(Integer.toString(cg.nIter));
                    output.add(Integer.toString(cg.nColumns));
                    output.add(Double.toString(cg.lowerbound));
                    output.add(Double.toString(cg.timerBeforeLast/1000.0));
                    output.add(Double.toString((toc - tic)/1000.0));
                    Functions.writeDataCSV("Data/UCVRP4/RandCol-multi/", data.fileName+"-"+n, output);
                    //Functions.writeDataCSV("Data/UCVRP4/Pulse-multi/", data.fileName+"-"+n, output);

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
                if(!f.toString().endsWith("R500.txt")){
                    continue;
                }
                for(int n = 201; n <=501; n=n+100){
                    Data data = new Data(f.toString(), n);
                    //ColumnGeneration_RC cg = new ColumnGeneration_RC(data);

                    ColumnGeneration cg = new ColumnGeneration(data);

                    long tic = System.currentTimeMillis();
                    try{
                        cg.solve();
                    }catch (Exception e){

                    }
                    long toc = System.currentTimeMillis();

                    ArrayList<String> output = new ArrayList<>();
                    output.add(f.toString());
                    output.add(Integer.toString(cg.nIter));
                    output.add(Integer.toString(cg.nColumns));
                    output.add(Double.toString(cg.lowerbound));
                    //output.add(Double.toString(cg.timerBeforeLast/1000.0));
                    output.add(Double.toString((toc - tic)/1000.0));
                    //Functions.writeDataCSV("Data/UCVRP4/RandCol/", data.fileName+"-"+n, output);
                    Functions.writeDataCSV("Data/UCVRP4/Pulse-multi/", data.fileName+"-"+n, output);

                }

//            break;
            }
        }
    }

    public static void record_pulse5(){
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
                    Data data = new Data(f.toString(), n);
                    //ColumnGeneration_RC cg = new ColumnGeneration_RC(data);

                    ColumnGeneration5 cg = new ColumnGeneration5(data);

                    long tic = System.currentTimeMillis();
                    try{
                        cg.solve();
                    }catch (Exception e){

                    }
                    long toc = System.currentTimeMillis();

                    ArrayList<String> output = new ArrayList<>();
                    output.add(f.toString());
                    output.add(Integer.toString(cg.nIter));
                    output.add(Integer.toString(cg.nColumns));
                    output.add(Double.toString(cg.lowerbound));
                    //output.add(Double.toString(cg.timerBeforeLast/1000.0));
                    output.add(Double.toString((toc - tic)/1000.0));
                    //Functions.writeDataCSV("Data/UCVRP4/RandCol/", data.fileName+"-"+n, output);
                    Functions.writeDataCSV("Data/UCVRP4/Pulse-multi5/", data.fileName+"-"+n, output);

                }

//            break;
            }
        }
    }

    public static void record_pulse10(){
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
                    Data data = new Data(f.toString(), n);
                    //ColumnGeneration_RC cg = new ColumnGeneration_RC(data);

                    ColumnGeneration10 cg = new ColumnGeneration10(data);

                    long tic = System.currentTimeMillis();
                    try{
                        cg.solve();
                    }catch (Exception e){

                    }
                    long toc = System.currentTimeMillis();

                    ArrayList<String> output = new ArrayList<>();
                    output.add(f.toString());
                    output.add(Integer.toString(cg.nIter));
                    output.add(Integer.toString(cg.nColumns));
                    output.add(Double.toString(cg.lowerbound));
                    //output.add(Double.toString(cg.timerBeforeLast/1000.0));
                    output.add(Double.toString((toc - tic)/1000.0));
                    //Functions.writeDataCSV("Data/UCVRP4/RandCol/", data.fileName+"-"+n, output);
                    Functions.writeDataCSV("Data/UCVRP4/Pulse-multi10/", data.fileName+"-"+n, output);

                }

//            break;
            }
        }
    }

    public static void record_pulse15(){
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
                    Data data = new Data(f.toString(), n);
                    //ColumnGeneration_RC cg = new ColumnGeneration_RC(data);

                    ColumnGeneration15 cg = new ColumnGeneration15(data);

                    long tic = System.currentTimeMillis();
                    try{
                        cg.solve();
                    }catch (Exception e){

                    }
                    long toc = System.currentTimeMillis();

                    ArrayList<String> output = new ArrayList<>();
                    output.add(f.toString());
                    output.add(Integer.toString(cg.nIter));
                    output.add(Integer.toString(cg.nColumns));
                    output.add(Double.toString(cg.lowerbound));
                    //output.add(Double.toString(cg.timerBeforeLast/1000.0));
                    output.add(Double.toString((toc - tic)/1000.0));
                    //Functions.writeDataCSV("Data/UCVRP4/RandCol/", data.fileName+"-"+n, output);
                    Functions.writeDataCSV("Data/UCVRP4/Pulse-multi15/", data.fileName+"-"+n, output);

                }

//            break;
            }
        }
    }

}
