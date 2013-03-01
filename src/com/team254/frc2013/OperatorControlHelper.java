package com.team254.frc2013;

import com.team254.frc2013.commands.ConveyorSpeedCommand;
import com.team254.frc2013.commands.HangerHookCommand;
import com.team254.frc2013.commands.IndexerCommand;
import com.team254.frc2013.commands.RunIntakeCommand;
import com.team254.frc2013.commands.ShootCommand;
import com.team254.frc2013.commands.ShooterOnCommand;
import com.team254.frc2013.commands.ShooterPresetCommand;

/**
 * Maps operator control buttons to a specified command.
 *
 * @author tom@team254.com (Tom Bottiglieri)
 */
public class OperatorControlHelper {
  public static void setupOperationMap(ControlBoard c) {
    //c.operatorJoystick.getBackPyramidButton().whenPressed(new SetIntakePositionCommand(105));
    //c.operatorJoystick.getFrontPyramidButton().whenPressed(new SetIntakePositionCommand(80));
    //c.operatorJoystick.getCloseButton().whenPressed(new SetIntakePositionCommand(45));
    //c.operatorJoystick.getShootButton().whenPressed(new SetIntakePositionCommand(0));

    c.operatorJoystick.getIndexButton().whenPressed(new IndexerCommand(false));
    c.operatorJoystick.getIndexButton().whenReleased(new IndexerCommand(true));

    c.operatorJoystick.getBackPyramidButton().whenPressed(new ShooterPresetCommand(13000, false));
    c.operatorJoystick.getFrontPyramidButton().whenPressed(new ShooterPresetCommand(13000, true));
    c.operatorJoystick.getShootButton().whenPressed(new ShootCommand());

    c.operatorJoystick.getIntakeOutButton().whenPressed(new RunIntakeCommand(-1.0));
    c.operatorJoystick.getIntakeOutButton().whenReleased(new RunIntakeCommand(0.0));
    c.operatorJoystick.getIntakeButton().whenPressed(new RunIntakeCommand(1.0));
    c.operatorJoystick.getIntakeButton().whenReleased(new RunIntakeCommand(0.0));

    c.operatorJoystick.getAutonSelectButton().whenPressed(new HangerHookCommand(true));
    c.operatorJoystick.getAutonSelectButton().whenReleased(new HangerHookCommand(false));

    c.operatorJoystick.getShooterOnSwitch().whenPressed(new ShooterOnCommand(true));
    c.operatorJoystick.getShooterOnSwitch().whenReleased(new ShooterOnCommand(false));
  }
}
