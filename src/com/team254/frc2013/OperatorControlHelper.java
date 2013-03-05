package com.team254.frc2013;

import com.team254.frc2013.commands.ContinuousShootCommand;
import com.team254.frc2013.commands.HangerHookCommand;
import com.team254.frc2013.commands.IndexerCommand;
import com.team254.frc2013.commands.IntakeRaiseCommand;
import com.team254.frc2013.commands.RunIntakeCommand;
import com.team254.frc2013.commands.ShootCommand;
import com.team254.frc2013.commands.ShooterOnCommand;
import com.team254.frc2013.commands.ShooterPresetCommand;
import com.team254.frc2013.subsystems.Hanger;
import com.team254.frc2013.subsystems.Shooter;

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

    c.operatorJoystick.intakeUpSwitch.whenPressed(
        new IntakeRaiseCommand(IntakeRaiseCommand.INTAKE_UP));
    c.operatorJoystick.intakeUpSwitch.whenReleased(
        new IntakeRaiseCommand(IntakeRaiseCommand.INTAKE_OFF));
    c.operatorJoystick.intakeDownSwitch.whenPressed(
        new IntakeRaiseCommand(IntakeRaiseCommand.INTAKE_DOWN));

    c.operatorJoystick.getIndexButton().whenPressed(new IndexerCommand(false));
    c.operatorJoystick.getIndexButton().whenReleased(new IndexerCommand(true));

    c.operatorJoystick.getBackPyramidButton().whenPressed(
        new ShooterPresetCommand(Shooter.PRESET_BACK_PYRAMID));
    c.operatorJoystick.getFrontPyramidButton().whenPressed(
        new ShooterPresetCommand(Shooter.PRESET_FRONT_PYRAMID));
    c.operatorJoystick.getPyramidGoalButton().whenPressed(
        new ShooterPresetCommand(Shooter.PRESET_PYRAMID_GOAL));
    c.operatorJoystick.getShootButton().whenPressed(new ShootCommand());

    c.operatorJoystick.getIntakeOutButton().whenPressed(new RunIntakeCommand(-1.0));
    c.operatorJoystick.getIntakeOutButton().whenReleased(new RunIntakeCommand(0.0));
    c.operatorJoystick.getIntakeButton().whenPressed(new RunIntakeCommand(1.0));
    c.operatorJoystick.getIntakeButton().whenReleased(new RunIntakeCommand(0.0));

    c.operatorJoystick.getShooterOnSwitch().whenPressed(new ShooterOnCommand(true));
    c.operatorJoystick.getShooterOnSwitch().whenReleased(new ShooterOnCommand(false));

    c.operatorJoystick.getConveyOutButton().whenPressed(new ContinuousShootCommand(true));
    c.operatorJoystick.getConveyOutButton().whenReleased(new ContinuousShootCommand(false));

    // NOTE(pat, 2013-03-03): PTO disabled for safety until the hanger works.
    //c.operatorJoystick.ptoOnSwitch.whenPressed(new PtoCommand());
    //c.operatorJoystick.ptoOnSwitch.whenReleased(new CheesyDriveCommand());
  }
}
