package virophage.game;

public class Game {

    public void nun() {
        EventDispatcher e = new EventDispatcher();
        e.register("abc", new EventListener<Object>() {
            @Override
            public void onEvent(Object evt) {
                EventDispatcher.X e = (EventDispatcher.X) evt;
                e.v();
            }
        });
    }

}
