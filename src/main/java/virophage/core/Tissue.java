package virophage.core;

import virophage.render.RenderTree;

public class Tissue {

    private Cell[][] cells;
    private RenderTree tree;

    public Tissue(Cell[][] start, RenderTree t) {
        cells = start;
        tree = t;

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

    public void setCell(Location loc, Cell c) {
    	cells[loc.y + cells.length / 2][loc.x + cells[0].length / 2] = c;
    }
}
