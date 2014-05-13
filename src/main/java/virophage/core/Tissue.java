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
    	//location x && y can be negative! Should convert?
        return cells[cells.length / 2 - loc.y][cells[0].length / 2 - loc.x];
    }

}
