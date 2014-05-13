package virophage.render;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;

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
    
    public Shape getCollision() {
    	return null;
    }
    
    public void onClick(MouseEvent e) {
    	
    }
    
}
