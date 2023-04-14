import { Grid } from "./grid";

const submitWordButton = document.querySelector(".submit-words");
// const GRID_SIZE = 10;


submitWordButton.onclick = function(){
    fetchWordGridFromService();
}

async function fetchWordGridFromService(){
    const grid = new Grid();
    const wordListByUser = document.getElementById("grid-words").value;
    const gridSizeByUser = document.getElementById("grid-size").value;
    
    grid.words = wordListByUser.split(",");
    const host = 'http://localhost:8090/wordgrid';
    const uri = `${host}?gridSize=${gridSizeByUser}&wordList=${wordListByUser}`;
    
    let response = await fetch(uri);
    let wordGridContent = await response.text();
    let wordGridArray = wordGridContent.split(" ");
    
    //filtering the elements which are empty
    wordGridArray = wordGridArray.filter((e) => {
        return e != "";
    });
    
    grid.renderGrid(gridSizeByUser, wordGridArray);
    const userInputWordlistSectionNode = document.createTextNode(grid.words);
    let wordInputDisplaySection = document.querySelector(".user-input-wordlist");
    if(wordInputDisplaySection.lastChild){
        wordInputDisplaySection.removeChild(wordInputDisplaySection.lastChild);
    }
    wordInputDisplaySection.appendChild(userInputWordlistSectionNode);
    
}