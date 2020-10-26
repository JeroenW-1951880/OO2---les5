package be.uhasselt.oo2.mvc.app.clock;

import be.uhasselt.oo2.mvc.AbstractController;
import java.util.Observable;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

/**
 * A clock controller that allows the clock to be started, stopped and reset.
 * @author jvermeulen
 */
public class ClockController extends AbstractController {
    public ClockController(Observable model) {
        super(model);
    }
    
    public void onStart() {
        ((ClockModel)getModel()).start();
    }
    
    public void onStop() {
        ((ClockModel)getModel()).stop();
    }
    
    public void onReset() {
        ((ClockModel)getModel()).setTime(0,0,0);
    }

    //added
    public void onSync() {
        LocalTime tm = LocalTime.now();
        ((ClockModel)getModel()).setTime(tm.getHour(), tm.getMinute(), tm.getSecond());
    }
}
