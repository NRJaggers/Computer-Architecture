/**
 * Programmers: Nathan Jaggers, Storm Randolph
 * 
 * CPE 315 - Computer Architecture
 * Dr.Seng
 * Spring 2022
 * 
 * Lab 6 - Cache Simulator
 * 
 * Description:
 */

package Lab6;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class lab6 {

    public static void main(String[] args) {

        // declare caches
        cache test1 = new cache("Cache #1", 2048, 1, 1);
        cache test2 = new cache("Cache #2", 2048, 1, 2);
        cache test3 = new cache("Cache #3", 2048, 1, 4);
        cache test4 = new cache("Cache #4", 2048, 2, 1);
        cache test5 = new cache("Cache #5", 2048, 4, 1);
        cache test6 = new cache("Cache #6", 2048, 4, 4);
        cache test7 = new cache("Cache #7", 4096, 1, 1);

        try {
            // read entrys in memory file
            // Scanner file_input = new Scanner(new File("./Lab6/mem_stream.1"));
            Scanner file_input = new Scanner(new File(args[0]));

            while (file_input.hasNextLine()) {
                // read line in file, parse it and then update caches
                String line = file_input.nextLine();// obtaining file input

                //split line and parse out address
                String[] splitLine = line.split("\t");
                int address = Integer.parseInt(splitLine[1],16);

                test1.search(address);
                test2.search(address);
                test3.search(address);
                test4.search(address);
                test5.search(address);
                test6.search(address);
                test7.search(address);

            }

            // print out results for caches
            test1.showSummary();
            System.out.println("---------------------------");
            test2.showSummary();
            System.out.println("---------------------------");
            test3.showSummary();
            System.out.println("---------------------------");
            test4.showSummary();
            System.out.println("---------------------------");
            test5.showSummary();
            System.out.println("---------------------------");
            test6.showSummary();
            System.out.println("---------------------------");
            test7.showSummary();
            System.out.println("---------------------------");

        } catch (IllegalStateException | FileNotFoundException e) {
            // if trouble opening file
            System.out.println("Scanner was closed");
        }
    }
}
