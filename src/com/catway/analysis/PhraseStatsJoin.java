package com.catway.analysis;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by administrator on 9/27/16.
 */
public class PhraseStatsJoin {

    static String dirPath = "harrypotter/";

    public static void main(String[] args) {

        PhraseStats resultPhraseStats = new PhraseStats();
        for (int i = 1; i <= 7; i++) {
            PhraseStats phraseStats = loadPhraseStats(String.format("%s%d%s", dirPath, i, ".pstat"));
            System.out.print(String.format("Unique phrases Hp%d : %d", i, phraseStats.phrases.keySet().size()));
            joinPhraseStats(resultPhraseStats, phraseStats);
        }

        HarryPotter.printPhraseStatDict(resultPhraseStats);
        System.out.print(String.format("Unique phrases: %d", resultPhraseStats.phrases.keySet().size()));

        writePhraseStats(resultPhraseStats, dirPath + "hp.pstat");
    }

    public static void joinPhraseStats(PhraseStats dest, PhraseStats src) {
        for (Map.Entry<String, Integer> entry : src.phrases.entrySet()) {
            if (dest.phrases.containsKey(entry.getKey())) {
                dest.phrases.put(entry.getKey(), dest.phrases.get(entry.getKey()) + entry.getValue());
            } else {
                dest.phrases.put(entry.getKey(), entry.getValue());
            }
        }
    }

    private static void writePhraseStats(PhraseStats phraseStats, String path) {
        try {
            new ObjectMapper().writeValue(new File(path), phraseStats);
        }
        catch (IOException e) { e.printStackTrace(); }
    }


    public static PhraseStats loadPhraseStats(String filePath) {
        PhraseStats phraseStats = null;
        try {
            phraseStats = new ObjectMapper().readValue(new File(filePath), PhraseStats.class);
        }
        catch (Exception e) { e.printStackTrace();}
        return phraseStats;
    }
}