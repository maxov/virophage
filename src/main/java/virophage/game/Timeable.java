package virophage.game;

/**
*
* @author Max Ovsiankin
* @since 2014-05-16
*/
public interface Timeable {

    public abstract void act(int tick);
    public abstract boolean shouldAct(int tick);

}
