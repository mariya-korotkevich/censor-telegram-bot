package ru.korotkevich.impl;

import org.springframework.stereotype.Component;
import ru.korotkevich.abstracts.WordFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class WordFilterImpl implements WordFilter {

    List<String> badWords = new ArrayList<>();

    {
        try (Scanner scanner = new Scanner(new File("words.txt"))){
            while (scanner.hasNextLine()){
                badWords.add(scanner.nextLine().toLowerCase());
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public String filter(String word) {
        if (!badWords.contains(word.toLowerCase())){
            return word;
        }
        char[] chars = word.toCharArray();
        for (int i = 1; i < chars.length - 1; i++) {
            chars[i] = '*';
        }
        return new String(chars);
    }
}
