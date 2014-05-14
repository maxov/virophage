package virophage.render;

import virophage.util.Vector;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class RenderNode {

    private RenderTree renderTree;

    public abstract Vector getPosition();

    public abstract Shape getCollision();

    public abstract void render(Graphics2D g);

    public void onClick(MouseEvent e) {
    }

    public RenderTree getRenderTree() {
        return renderTree;
    }

    public void setRenderTree(RenderTree renderTree) {
        this.renderTree = renderTree;
    }
}
