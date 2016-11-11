package com.catway;


import java.util.Random;

public class Test100Threads {
    static int N = 100000000;
    public static void main(String[] args) {
        int [] matrix = new int[N];

        long start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            matrix[i] = i%10;
        }

        long end = System.currentTimeMillis();

        System.out.print(String.format("Fill %s ms\n", end-start));


        start = System.currentTimeMillis();

        float average = matrix[0];
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += matrix[i]*10/5+2*matrix[i]+matrix[i]*matrix[i]+new Random().nextInt()%10+matrix[i];
        }
        average = sum/N;

        end = System.currentTimeMillis();
        System.out.print(String.format("count %s ms\n", end-start));

        final int threads = 10;

        for (int threadNum = 0; threadNum < threads; threadNum++) {
            final int finalThreadNum = threadNum;
            final int[] cMatrix = matrix;
            new Thread(new Runnable() {
                int sum = 0;

                @Override
                public void run() {
                    for (int j = finalThreadNum * N / 10; j < (finalThreadNum + 1) * N / 10; j++) {
                        sum += cMatrix[j] * 10 / 5 + 2 * cMatrix[j] + cMatrix[j] * cMatrix[j] + new Random().nextInt() % 10 + cMatrix[j];
                    }


                }
            }).run();
        }

    }
}