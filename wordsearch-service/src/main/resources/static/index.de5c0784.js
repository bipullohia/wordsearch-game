const submitWordButton = document.querySelector(".submit-words");
const GRID_SIZE = 10;
submitWordButton.onclick = function() {
    fetchWordGridFromService();
};
async function fetchWordGridFromService() {
    const words = "ONE, TWO, THREE";
    const uri = `http://localhost:8090/wordgrid?gridSize=${GRID_SIZE}&wordList=${words}`;
    let response = await fetch(uri);
    let jsonData = await response.text();
    console.log(jsonData);
}

//# sourceMappingURL=index.de5c0784.js.map
