package virophage.game;

public class Tissue {

    private Cell[][] cells;

    public Tissue(Cell[][] start) {
        cells = start;
        for(Cell[] arr: cells){
            for(int i = 0; i < arr.length; i++) {
                Cell c = arr[i];
                if(c == null) {
                    arr[i] = new Cell();
                }
            }
        }
    }

}
