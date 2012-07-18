/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ugenlik1
 */
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

public class Submission {

    /**
     * @param args the command line arguments
     */
    private static int n;
    private static double C, p[], w[];
    private static Integer order[];
    private static double maxProfit = 0.0;
    
    private static void checkNode(int node, boolean include, double weight, double profit, double upperBound) {
        double wi = w[order[node]];
        double pi = p[order[node]];
        
        if (include) {
            profit += pi;
            weight += wi;
        }
        
        upperBound -= pi;
        
        //Slide 19/26
        if (weight <= C && profit > maxProfit) {
            maxProfit = profit;
        }
        if ((weight <= C) && (profit+upperBound >= maxProfit)) {
            if ((node+1) < n){
                checkNode(node+1, true, weight, profit, upperBound);
                checkNode(node+1, false, weight, profit, upperBound);
            }
        }
    }


    private static void read(String inf, String outf) {

        try {
            //Open the input/output files
            BufferedReader bufRead = new BufferedReader(new FileReader(inf));
            PrintWriter pw = new PrintWriter(new FileWriter(outf), true);

            String line = bufRead.readLine();

            while (line != null) {
                //Read the problem
                String[] v = line.split(" ");
                n = Integer.valueOf(v[0]);
                C = Integer.valueOf(v[1]);
                
                w = new double[n];
                p = new double[n];
                order = new Integer[n];

                for (int i = 0; i < n; i++) {
                    line = bufRead.readLine();
                    v = line.split(" ");
                    w[i] = Integer.valueOf(v[0]);
                    p[i] = Integer.valueOf(v[1]);
                    order[i] = i;
                }

                //Start timing
                long startTime = System.currentTimeMillis();

                //According to slide 28, it's in "non-increasing pi/wi" order
                Arrays.sort(order, new Comparator<Integer>() {

                    @Override
                    public int compare(Integer i1, Integer i2) {
                        double p1 = p[i1.intValue()];
                        double w1 = w[i1.intValue()];
                        double p2 = p[i2.intValue()];
                        double w2 = w[i2.intValue()];
                        return -Double.compare(p1 / w1, p2 / w2);
                    }
                });
                
                //for (int i = 0; i < n; i++)
                //    System.out.println(p[order[i]]/w[order[i]]);
                
                //Calculate the best profit
                double bestProfit = fill();

                long estimatedTime = System.currentTimeMillis() - startTime;


                pw.println(n + " " + bestProfit + " " + (estimatedTime));

                line = bufRead.readLine();
            }

            bufRead.close();
            pw.close();

        } catch (IOException e) {
        }


    }
    
    private static double fill() {
        maxProfit = 0.0;
        
        double upperBound = 0;
        for (int i = 0; i < n; i++) upperBound+=p[i];
        
        checkNode(0, true, 0.0, 0.0, upperBound);
        checkNode(0, false, 0.0, 0.0, upperBound);

        System.out.println("max profit is = " + maxProfit);

        return maxProfit;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        read(
                args.length < 1 ? "input.txt" : args[0],
                args.length < 2 ? "output.txt" : args[1]);
    }
}
