package com.team254.frc2013;

import com.team254.lib.util.Gamepad;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Contains the input devices making up the driver control board.
 *
 * @author richard@team254.com (Richard Lin)
*/
public class ControlBoard {
  public Joystick leftStick = new Joystick(Constants.leftJoystickPort.getInt());
  public Joystick rightStick = new Joystick(Constants.rightJoystickPort.getInt());
  public Gamepad gamepad = new Gamepad(Constants.gamepadPort.getInt());
  
  public boolean getQuickTurn() {
    return rightStick.getRawButton(2);
  }
  
  public boolean getHighGear() {
    return !rightStick.getRawButton(3);
  }
}
