export class Grid {

    constructor() {
        this.wordSelectedMode = false;
        this.selectedItems = [];
        this.firstSelectedItem;
        this.gridSection;
        this.words = [];
        this.foundWords = [];
    }

    getCellsInRange(firstLetter, currentLetter) {
        let cellsInRange = [];
        //swapping the letters if it is inverse horizontal/vertical/diagonal
        if(firstLetter.x > currentLetter.x || firstLetter.y > currentLetter.y) {
            [firstLetter, currentLetter] = [currentLetter, firstLetter];
        }

        //horizontal selection
        if(firstLetter.x === currentLetter.x) {
            for(let i = firstLetter.y; i <= currentLetter.y; i++){
                cellsInRange.push(this.gridSection.querySelector(`td[data-x="${currentLetter.x}"][data-y="${i}"]`));
            }
        } else if(firstLetter.y === currentLetter.y) { //vertical selection
            for(let i = firstLetter.x; i <= currentLetter.x; i++){
                cellsInRange.push(this.gridSection.querySelector(`td[data-x="${i}"][data-y="${currentLetter.y}"]`));
            }
        } else if(firstLetter.y - currentLetter.y === firstLetter.x - currentLetter.x) { //diagonal selection
            let diff = currentLetter.y - firstLetter.y;
            for(let i = 0; i <= diff; i++){
                cellsInRange.push(this.gridSection.querySelector(`td[data-x="${firstLetter.x + i}"][data-y="${firstLetter.y + i}"]`));
            }
        }

        return cellsInRange;
    }

    renderGrid(gridSize, gridContent) {
        var gridSection = document.getElementsByClassName("grid-section")[0];
        //to remove the old table grid when before we generate a new table for word grid
        if(gridSection.lastChild){
            gridSection.removeChild(gridSection.lastChild);
        }
        this.gridSection = gridSection;
        const tbl = document.createElement("table");
        const tblBody = document.createElement("tbody");
        let index = 0;

        for (let i = 0; i < gridSize; i++) {
            const row = document.createElement("tr");
            for (let j = 0; j < gridSize; j++) {
                let letter = gridContent[index++];
                const cell = document.createElement("td");
                const cellText = document.createTextNode(letter);
                cell.appendChild(cellText);
                cell.setAttribute('data-x', i);
                cell.setAttribute('data-y', j);
                cell.setAttribute('data-letter', letter);
                row.appendChild(cell);
            }
            tblBody.appendChild(row);
        }
        tbl.appendChild(tblBody);
        document.body.appendChild(tbl);
        gridSection.appendChild(tbl);


        //event handlers for mouseup/mousedown/mousemove
        tbl.addEventListener("mousedown", (e) => {
            this.wordSelectedMode = true;
            let cell = e.target;
            let x = +cell.getAttribute('data-x');
            let y = +cell.getAttribute('data-y');
            this.firstSelectedItem = ({
                x, y
            });  
        });

        tbl.addEventListener("mousemove", (e) => {
            if(this.wordSelectedMode){
                let cell = e.target;
                let x = +cell.getAttribute('data-x');
                let y = +cell.getAttribute('data-y');
                this.selectedItems.forEach(cell => cell.classList.remove("selected"));
                this.selectedItems = this.getCellsInRange(this.firstSelectedItem, {x,y});
                this.selectedItems.forEach(cell => cell.classList.add("selected"));
            }
        });

        tbl.addEventListener("mouseup", () => {
            this.wordSelectedMode = false;
            const selectedWord = this.selectedItems.reduce((word, cell) => word += cell.getAttribute("data-letter"), '');
            const inverseSelectedWord = selectedWord.split("").reverse().join("");
            if(this.words.indexOf(selectedWord) !== -1){
                this.foundWords.push(selectedWord);
            }else if(this.words.indexOf(inverseSelectedWord) !== -1){
                this.foundWords.push(inverseSelectedWord);
            }else {
                this.selectedItems.forEach(cell => {
                    cell.classList.remove("selected"); //the e will contain the entire object with x, y, letter and cell
                });
            }
            this.selectedItems = [];
        });
    }

}