package com.team254.frc2013.subsystems;

import com.team254.frc2013.Constants;
import com.team254.frc2013.ShooterGains;
import com.team254.lib.control.ControlOutput;
import com.team254.lib.control.ControlSource;
import com.team254.lib.control.impl.FlywheelController;
import com.team254.lib.util.Debouncer;
import com.team254.lib.util.ThrottledPrinter;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Class representing the shooter wheels, managing its motors and sensors.
 *
 * @author richard@team254.com (Richard Lin)
 * @author tom@team254.com (Tom Bottiglieri)
 * @author eric.vanlare14@bcp.org (Eric van Lare)
 * @author eliwu26@gmail.com (Elias Wu)
 */
public class Shooter extends Subsystem {

  public static final int PRESET_BACK_PYRAMID = 0;
  public static final int PRESET_FRONT_PYRAMID = 1;
  public static final int SLOW_SHOOT = 2;
  private Talon frontMotor = new Talon(Constants.frontShooterPort.getInt());
  private Talon backMotor = new Talon(Constants.backShooterPort.getInt());
  private Solenoid loader = new Solenoid(Constants.shooterLoaderPort.getInt());
  private Solenoid angle = new Solenoid(Constants.shooterAnglePort.getInt());
  private Solenoid indexerLeft = new Solenoid(Constants.indexerLeftPort.getInt());
  private Solenoid indexerRight = new Solenoid(Constants.indexerRightPort.getInt());
  ThrottledPrinter p = new ThrottledPrinter(.1);
  public DigitalInput indexerDownSensorA =
          new DigitalInput(Constants.indexerDownSensorPortA.getInt());
  public DigitalInput indexerDownSensorB =
          new DigitalInput(Constants.indexerDownSensorPortB.getInt());
  private DigitalInput discSensor =
          new DigitalInput(Constants.discSensorPort.getInt());
  public Counter counter = new Counter(Constants.shootEncoderPort.getInt());
  Debouncer debounce = new Debouncer(.1);
  double goal = 0;

  private class ShooterSource implements ControlSource {

    public double get() {
      int kCountsPerRev = 1;
      double period = counter.getPeriod();
      double rpm = 60.0 / (period * (double) kCountsPerRev);
      lastRpm = rpm;
      onTarget = lastRpm > Constants.minShootRpm.getDouble();
      return (lastRpm * Math.PI * 2.0) / 60.0;
    }

    public void updateFilter() {

    }
  }

  private class ShooterOutput implements ControlOutput {
    public void set(double value) {
      if (value > speedLimit) {
        value = speedLimit;
      }
      frontMotor.set(-value);
      backMotor.set(-value);
      if (DriverStation.getInstance().isEnabled()) {
        System.out.println("D:" + Timer.getFPGATimestamp() + ", " + value + ", " + getRpm() + ":D");
      }
    }
  }

  FlywheelController controller = new FlywheelController("shooter", new ShooterOutput(), new ShooterSource(), ShooterGains.getGains()[0], 1.0/100.0);
  private double frontPower;
  private double backPower;
  private boolean shooterOn;
  boolean onTarget = false;
  public double lastRpm = 0;
  private Timer stateTimer = new Timer();
  double speedLimit = 15000;

  public void setIndexerUp(boolean up) {
    indexerLeft.set(!up);
    indexerRight.set(!up);
  }

  // Load a frisbee into shooter by retracting the piston
  public void retract() {
    loader.set(false);
  }

  // Shoot already loaded frisbee by extending the piston
  public void extend() {
    loader.set(true);
  }

  public void setHighAngle(boolean high) {
    angle.set(!high);
  }

  public boolean isHighAngle() {
    return angle.get();
  }

  public boolean getLoaderState() {
    return loader.get();
  }

  public boolean isIndexerDown() {
    // The sensor reads true when the indexer is down.
    return indexerLeft.get() && indexerRight.get();
  }

  public void setVelocityGoal(double rpm) {
    goal = rpm;
    controller.setVelocityGoal((goal * Math.PI * 2.0) / 60.0);
  }

  public boolean isIndexerLoaded() {
    return !discSensor.get();
  }

  public Shooter() {
    super();
    setPreset(PRESET_FRONT_PYRAMID);
    shooterOn = false;
    stateTimer.start();
    counter.start();
    controller.enable();
  }

  public void setPreset(int preset) {
    switch (preset) {
      case PRESET_FRONT_PYRAMID:
        setHighAngle(true);
        setVelocityGoal(Constants.shootRpm.getDouble());
        break;
      case SLOW_SHOOT:
        //setHighAngle(true);
        setVelocityGoal(2000);
        break;
      case PRESET_BACK_PYRAMID:
      default:
        setHighAngle(false);
        setVelocityGoal(Constants.shootRpm.getDouble());
    }
  }

  public void setSpeedLimit(double limit) {
    speedLimit = limit;
  }

  private void setPowers(double frontPower, double backPower) {
  //  this.frontPower = (frontPower < 0) ? 0 : (frontPower > speedLimit) ? speedLimit : frontPower;
  //  this.backPower = (backPower < 0) ? 0 : (backPower > speedLimit) ? speedLimit : backPower;
  //  setShooterOn(shooterOn);
  }

  public void setShooterOn(boolean isOn) {
    shooterOn = isOn;
    if (isOn) {
      controller.enable();
     // setVelocityGoalRaw(goal);
    } else {
    //  setVelocityGoalRaw(0);
      controller.disable();
      frontMotor.set(0);
      backMotor.set(0);
      lastRpm = 0;
    }
  }

  protected void initDefaultCommand() {
  }
  public double getRpm() {
    return lastRpm;
  }

  public boolean onSpeedTarget() {
    return onTarget;
  }

  public boolean isOn() {
    return shooterOn;
  }

  public double getCurrentTalon() {
    return frontMotor.get();
  }
}
