package com.bipullohia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Grid {

    private int gridSize;
    char[][] gridContent;
    List<String> ignoredWords = new ArrayList<>();
    List<Coordinates> coordinates = new ArrayList<>();

    private enum Direction {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL,
        INVERSE_HORIZONTAL,
        INVERSE_VERTICAL,
        INVERSE_DIAGONAL
    }

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

    public Grid (int gridSize){
        this.gridSize = gridSize;
        gridContent = new char[gridSize][gridSize];
        //initializing the grid with '_'
        for(int i=0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                gridContent[i][j] = '_';
                coordinates.add(new Coordinates(i,j));
            }
        }
    }

    public void populateGridWithWords(List<String> words){
        for(String word: words){
            Collections.shuffle(coordinates); //shuffles the list to randomize the coordinates
            for(Coordinates coordinate: coordinates){
                System.out.println("Word: " + word + "; coordinates: " + coordinate.toString());
                int x = coordinate.x;
                int y = coordinate.y;
                Direction direction = getDirectionForWordFit(word, coordinate);
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
        fillGridWithRandomChars();
    }

    private Direction getDirectionForWordFit(String word, Coordinates coordinate) {
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        for(Direction direction: directions){
            if(doesWordFit(word, coordinate, direction)) return direction;
        }
        return null;
    }

    private boolean doesWordFit(String word, Coordinates coordinate, Direction direction){
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

    public void printGridElements(){
        for(int i=0; i<gridSize; i++){
            for(int j=0; j<gridSize; j++){
                System.out.print(gridContent[i][j] + " ");
            }
            System.out.println("");
        }
        if(ignoredWords.size()>0){
            System.out.println("Ignored Words from Input: " + ignoredWords.toString());
        }
    }

    private void fillGridWithRandomChars(){
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
    }


}
