export class Grid {

    constructor() {
        this.wordSelectedMode = false;
        this.selectedItems = [];
    }

    renderGrid(gridSize, gridContent) {
        var gridSection = document.getElementsByClassName("grid-section")[0];
        if(gridSection.lastChild){
            gridSection.removeChild(gridSection.lastChild);
        }


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
        gridSection.addEventListener("mousedown", (e) => {
            this.wordSelectedMode = true;
        });

        gridSection.addEventListener("mousemove", (e) => {
            if(this.wordSelectedMode){
                let cell = e.target;
                cell.classList.add("selected");
                let x = cell.getAttribute('data-x');
                let y = cell.getAttribute('data-y');
                let letter = cell.getAttribute('data-letter');

                if(this.selectedItems.length>1){
                    const lastItem = this.selectedItems[this.selectedItems.length-1];
                    if(lastItem.x === x && lastItem.y === y) return; // to check if the mouse hasn't moved (i.e., the new item is same as last item, don't push the item)
                }

                //pushing the object with all the data in the array
                this.selectedItems.push({
                    x, y, letter, cell
                });
                console.log(this.selectedItems);
            }
        });

        gridSection.addEventListener("mouseup", () => {
            this.wordSelectedMode = false;
            this.selectedItems.forEach(item => {
                item.cell.classList.remove("selected"); //the e will contain the entire object with x, y, letter and cell
            });
        });
    }

}