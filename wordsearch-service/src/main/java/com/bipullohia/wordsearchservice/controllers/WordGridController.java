package com.bipullohia.wordsearchservice.controllers;

import com.bipullohia.wordsearchservice.service.WordGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController("/")
public class WordGridController {

    @Autowired
    WordGridService wordGridService;

    @GetMapping("/wordgrid")
    @CrossOrigin(origins = "http://localhost:1234")
    public String returnWordGrid(@RequestParam int gridSize, @RequestParam String wordList){
        List<String> words = Arrays.asList(wordList.split(","));
        char[][] gridContent = wordGridService.returnGridWithWords(gridSize, words);
        return wordGridService.getGridContentString(gridContent);
    }
}
