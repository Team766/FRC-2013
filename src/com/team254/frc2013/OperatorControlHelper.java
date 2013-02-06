package com.team254.frc2013;

import com.team254.frc2013.commands.ShooterSpeedCommand;

/**
 * Maps operator control buttons to a specified command.
 *
 * @author tom@team254.com (Tom Bottiglieri)
 */
public class OperatorControlHelper {
  
  public static void setupOperationMap(ControlBoard c) {
    c.gamepad.getButtonA().whenPressed(new ShooterSpeedCommand(0));
    c.gamepad.getButtonX().whenPressed(new ShooterSpeedCommand(1500));
    c.gamepad.getButtonY().whenPressed(new ShooterSpeedCommand(2500));
    c.gamepad.getButtonB().whenPressed(new ShooterSpeedCommand(3500));
  }
}