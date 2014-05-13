package virophage.core;

public class Tissue {

    private Cell[][] cells;

    public Tissue(Cell[][] start) {
        cells = start;

        // Initialize cells
        /*for(Cell[] arr: cells){
            for(int i = 0; i < arr.length; i++) {
                Cell c = arr[i];
                if(c == null) {
                    arr[i] = new Cell();
                }
            }
        }*/
        
    }

    public Cell getCell(Location loc) {
        return cells[loc.y + cells.length / 2][loc.x + cells[0].length / 2];
    }

}
