/**
 * This class contains the algorithm to solve ESPPRC by Feillet et al.
 */

package com.miaoyu.algorithm;

import com.miaoyu.helper.DataMD;
import com.miaoyu.helper.DataMD;
import com.miaoyu.helper.Route;

import java.util.*;

public class ESPPRC_MD {
    public DataMD data;
    public int depot;
    public Set<Route> solutionSet;

    public ESPPRC_MD(DataMD d, int depot){
        this.data = d;
        this.depot = depot;
        this.solutionSet = new HashSet<>();
    }

    public class RCTask implements Runnable{
        public ArrayList<Label> labels;
        public int[] color;
        public int nColor = data.capacity;
        public int nIter = 100;



        public void randomColor(){
            color = new int[data.nNode];
            for(int i = 1; i < data.nNode; i++){
                color[i] = (int) (Math.random() * nColor);
            }
        }

        public class Label{
            public int endNode;
            public int prevIndex;
            public double cost;
            public double time;
            public int demand;
            public boolean dominated;
            public boolean[] nodeVisited;
            public int length;

            public Label(int a1, int a2, double a3, double a4, int a5, boolean a6, boolean[] a7){
                endNode = a1;
                prevIndex = a2;
                cost = a3;
                time = a4;
                demand = a5;
                dominated = a6;
                nodeVisited = a7;
                length = 0;
            }
        }

        public class LabelComparator implements Comparator<Integer>{
            public int compare(Integer a, Integer b){
                Label l1 = labels.get(a);
                Label l2 = labels.get(b);
                if (l1.cost - l2.cost < -1e-6)
                    return -1;
                else if (l1.cost - l2.cost > 1e-6)
                    return 1;
                else  {
                    if (l1.endNode == l2.endNode) {
                        if (l1.time - l2.time < -1e-6)
                            return -1;
                        else if (l1.time - l2.time > 1e-7)
                            return 1;
                        else if (l1.demand - l2.demand < -1e-6)
                            return -1;
                        else if (l1.demand - l2.demand > 1e-6)
                            return 1;
                        else {
                            int i=0;
                            while (i < l1.nodeVisited.length) {
                                if (l1.nodeVisited[i] != l2.nodeVisited[i]) {
                                    if (l1.nodeVisited[i])
                                        return -1;
                                    else
                                        return 1;
                                }
                                i++;
                            }
                            return 0;
                        }
                    } else if (l1.endNode > l2.endNode)
                        return 1;
                    else
                        return -1;
                }
            }
        }
        public Set<Route> threadSolutionSet;
        public Set<Integer> test;

        public RCTask(){
            this.threadSolutionSet = new HashSet<>();
            this.test = new HashSet<>();
            this.labels = new ArrayList<Label>();
            this.color = new int[nColor];
        }
        public void run(){
            test.add(1);
            randomColor();
            int i, j, currentIndex;
            int nbsol;
            double  t,tt;
            int d, dd;
            int[] checkDom;
            Label current;
            // unprocessed labels list
            TreeSet<Integer> U = new TreeSet<Integer>(new LabelComparator());
            // processed labels list => ordered TreeSet List
            TreeSet<Integer> P = new TreeSet<Integer>(new LabelComparator());
            boolean[] cust= new boolean[nColor];
            for (i=0;i<nColor;i++)
                cust[i]=false;
            labels.add(new Label(0,-1,0.0,0,0,false,cust));	// first label: start from depot (client 0)
            U.add(0);

            // for each node, an array with the index of the corresponding labels (for dominance)
            checkDom = new int[data.nNode+1];
            ArrayList<Integer>[] node2labels = new ArrayList[data.nNode+1];
            for (i=0; i<data.nNode+1; i++) {
                node2labels[i]=new ArrayList<Integer>();
                checkDom[i]=0;  // index of the first label in city2labels that needs to be checked for dominance (last labels added)
            }
            node2labels[0].add(0);

            while (U.size() > 0){
                if(P.size() > 30){
                    break;
                }
                currentIndex = U.pollFirst();
                current = labels.get(currentIndex);

                int i1, i2;
                boolean pathdom;
                Label l1 , l2;
                // check for dominance

                ArrayList<Integer> cleaning = new ArrayList<Integer>();
                for (i = checkDom[current.endNode]; i < node2labels[current.endNode].size(); i++) {
                    // check for dominance between the labels added since the last time we came here with this city and all the other ones
                    for (j = 0; j < i; j++) {
                        i1 = node2labels[current.endNode].get(i);
                        i2 = node2labels[current.endNode].get(j);
                        l1 = labels.get(i1);
                        l2 = labels.get(i2);
                        if (!(l1.dominated || l2.dominated)) {  // could happen since we clean 'node2labels' thanks to 'cleaning' only after the double loop
                            pathdom = true;
                            for (int k = 1; pathdom && (k < nColor); k++)
                                pathdom = (!l1.nodeVisited[k] || l2.nodeVisited[k]);
                            if (pathdom && (l1.cost<=l2.cost) && (l1.time<=l2.time) && (l1.demand<=l2.demand)&& (l1.length <= l2.length)) {
                                labels.get(i2).dominated = true;
                                U.remove((Integer) i2);
                                cleaning.add(i2);
                                pathdom = false;
                                //System.out.print(" ###Remove"+i2);
                            }
                            pathdom = true;
                            for (int k = 1; pathdom && (k < nColor); k++)
                                pathdom = (!l2.nodeVisited[k] || l1.nodeVisited[k]);

                            if (pathdom && (l2.cost<=l1.cost) && (l2.time<=l1.time) && (l2.demand<=l1.demand)&& (l2.length <= l1.length)) {
                                labels.get(i1).dominated = true;
                                U.remove(i1);
                                cleaning.add(i1);
                                //System.out.print(" ###Remove"+i1);
                                j = node2labels[current.endNode].size();
                            }
                        }
                    }
                }

                for (Integer c : cleaning)
                    node2labels[current.endNode].remove(c);   // a little bit confusing but ok since c is an Integer and not an int!

                cleaning = null;
                checkDom[current.endNode] = node2labels[current.endNode].size();  // update checkDom: all labels currently in city2labels were checked for dom.

                // expand REF
                if (!current.dominated){
                    //System.out.println("Label "+current.city+" "+current.indexPrevLabel+" "+current.cost+" "+current.time+" "+current.dominated);
                    if (current.endNode == data.nNode) { // shortest path candidate to the depot!
                        if (current.cost<-1e-7)	{				// SP candidate for the column generation
                            P.add(currentIndex);
                            nbsol=0;
                            for (Integer labi : P) {
                                Label s = labels.get(labi);
                                if (!s.dominated)
                                    nbsol++;
                            }

                        }
                    } else {  // if not the depot, we can consider extensions of the path
                        for (i = 1; i < data.nNode + 1; i++) {
//                        if(current.length == nColor && i != data.nNode)
//                            continue;
                            if ((i == data.nNode || (!current.nodeVisited[color[i]]) && (data.cost[depot][current.endNode][i] < 1e6))) {  // don't go back to a vertex already visited or along a forbidden edge
                                // time
                                t =  (current.time + data.distance[depot][current.endNode][i] + data.service_time[current.endNode]);
                                if (t < data.tw_a[i])
                                    t = data.tw_a[i];
                                // demand
                                d = current.demand + data.demand[i];
                                //System.out.println("  -- "+i+" d:"+d+" t:"+tt);

                                // is feasible?
                                if ((t <= data.tw_b[i]) && (d <= data.capacity)) {
                                    int idx = labels.size();
                                    boolean[] newcust = new boolean[nColor];
                                    System.arraycopy(current.nodeVisited, 0, newcust, 0, nColor);
                                    if(i != data.nNode)
                                        newcust[color[i]] = true;
                                    //speedup: third technique - Feillet 2004 as mentioned in Laporte's paper
//                                for (j=1;j<=data.nNode;j++)
//                                    if (!newcust[j]) {
//                                        tt=(double) (t+data.distance[i][j]+data.service_time[i]);
//                                        dd=d+data.demand[j];
//                                        if ((tt>data.tw_b[j]) || (dd>data.capacity))
//                                            newcust[j]=true;  // useless to visit this client
//                                    }
                                    Label nl = new Label(i, currentIndex, current.cost+data.cost[depot][current.endNode][i], t, d, false, newcust);
                                    nl.length = current.length + 1;
                                    labels.add(nl);	// first label: start from depot (client 0)
                                    if (!U.add((Integer) idx)) {
                                        // only happens if there exists already a label at this vertex with the same cost, time and demand and visiting the same cities before
                                        // It can happen with some paths where the order of the cities is permuted
                                        labels.get(idx).dominated = true; // => we can forget this label and keep only the other one
                                    } else
                                        node2labels[i].add(idx);

                                }
                            }
                        }
                    }
                }
            }
            // clean
            checkDom = null;

            // filtering: find the path from depot to the destination
            Integer lab;
            i = 0;
//            while (((lab = P.pollFirst()) != null)) {
//                Label s = labels.get(lab);
//                if (!s.dominated) {
//                    if ((s.cost < -1e-4)) {
//                        // System.out.println(s.cost);
//                        ArrayList<Integer> temp = new ArrayList<Integer>();
//                        int path = s.prevIndex;
//                        while (path > 0) {
//                            temp.add(labels.get(path).endNode);
//                            path = labels.get(path).prevIndex;
//                        }
//                        Collections.reverse(temp);
//                        Route newroute = new Route(temp);
//                        newroute.cost = s.cost;
//                        threadSolutionSet.add(newroute);
//                    }
//                }
//
//            }
            while ((i < 30) && ((lab = P.pollFirst()) != null)) {
                Label s = labels.get(lab);
                if (!s.dominated) {
                    if ((i < 30 / 2) || (s.cost < -1e-4)) {
                        // System.out.println(s.cost);
                        ArrayList<Integer> temp = new ArrayList<Integer>();
                        int path = s.prevIndex;
                        while (path > 0) {
                            temp.add(labels.get(path).endNode);
                            path = labels.get(path).prevIndex;
                        }
                        Collections.reverse(temp);
                        Route newroute = new Route(temp);
                        newroute.cost = s.cost;
                        threadSolutionSet.add(newroute);
                        i++;
                    }
                }

            }
        }
    }





    public boolean solve(){
        RCTask[] tasks = new RCTask[data.maxThread];
        for(int i = 0; i <data.maxThread; i++){
            tasks[i] = new RCTask();
        }
        try{
            for(int i = 1; i < data.maxThread; i++){
                data.threads[i] = new Thread(tasks[i]);
                data.threads[i].start();
            }


        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
            for(int i = 1; i < data.maxThread; i++){
                data.threads[i].join();
            }
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for(int i = 1; i < data.maxThread; i++){
            solutionSet.addAll(tasks[i].threadSolutionSet);
        }

        if(solutionSet.size() > 0){
            System.out.println("new routes count"+solutionSet.size());
            return true;
        }else{
            return false;
        }
    }

}
