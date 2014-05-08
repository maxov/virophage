package virophage.game;

public class Player {

    public int excessEnergy = 0;

    public void giveEnergy(int energy) {
        this.excessEnergy += energy;
    }

}
