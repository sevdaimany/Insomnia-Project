import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import java.awt.*;

public class Insomnia extends SwingWorker{ 
  
    
   
    public Integer doInBackground()
    {
        Jurl.main(connectionClass.getArgs());
        return 0;
    }

    protected void done()
    {
        connectionClass.runGUi();
    }
}