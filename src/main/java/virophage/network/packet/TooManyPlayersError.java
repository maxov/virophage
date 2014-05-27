package virophage.network.packet;

/**
 * Too many players!
 *
 */
public class TooManyPlayersError extends PlayerError {

    /**
     *
     *
     * @param error string error
     */
    public TooManyPlayersError(String error) {
        super(error);
    }

}
