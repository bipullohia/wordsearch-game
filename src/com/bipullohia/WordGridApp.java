package com.bipullohia;

import java.util.Arrays;
import java.util.List;

public class WordGridApp {

    public static void main(String[] args){
        List<String> words = Arrays.asList("HELLO", "GUYS", "TO", "THE", "EXPERIMENT");
        Grid grid = new Grid(12);
        grid.populateGridWithWords(words);
        grid.printGridElements();
    }

}
