package com.catway.analysis;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;


/**
 * Created by administrator on 9/27/16.
 */
public class HarryPotter {

    static String filePath = "harrypotter/7";


    static PhraseStats phraseStats = new PhraseStats();
    public static void main(String[] args) {

        long timeStart = System.currentTimeMillis();
        String text = getText(filePath);
        ArrayList<String> sentences = splitTextIntoSentences(text);
        ArrayList<String> phrases = getPhrases();

        //printSentences(sentences);


        int phraseCount = 0;
        for (String sentence : sentences) {
            for (String phrase : phrases) {
                if (sentence.toUpperCase().contains(phrase.toUpperCase())) {
                    //System.out.print(phraseStats);
                    Integer value = phraseStats.phrases.get(phrase);
                    if (value == null) phraseStats.phrases.put(phrase, 1);
                    else phraseStats.phrases.put(phrase, value + 1);

                    phraseCount++;
                }
            }
        }

        long timeEnd = System.currentTimeMillis();
        System.out.print(String.format("Time: %d, Phrases: %d, Unique: %d", timeEnd - timeStart, phraseCount, phraseStats.phrases.keySet().size()));


        printPhraseStatDict(phraseStats);

        writePhraseStats(phraseStats);
    }

    private static void writePhraseStats(PhraseStats phraseStats) {
        try {
            new ObjectMapper().writeValue(new File(filePath + ".pstat"), phraseStats);
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    public static void printPhraseStatDict(PhraseStats phraseStats) {
        for (Map.Entry<String, Integer> entry: phraseStats.phrases.entrySet()) {
            System.out.print(entry.getKey() + " " + entry.getValue() + '\n');
        }
    }

    private static void printSentences(ArrayList<String> sentences) {
        for (String sentence: sentences) {
            System.out.print(sentence + "\n");
        }
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
                if (line.length() > 0) {
                    phrases.add(line);
                    //System.out.print(line);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return phrases;
    }
}
