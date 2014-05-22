package virophage.game;

public interface Timeable {

    public abstract void act(int tick);
    public abstract boolean shouldAct(int tick);

}
