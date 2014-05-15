package virophage.render;

import virophage.core.Channel;
import virophage.core.Location;
import virophage.core.Virus;
import virophage.gui.GameClient;
import virophage.util.HexagonConstants;
import virophage.util.Vector;

import java.awt.*;

public class ChannelNode extends RenderNode {

    private Channel channel;

    public ChannelNode(Channel channel) {
        this.channel = channel;
    }

    @Override
    public Vector getPosition() {
        Vector from = channel.from.asCoordinates().add(new Vector(
                HexagonConstants.RADIUS,
                HexagonConstants.TRI_HEIGHT));
        //Vector rot = new Vector(HexagonConstants.TRI_HEIGHT, 0).rotate(channel.from.getDirectionTowards(channel.to));
        //rot = new Vector(rot.x, rot.y * -1);
        //Vector vec = from.add(rot);
        return from;
    }

    @Override
    public Shape getCollision() {
        return new Rectangle();
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public void render(Graphics2D g) {

        // loop caluclations
        int rad = (int) (HexagonConstants.RADIUS * 2 * 0.7);
        int pos = rad / -2;
        Vector endpoint = new Vector(HexagonConstants.TRI_HEIGHT * 2, 0)
                .rotate(channel.from.getDirectionTowards(channel.to));
        double mult = (rad / 2 + 5) / endpoint.magnitude();
        Vector startpoint = endpoint.scale(mult);

        // draw line
        g.setColor(channel.player.getColor().darker().darker());
        g.setStroke(new BasicStroke(10));
        g.drawLine((int) startpoint.x, (int) startpoint.y, (int) endpoint.x, (int) endpoint.y);

        // draw loop
        g.setColor(channel.player.getColor().darker().darker().darker().darker());
        g.setStroke(new BasicStroke(3));
        g.drawOval(pos, pos, rad, rad);

        // draw endpoint
        g.setColor(channel.player.getColor().darker().darker());
        g.setStroke(new BasicStroke());
        if(channel.hasVirus()) {
            Virus v = channel.virus;
            double circleRadius = (v.getEnergy() / (double) GameClient.MAX_ENERGY) *
                    (HexagonConstants.RADIUS * 0.7);
            int circleDiameter = (int) (circleRadius * 2);
            int x = (int) (endpoint.x - circleRadius);
            int y = (int) (endpoint.y - circleRadius);
            g.fillOval(x, y, circleDiameter, circleDiameter);
        }
    }

}
