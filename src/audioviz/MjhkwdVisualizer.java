package audioviz;

import static java.lang.Integer.min;
import java.util.Arrays;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author Matthew Hess
 */

public class MjhkwdVisualizer implements Visualizer
{
    private String name = "MjhkwdVisualizer";
    
    private Integer numBands;
    private AnchorPane vizPane;
    
    private Double baseLine;
    
    private Double width = 0.0;
    private Double height = 0.0;
    
    private Double bandWidth = 0.0;
    private Line[] lines;
    
    public MjhkwdVisualizer()
    {
    }
    
    @Override
    public String getName()
    {
        return name;
    }
    
    @Override
    public void start(Integer numBands, AnchorPane vizPane)
    {
        end();
        
        this.numBands = numBands;
        this.vizPane = vizPane;
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        bandWidth = width / numBands;
        baseLine = height/1.15;
        
        lines = new Line[numBands];
        
        for (int i = 0; i < numBands; i++)
        {
            Line line = new Line();
            line.setStartX(i*bandWidth);
            line.setStartY(baseLine);
            line.setEndX((i+1)*bandWidth);
            line.setEndY(baseLine);
            vizPane.getChildren().add(line);
            lines[i] = line;
            
        }

    }
    
    @Override
    public void end()
    {
        if (lines != null)
        {
           for (int i = 0; i < lines.length; i++)
           {
               vizPane.getChildren().remove(lines[i]);
           }
           lines = null;
        } 
    }
    
    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases)
    {
        if (lines == null)
        {
            return;
        }
        
        Integer num = min(lines.length, magnitudes.length);
        
        for (int i = 0; i < num; i++)
        {
            if(i==0)
            {
                lines[i].setStartY(baseLine);
                lines[i].setEndY((-1) * ((magnitudes[i]+60)*8)+baseLine);
            }
            else
            {
                lines[i].setStartY((-1) * ((magnitudes[i-1]+60)*8)+baseLine);
                lines[i].setEndY((-1) * ((magnitudes[i]+60)*8)+baseLine);
            }
        }
    }
}
