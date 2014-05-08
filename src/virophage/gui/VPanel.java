package virophage.gui;

import javax.swing.*;
import java.awt.*;

public class VPanel extends JPanel {

    public VPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        add(new RenderTissue(), BorderLayout.CENTER);
    }

}
