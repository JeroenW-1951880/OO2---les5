package be.uhasselt.oo2.mvc.app.clock;

import be.uhasselt.oo2.mvc.AbstractView;
import be.uhasselt.oo2.mvc.Controller;
import be.uhasselt.oo2.mvc.AbstractController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TimeSetter extends AbstractView {
    JFrame mWindow;
    JSpinner mHourChooser;
    JSpinner mMinuteChooser;
    JSpinner mSecondChooser;
    JButton mSubmit;

    TimeSetter(ClockController controller){
        super(null, controller);
        mHourChooser = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
        mHourChooser.setSize(50,50);
        mMinuteChooser = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        mMinuteChooser.setSize(50,50);
        mSecondChooser = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        mSecondChooser.setSize(50,50);

        mSubmit = new JButton("submit");

        mWindow = new JFrame();
        mWindow.setLayout(new GridLayout(2, 3));
        mWindow.setSize(400, 200);

        mWindow.add(mHourChooser);
        mWindow.add(mMinuteChooser);
        mWindow.add(mSecondChooser);
        mWindow.add(mSubmit);

        mSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { ((ClockController)getController()).onSubmit((int)mHourChooser.getValue(), (int)mMinuteChooser.getValue(), (int)mSecondChooser.getValue());
            }
        });
    }

    public void show(){
        mWindow.setVisible(true);
    }
}
