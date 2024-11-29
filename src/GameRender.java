import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameRender class is responsible for rendering game objects on a JPanel.
 * It manages a list of {@link Displayable} objects to be drawn and implements
 * the {@link Engine} interface to update the rendering process.
 */
public class GameRender extends JPanel implements Engine {
    private List<Displayable> renderList; // List of objects to be rendered.

    /**
     * Constructs a GameRender instance with an empty list of displayable objects.
     */
    public GameRender() {
        this.renderList = new ArrayList<>();
    }

    /**
     * Sets the list of objects to be rendered.
     *
     * @param renderList a list of {@link Displayable} objects to render.
     */
    public void setRenderList(List<Displayable> renderList) {
        this.renderList = renderList;
    }

    /**
     * Adds a single {@link Displayable} object to the render list.
     *
     * @param displayable the object to be added to the render list.
     */
    public void addToRenderList(Displayable displayable) {
        renderList.add(displayable);
    }

    /**
     * Updates the rendering process by repainting the panel.
     * This method is part of the {@link Engine} interface implementation.
     */
    @Override
    public void update() {
        repaint(); // Triggers a call to the paint method to refresh the display.
    }

    /**
     * Paints all the objects in the render list onto the JPanel.
     *
     * @param g the {@link Graphics} object used to draw the render list.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g); // Clears the panel and prepares it for rendering.
        for (Displayable displayable : renderList) {
            displayable.draw(g); // Calls the draw method of each displayable object.
        }
    }
}
