package be.uhasselt.oo2.mvc.app.clock;

import be.uhasselt.oo2.mvc.AbstractController;
import java.util.Observable;
import java.util.*;

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
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        ((ClockModel)getModel()).setTime(calendar.get(calendar.HOUR), calendar.get(calendar.MINUTE), calendar.get(calendar.SECOND));
    }

    //added
    public void onSetTime(){
        TimeSetter window = new TimeSetter(this);
        window.show();
    }

    //added
    public void onSubmit(int hours, int mins, int secs){
        ((ClockModel)getModel()).setTime(hours, mins, secs);
    }
}
