package virophage;

import virophage.core.*;
import virophage.game.Game;
import virophage.game.ServerGame;
import virophage.util.Location;

import java.io.*;

public class SerializeTest {

    public static Tissue serialize(Tissue tissue) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(tissue);
            out.flush();
            ByteArrayInputStream bios = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bios);
            Tissue tis = (Tissue) in.readObject();
            System.out.println(tis);
            return tis;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws Exception {


    }

}
