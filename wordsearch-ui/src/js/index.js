import { Grid } from "./grid";

const submitWordButton = document.querySelector(".submit-words");
const GRID_SIZE = 10;
const grid = new Grid();

submitWordButton.onclick = function(){
    fetchWordGridFromService();
}

async function fetchWordGridFromService(){
    const words = "ONE,TWO,THREE";
    const host = 'http://localhost:8090/wordgrid';
    const uri = `${host}?gridSize=${GRID_SIZE}&wordList=${words}`;
    
    let response = await fetch(uri);
    let wordGridContent = await response.text();
    let wordGridArray = wordGridContent.split(" ");
    
    //filtering the elements which are empty
    wordGridArray = wordGridArray.filter((e) => {
        return e != "";
    });
    
    console.log(wordGridArray);
    grid.renderGrid(GRID_SIZE, wordGridArray);
}

function cleanWordGridArray(wordGridArray){
    for(let e in wordGridArray){
        console.log(wordGridArray[e]);
    }
}