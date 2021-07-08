//********************************************************************************************************
// CLASS: Main (Main.java)
//
// DESCRIPTION
// Reading other files and computing the numbers in the file and outputting to another file. 
//
// COURSE AND PROJECT INFO
// CSE205 Object Oriented Programming and Data Structures, summer and 2021
// Project Number: project-number 1
// 
// AUTHOR: Erik Christian Gotta, egotta, egotta@asu.edu
//********************************************************************************************************

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.lang.Integer;

public class Main {

    /**
    * The main method calling the run method that will start the code.
    */
    public static void main(String[] args) {
        // Main mainObject = new Main();
        // mainObject.run();
        new Main().run();
    }

     /**
    * The run method that try catches the files and declares different arraylists for run ups and run downs.
    */
    private void run() {

        ArrayList<Integer> list = new ArrayList<>();

        String inFileName = "p01-in.txt";
        // String inFileName = "C:\\Users\\erikg\\Desktop\\test.txt";

        try {
            list = readInputFile(inFileName);    
        } catch (FileNotFoundException pExcept) {
            System.out.println("Oops, could not open 'p01-in.txt' for reading. The program is ending.");
            System.exit(-100);
        }
        
        final int RUNS_UP = 1;
        final int RUNS_DN = 2;

        ArrayList<Integer> listRunsUpCount = new ArrayList<>();
        ArrayList<Integer> listRunsDnCount = new ArrayList<>();

        listRunsUpCount = findRuns(list, RUNS_UP);
        listRunsDnCount = findRuns(list, RUNS_DN);

        ArrayList<Integer> listRunsCount = new ArrayList<>();
        listRunsCount = mergeLists(listRunsUpCount, listRunsDnCount);

        String outFileName = "p01-runs.txt";
        try {
            writeOutputFile(outFileName, listRunsCount);
        } catch (FileNotFoundException pExcept) {
            System.out.println("Oops, could not open 'p01-runs.txt' for writing. The program is ending.");
            System.exit(-200);
        }
    }

     /**
    * This method finds and returns how many runs by iterating through the arraylist with a while loop.
    */
    public ArrayList<Integer> findRuns(ArrayList<Integer> pList, int pDir) {
        ArrayList<Integer> listRunsCount = arrayListCreate(pList.size(), 0);
        int i = 0;
        int k = 0;

        while (i < pList.size() - 1) {
            if (pDir == 1 && pList.get(i) <= pList.get(i + 1)) {
                k++;
            } else if (pDir == 2 && pList.get(i) >= pList.get(i + 1)) {
                k++;
            } else {
                if (k != 0) {
                    listRunsCount.set(k, listRunsCount.get(k) + 1);
                    k = 0;
                }
            }
            i++;
        }

        if (k != 0) {
            listRunsCount.set(k, listRunsCount.get(k) + 1);
        }

        return listRunsCount;
    }

     /**
    * mergeLists take both run up and run downs and adds them to find the total runs count
    */
    public ArrayList<Integer> mergeLists(ArrayList<Integer> pListRunsUpCount, ArrayList<Integer> pListRunsDnCount) {
        ArrayList<Integer> listRunsCount = arrayListCreate(pListRunsUpCount.size(), 0);

        for (int i = 0; i < pListRunsUpCount.size() - 1; i++) {
            listRunsCount.set(i, pListRunsUpCount.get(i) + pListRunsDnCount.get(i));
        }

        return listRunsCount;
    }

     /**
    * This method creates and arraylist with a certain size and initializes all the values
    */
    public ArrayList<Integer> arrayListCreate(int pSize, int pInitValue) {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < pSize; i++) {
            list.add(pInitValue);
        }

        return list;
    }

    /**
    * This file writes out to the new file the amount total and amount of runs at the indices 
    */
    public void writeOutputFile(String pFilename, ArrayList<Integer> pListRuns) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new File(pFilename));
        
        int totalRuns = 0;
        for (Integer element : pListRuns) {
            totalRuns += element;
        }

        out.printf("runs_total: %d\n", totalRuns);

        for (int k = 1; k < pListRuns.size(); k++) {
            out.printf("runs_%d: %d\n", k, pListRuns.get(k));
        }

        out.close();
    }

     /**
    * This method reads the file taking all integers and creates an arraylist from them
    */
    public ArrayList<Integer> readInputFile(String pFilename) throws FileNotFoundException {
        Scanner in = new Scanner(new File(pFilename));  
        ArrayList<Integer> list = new ArrayList<>();
        
        while (in.hasNextInt()) {
            int i = in.nextInt();
            list.add(i);
        }
        in.close();   
        return list;
    }
}
