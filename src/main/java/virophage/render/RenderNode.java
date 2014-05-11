package virophage.render;

import javax.swing.*;
import java.awt.*;

public class RenderNode extends JComponent {

    private RenderTree renderTree;

    public RenderTree getRenderTree() {
        return renderTree;
    }
    public void setRenderTree(RenderTree renderTree) {
        this.renderTree = renderTree;
    }

    public Point getPreferredPosition() {
        return null;
    }
}
