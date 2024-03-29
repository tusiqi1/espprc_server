package com.miaoyu.exe;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.*;
import com.miaoyu.helper.*;
import com.miaoyu.algorithm.*;

public class test {
    public static void main(String[] args){

        String fileName = ".\\solomon_100\\C121.txt";

        Data data = new Data(fileName, 101,4);
        System.out.println("here");
        ColumnGeneration_multi cg2 = new ColumnGeneration_multi(data);

        long tic2 = System.currentTimeMillis();
        //cg2.test();
        try{
            cg2.solve();
        }
        catch (Exception e){
            System.out.println(e);
        }
        long toc2 = System.currentTimeMillis();


        System.out.println("Solution time for general algorithm: " + (double)(toc2-tic2)/1000.0);
        //System.out.println("Solution time for randomized algorithm: " + (double)(toc - tic)/1000.0);


    }
}
