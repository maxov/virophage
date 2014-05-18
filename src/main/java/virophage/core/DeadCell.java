package virophage.core;

/**
 * A <code>DeadCell</code> is a space that no virus can "infect".
 *
 * @author Leon Ren
 * @since 2014-05-6
 */
public class DeadCell extends Cell {

	/**
	 * Constructs a dead cell.
	 */
    public DeadCell(Tissue tissue) {
        super(tissue, null);
    }

}
