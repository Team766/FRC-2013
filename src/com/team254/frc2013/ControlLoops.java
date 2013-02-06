package com.team254.frc2013;

import com.team254.lib.control.Controller;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Updates all controllers, while operating on a given period.
 *
 * @author richard@team254.com (Richard Lin)
 */
public class ControlLoops {
  Timer controlLoop;
  double period;
  protected boolean enabled = true;
  
  private class ControllerTask extends TimerTask {
    private ControlLoops loop;
    
    public ControllerTask(ControlLoops loop) {
      if (loop == null) {
        throw new NullPointerException("Given ControlLoops was null");
      }
      this.loop = loop;
    }
    
    public void run() {
      loop.update();
    }
  }
  
  public ControlLoops(double period) {
    controlLoop = new Timer();
    this.period = period;
    controlLoop.schedule(new ControllerTask(this), 0L, (long) (this.period * 1000));
  }
  
  public void update() {
    Controller.updateAll();
  }
}