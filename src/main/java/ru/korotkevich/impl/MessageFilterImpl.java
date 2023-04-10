package ru.korotkevich.impl;

import org.springframework.stereotype.Component;
import ru.korotkevich.abstracts.MessageFilter;
import ru.korotkevich.abstracts.WordFilter;

import java.util.List;

@Component
public class MessageFilterImpl implements MessageFilter {

    private final WordFilter wordFilter;
    private final List<Character> wordSeparators = List.of('.', ',', ' ', ';', '?', '!', '\r', '\n');

    public MessageFilterImpl(WordFilter wordFilter) {
        this.wordFilter = wordFilter;
    }

    @Override
    public String filter(String inputString) {
        StringBuilder word = new StringBuilder();
        StringBuilder filterStringBuilder = new StringBuilder();
        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (!isWordSeparator(currentChar)) {
                word.append(currentChar);
            } else {
                filterStringBuilder.append(getFilterWord(word));
                filterStringBuilder.append(currentChar);
            }
        }
        filterStringBuilder.append(getFilterWord(word));
        return filterStringBuilder.toString();
    }

    private String getFilterWord(StringBuilder word){
        String result = "";
        if (word.length() != 0){
            result = wordFilter.filter(word.toString());
            word.setLength(0);
        }
        return result;
    }

    private boolean isWordSeparator(char c) {
        return wordSeparators.contains(c);
    }
}