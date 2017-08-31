package com.example.springcloudsentence.domain;

import java.util.Map;
import java.util.TreeMap;

/**
 * Model of a Sentence, composed of Words.
 *
 * @author Ken Krueger, Jorge Centeno Fernandez
 */
public class Sentence {

    // ordered by role so to make easier print the sentence
    private final Map<Role, String> words = new TreeMap<>();

    public void add(Role role, String word) {
        words.put(role, word);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Role role : words.keySet()) {
            sb.append(words.get(role)).append(' ');
        }
        return sb.toString();
    }

    public static enum Role {
        subject, verb, article, adjective, noun;
    }
}


