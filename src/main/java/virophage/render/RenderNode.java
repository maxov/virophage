package virophage.render;

import virophage.util.Vector;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A <code>RenderNode</code> is a componet of a RenderTree to assist in scaling/panning.
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public abstract class RenderNode implements Comparable<RenderNode> {


    private RenderTree renderTree;

    public abstract Vector getPosition();

    public abstract Shape getCollision();



    public abstract void render(Graphics2D g);

    public void onClick(MouseEvent e) {
    }

    public int getPriority() {
        return 0;
    }

    @Override
    public int compareTo(RenderNode that) {
        return new Integer(getPriority()).compareTo(that.getPriority());
    }

    public RenderTree getRenderTree() {
        return renderTree;
    }

    public void setRenderTree(RenderTree renderTree) {
        this.renderTree = renderTree;
    }
}
