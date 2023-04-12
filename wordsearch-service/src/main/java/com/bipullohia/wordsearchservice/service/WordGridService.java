package com.bipullohia.wordsearchservice.service;

import com.bipullohia.wordsearchservice.model.Direction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WordGridService {

    //making an inner class for convenience (this can as well be an outer class in the model pkg)
    private class Coordinates {
        int x;
        int y;

        public Coordinates (int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Coordinates{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public char[][] returnGridWithWords(int gridSize, List<String> words){
        List<Coordinates> coordinates = new ArrayList<>();
        //initialize the grid with underscores ('_')
        char[][] gridContent = new char[gridSize][gridSize];
        for(int i=0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                gridContent[i][j] = '_';
                coordinates.add(new Coordinates(i,j));
            }
        }

        for(String word: words){
            Collections.shuffle(coordinates); //shuffles the list to randomize the coordinates
            for(Coordinates coordinate: coordinates){
                System.out.println("Word: " + word + "; coordinates: " + coordinate.toString());
                int x = coordinate.x;
                int y = coordinate.y;
                Direction direction = getDirectionForWordFit(gridContent, word, coordinate);
                System.out.println("direction: " + direction);
                if(direction != null){
                    switch(direction){
                        case HORIZONTAL:
                            for(char c : word.toCharArray()){
                                gridContent[x][y++] = c;
                            }
                            break;
                        case VERTICAL:
                            for(char c : word.toCharArray()){
                                gridContent[x++][y] = c;
                            }
                            break;
                        case DIAGONAL:
                            for(char c : word.toCharArray()){
                                gridContent[x++][y++] = c;
                            }
                            break;
                        case INVERSE_HORIZONTAL:
                            for(char c : word.toCharArray()){
                                gridContent[x][y--] = c;
                            }
                            break;
                        case INVERSE_VERTICAL:
                            for(char c : word.toCharArray()){
                                gridContent[x--][y] = c;
                            }
                            break;
                        case INVERSE_DIAGONAL:
                            for(char c : word.toCharArray()){
                                gridContent[x--][y--] = c;
                            }
                            break;
                    }
                    break;
                }
            }
        }
        gridContent = fillGridWithRandomChars(gridContent);
        return gridContent;
    }

    private Direction getDirectionForWordFit(char[][] gridContent, String word, Coordinates coordinate) {
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        for(Direction direction: directions){
            if(doesWordFit(gridContent, word, coordinate, direction)) return direction;
        }
        return null;
    }

    private boolean doesWordFit(char[][] gridContent, String word, Coordinates coordinate, Direction direction){
        int gridSize = gridContent[0].length;
        switch (direction){
            case HORIZONTAL:
                if(word.length()+coordinate.y > gridSize) return false;
                for(int i=0; i<word.length(); i++){
                    if(gridContent[coordinate.x][coordinate.y+i] != '_') return false;
                }
                break;
            case VERTICAL:
                if(word.length()+coordinate.x > gridSize) return false;
                for(int i=0; i<word.length(); i++){
                    if(gridContent[coordinate.x+i][coordinate.y] != '_') return false;
                }
                break;
            case DIAGONAL:
                if(word.length()+coordinate.x > gridSize || word.length()+coordinate.y > gridSize) return false;
                for(int i=0; i<word.length(); i++){
                    if(gridContent[coordinate.x+i][coordinate.y+i] != '_') return false;
                }
                break;
            case INVERSE_HORIZONTAL:
                if(coordinate.y-word.length() < 0) return false;
                for(int i=0; i<word.length(); i++){
                    if(gridContent[coordinate.x][coordinate.y-i] != '_') return false;
                }
                break;
            case INVERSE_VERTICAL:
                if(coordinate.x-word.length() < 0) return false;
                for(int i=0; i<word.length(); i++){
                    if(gridContent[coordinate.x-i][coordinate.y] != '_') return false;
                }
                break;
            case INVERSE_DIAGONAL:
                if(coordinate.x-word.length() < 0 || coordinate.y-word.length() < 0) return false;
                for(int i=0; i<word.length(); i++){
                    if(gridContent[coordinate.x-i][coordinate.y-i] != '_') return false;
                }
                break;
        }
        return true;
    }

    private char[][] fillGridWithRandomChars(char[][] gridContent){
        int gridSize = gridContent[0].length;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int randomIndex;
        for(int i=0; i<gridSize; i++){
            for(int j=0; j<gridSize; j++){
                if(gridContent[i][j] == '_'){
                    randomIndex = ThreadLocalRandom.current().nextInt(0, chars.length());
                    gridContent[i][j] = chars.charAt(randomIndex);
                }
            }
        }
        return gridContent;
    }

    public String printGridElements(char[][] gridContent){
        int gridSize = gridContent[0].length;
        StringBuilder gridResultString = new StringBuilder();
        for(int i=0; i<gridSize; i++){
            for(int j=0; j<gridSize; j++){
                gridResultString.append(gridContent[i][j]).append(" ");
            }
            gridResultString.append("\r\n");
        }
        return gridResultString.toString();
    }

}
