package com.catway;

import java.io.*;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {

        printWordsForTest();

        //long timeStart = System.currentTimeMillis();
        //String fileName = "src/sub.srt";
        //String text = getText(fileName);
        //ArrayList<String> sentences = splitTextIntoSentences(text);
//
        ////for (String sentence: sentences) {
        ////    System.out.print(sentence + "\n");
        ////}
//
        //ArrayList<String> phrases = getPhrases();
//
        //for (String sentence: sentences) {
        //    for (String phrase: phrases) {
        //        if(sentence.toUpperCase().contains(phrase.toUpperCase()))
        //            System.out.print(phrase + "\n");
        //    }
        //}
//
        //long timeEnd = System.currentTimeMillis();
        //System.out.print(timeEnd-timeStart);


        //JsonFactory factory = new JsonFactory();
        //factory.enable(JsonParser.Feature.ALLOW_COMMENTS);
    }

    private static void printWordsForTest() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("lists/words")));

            int lineCount = 0;
            String line;
            while ((line = br.readLine()) != null) {
                if (++lineCount == 150) {
                    lineCount = 0;
                    System.out.print(line + '\n');
                }
            }
            br.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private static String getText(String fileName) {
        String text = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > 0 && !Character.isDigit(line.charAt(0))) {
                    text += line + ' ';
                }
            }
            br.close();
        } catch (IOException e) { e.printStackTrace(); }
        return text;
    }

    private static ArrayList<String> splitTextIntoSentences(String text) {
        ArrayList<String> sentences = new ArrayList<>();
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {
            sentences.add(text.substring(start,end-1));
        }
        return sentences;
    }

    public static ArrayList<String> getPhrases() {
        ArrayList<String> phrases = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("lists/phrases")));

            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > 0 && !Character.isDigit(line.charAt(0))) {
                    phrases.add(line);
                }
            }
            br.close();
        } catch (IOException e) { e.printStackTrace(); }

        return phrases;
    }
}
