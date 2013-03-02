package com.team254.frc2013.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Loads a disc that is at the end of the conveyor into the shooter and shoots it.
 *
 * @author pat@team254.com (Patrick Fairbank)
 */
public class LoadAndShootCommand extends CommandGroup {
  public LoadAndShootCommand() {
    addSequential(new IndexerCommand(false));
    addSequential(new WaitCommand(0.4));
    addSequential(new IndexerCommand(true));
    addSequential(new WaitCommand(0.1));
    addSequential(new ShootCommand());
  }
}