package com.company;

import jsjf.CircularArrayQueue;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static int n;
    static int selectedMethod;
    static int methodType;
    static int runCheck;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter how many numbers you would like to sort: ");
        n = sc.nextInt();
        double time;
        int min = 0;
        int max = n;
        int[] tall = new int[max];
        Random randomNum = new Random();

        for(int i = 0; i< max; i++){
            tall[i] = randomNum.nextInt(max*2);

        }
        System.out.println("What sorting method would you like to use? Type 1 for insertion, 2 for merge, 3 for radix and 4 for quick");
        selectedMethod = sc.nextInt();
        System.out.println("Type 1 to execute sort or 2 to get an estimated C value");
        methodType = sc.nextInt();

        if(methodType == 2){
            estimate(tall, min, max);
        }
        else if(methodType == 1){


            time = System.currentTimeMillis();
            runSort(tall, min, max);
            time = System.currentTimeMillis() - time;
            System.out.println("Runtime: " +time+ " ms");

        }
        System.out.println("");
        System.out.println("//////////////////////////////////////////////////////////////////////////////////");
        System.out.println("Enter 1 to run again or any other character/number to stop the program ");
        runCheck = sc.nextInt();
        if(runCheck == 1){
            main(args);
        }
    }

    public static void estimate(int[] tall, int min, int max){
        switch (selectedMethod){
            case 1: estimateInsertion(tall);
            break;

            case 2: estimateMerge(tall, min, max-1);
            break;

            case 3: estimateRadix(tall, (int)Math.log10(max)+1);
            break;

            case 4: estimateQuick(tall, min, max-1);
            break;
        }
    }

    public static void runSort(int[] tall, int min, int max){
        switch (selectedMethod){
            case 1: insertionSort(tall);
                break;

            case 2: mergeSort(tall, min, max-1);
                break;

            case 3: radixSort(tall, (int)Math.log10(max)+1);
                break;

            case 4: quickSort(tall, min, max-1);
        }
    }

    public static void estimateQuick(int[] tall, int min, int max){
        double workload = n * Math.log10(n);
        double time = System.currentTimeMillis();
        quickSort(tall, min, max);
        time = System.currentTimeMillis() - time;
        double sum = 0;
        for(int i = n; i<=n*10;i+=n){
            sum += time/workload;
        }
        double estimated = sum/10;
        System.out.println("Estimated constant is: " +estimated);
    }

    public static void estimateInsertion(int[] tall){
        double workload = Math.pow(n,2);
        double time = System.currentTimeMillis();
        insertionSort(tall);
        time = System.currentTimeMillis() - time;
        double sum = 0;
        for(int i = n; i<=n*10;i+=n){
            sum += time/workload;
        }
        double estimated = sum/10;
        System.out.println("Estimated constant is: " +estimated);
    }

    public static void estimateMerge(int[] tall, int min, int max){
        double workload = n * Math.log10(n);
        double time = System.currentTimeMillis();
        mergeSort(tall, min, max);
        time = System.currentTimeMillis() - time;
        double sum = 0;
        for(int i = n; i<=n*10;i+=n){
            sum += time/workload;
        }
        double estimated = sum/10;
        System.out.println("Estimated constant is: " +estimated);
    }

    public static void estimateRadix(int[] tall, int max){
        double workload = n * max;
        double time = System.currentTimeMillis();
        radixSort(tall, max);
        time = System.currentTimeMillis() - time;
        double sum = 0;
        for(int i = n; i<=n*10;i+=n){
            sum += time/workload;
        }
        double estimated = sum/10;
        System.out.println("Estimated constant is: " +estimated);
    }

/////////////////////////////////////////////////insertion sort///////////////////////////////////////////////////////////////////////////
    public static void insertionSort(int A[])
    {
        // Innstikksortering av array med heltall
        int n = A.length;
        int key;
        for (int i = 1; i < n; i++)
        {
            // A er sortert t.o.m. indeks i-1
            key = A[i];
            int j = i;
            // Setter element nummer i pÃ¥ riktig plass
            // blant de i-1 fÃ¸rste elementene
            while (j > 0 && A[j-1] > key)
            {
                A[j] = A[j-1];
                j--;
            }
            A[j] = key;
        }
    }

////////////////////////////////////////////////Merge sort ///////////////////////////////////////////////
    public static void mergeSort (int[] A, int min, int max)
    {
        // Flettesortering av array med heltall
        if (min==max)
            return;
        int[] temp;
        int index1, left, right;
        int size = max - min + 1;
        int mid = (min + max) / 2;
        temp = new int[size];
        // Flettesorterer de to halvdelene av arrayen
        mergeSort(A, min, mid);
        mergeSort(A, mid + 1,max);
        // Kopierer array over i temp.array
        for (index1 = 0; index1 < size; index1++)
            temp[index1] = A[min + index1];
        // Fletter sammen de to sorterte halvdelene over i A
        left = 0;
        right = mid - min + 1;
        for (index1 = 0; index1 < size; index1++)
        {
            if (right <= max - min)
                if (left <= mid - min)
                    if (temp[left] > temp[right])
                        A[index1 + min] = temp[right++];
                    else
                        A[index1 + min] = temp[left++];
                else
                    A[index1 + min] = temp[right++];
            else
                A[index1 + min] = temp[left++];
        }
    }
    ///////////////////////////////////////////Quick sort///////////////////////////////////////////77
    public static void quickSort(int A[], int min, int max)
    {
        // Quicksort av array med heltall
        int indexofpartition;
        if (max - min  > 0)
        {
            // Partisjonerer array
            indexofpartition = findPartition(A, min, max);

            // Sorterer venstre del
            quickSort(A, min, indexofpartition - 1);

            // Sorterer hÃ¸yre del
            quickSort(A, indexofpartition + 1, max);
        }
    }

    private static int findPartition (int[] A, int min, int max) {
        int left, right;
        int temp, partitionelement;
        // Bruker *fÃ¸rste* element til Ã¥ dele opp
        partitionelement = A[min];
        left = min;
        right = max;
        // GjÃ¸r selve partisjoneringen
        while (left < right) {
            // Finn et element som er stÃ¸rre enn part.elementet
            while (A[left] <= partitionelement && left < right)
                left++;
            // Finn et element som er mindre enn part.elementet
            while (A[right] > partitionelement)
                right--;
            // Bytt om de to hvis ikke ferdig
            if (left < right) {
                temp = A[left];
                A[left] = A[right];
                A[right] = temp;
            }
        }
        // Sett part.elementet mellom partisjoneringene
        temp = A[min];
        A[min] = A[right];
        A[right] = temp;
        // Returner indeksen til part.elementet
        return right;
    }

//////////////////////////////////////////////radixSort/////////////////////////////////////////////////////////77
        public static void radixSort(int a[], int maksAntSiffer)
        {
            // Radixsortering av en array a med desimale heltall
            // maksAntSiffer: Maksimalt antall siffer i tallene
            int ti_i_m = 1; // Lagrer 10^m
            int n = a.length;
            // Oppretter 10 tomme kÃ¸er
            CircularArrayQueue<Integer>[] Q = (CircularArrayQueue<Integer>[])(new CircularArrayQueue[10]);
            for (int i = 0; i < 10; i++)
                Q[i] = new CircularArrayQueue<Integer>();
            // Sorterer pÃ¥ et og et siffer, fra venstre mot hÃ¸yre
           for (int m = 0; m < maksAntSiffer; m++)
            {
                // Fordeler tallene i 10 kÃ¸er
                for (int i = 0; i < n; i++)
                {
                    int siffer = (a[i] / ti_i_m) % 10;
                    Q[siffer].enqueue(a[i]);
                }
                // TÃ¸mmer kÃ¸ene og legger tallene fortlÃ¸pende tilbake i a
                int j = 0;
                for (int i = 0; i < 10; i++)
                    while (!Q[i].isEmpty())
                        a[j++] = (int) Q[i].dequeue();
                // Beregner 10^m for neste iterasjon
                ti_i_m *= 10;
            }

        }
}
