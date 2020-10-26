package be.uhasselt.oo2.mvc.app.clock;

import be.uhasselt.oo2.mvc.AbstractView;
import be.uhasselt.oo2.mvc.Controller;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author jvermeulen
 */
public class ClockTools extends AbstractView {
    // The user interface for this view.
    private JPanel mTools;
    private JButton mStart;
    private JButton mStop;
    private JButton mReset;

    //added
    private JButton mSync;
    private JButton mSettime;
    
    public ClockTools(Observable model, ClockController controller) {
        super(model, controller);
        init();
    }

    private void init() {
        mTools = new JPanel();
        mTools.setLayout(new GridLayout(1, 3));
        mStart = new JButton("Start");
        mStart.setEnabled(false);
        mStop = new JButton("Stop");
        mStop.setEnabled(false);
        mReset = new JButton("Reset");

        //added
        mSync = new JButton("sync");
        mSettime = new JButton("set time");

        
        // handle events: pass to controller
        mStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ((ClockController)getController()).onStart();
            }
        });
        
        mStop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ((ClockController)getController()).onStop();
            }
        });
                
        mReset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ((ClockController)getController()).onReset();
            }
        });

        //added
        mSync.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((ClockController)getController()).onSync();
            }
        });

        //added
        mSettime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { ((ClockController)getController()).onSetTime(); }
        });
        
        mTools.add(mStart);
        mTools.add(mStop);
        mTools.add(mReset);

        //added
        mTools.add(mSync);
        mTools.add(mSettime);
    }
    
        /**
     * Updates the state of the clock tools.
     * Invoked automatically by ClockModel.
     * @param o The ClockModel object that is broadcasting an update
     * @param info A ClockUpdate instance describing the changes that have 
     * occurred in the ClockModel
     */
    @Override
    public void update(Observable o, Object info) {    
        // Cast info to ClockUpdate type.
        ClockUpdate clockInfo = (ClockUpdate) info;
        
        if (clockInfo.isRunning()) {
            mStop.setEnabled(true);
            mStart.setEnabled(false);
        } else {
            mStart.setEnabled(true);
            mStop.setEnabled(false);
        }
    }
    
    @Override
    public Controller defaultController(Observable model) {
        return new ClockController(model);        
    }
    
    /**
     * Convenience method to return the user interface component. We don't need 
     * this if we implement View directly and directly subclass a GUI component.
     * @return 
     */
    public JComponent getUI() {
        return mTools;
    }
}
