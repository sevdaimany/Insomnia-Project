import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import java.awt.*;

public class Insomnia extends SwingWorker{ 
    // String[] args;
    // JPanel panelWest_Center;
    // JPanel panelMessageBodyCenter;
    // JPanel panelHeaderEast;
    // CardLayout card;
    // JLabel label1;
    // JLabel label3 ;
    // JLabel label2;
    // long startTime;
    
        // public Insomnia(String[] args, JPanel panelWest_Center, JPanel panelMessageBodyCenter, JPanel panelHeaderEast,
        //         CardLayout card, JLabel label1, JLabel label3, JLabel label2, long startTime) {
        //     this.args = args;
        //     this.panelWest_Center = panelWest_Center;
        //     this.panelMessageBodyCenter = panelMessageBodyCenter;
        //     this.panelHeaderEast = panelHeaderEast;
        //     this.card = card;
        //     this.label1 = label1;
        //     this.label3 = label3;
        //     this.label2 = label2;
        //     this.startTime = startTime;
        // }
    
   
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