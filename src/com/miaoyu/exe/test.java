package com.miaoyu.exe;
import java.lang.reflect.Array;
import java.util.*;
import com.miaoyu.helper.*;
import com.miaoyu.algorithm.*;

public class test {
    public static void main(String[] args){

        String fileName = "./solomon_100/R104.txt";
//        Data data = new Data(fileName);
//        ColumnGeneration_RC cg = new ColumnGeneration_RC(data);
//
//        long tic = System.currentTimeMillis();
//        cg.solve();
//        long toc = System.currentTimeMillis();
        String f = fileName.substring(fileName.length()-8);
        System.out.println(f+","+f.substring(0,1)+"");
        Data data = new Data(fileName);
        ColumnGeneration cg2 = new ColumnGeneration(data);

        long tic2 = System.currentTimeMillis();
        //cg2.test();
        try{
        cg2.solve();}
        catch (Exception e){
            System.out.println(e);
        }
        long toc2 = System.currentTimeMillis();


        System.out.println("Solution time for general algorithm: " + (double)(toc2-tic2)/1000.0);
        //System.out.println("Solution time for randomized algorithm: " + (double)(toc - tic)/1000.0);


    }
}
