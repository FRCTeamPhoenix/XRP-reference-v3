// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.commands;

import java.util.Set;
import org.wpilib.command3.Command;
import org.wpilib.command3.Coroutine;
import org.wpilib.command3.Mechanism;
import first.robot.subsystems.Drivetrain;

public class DriveDistance implements Command {
  private final Drivetrain drive;
  private final double distance;
  private final double velocity;

  /**
   * Creates a new DriveDistance. This command will drive your your robot for a desired distance at
   * a desired velocity.
   *
   * @param velocity The velocity at which the robot will drive
   * @param inches The number of inches the robot will drive
   * @param drive The drivetrain subsystem on which this command will run
   */
  public DriveDistance(double velocity, double inches, Drivetrain drive) {
    distance = inches;
    this.velocity = velocity;
    this.drive = drive;
  }

  @Override
  public void run(Coroutine coroutine) {
    drive.arcadeDrive(0, 0);
    drive.resetEncoders();
    while (Math.abs(drive.getAverageDistanceInch()) < distance) {
      drive.arcadeDrive(velocity, 0);
      coroutine.yield();
    }
    drive.arcadeDrive(0, 0);
  }

  @Override
  public String name() {
    return "DriveDistance";
  }

  @Override
  public Set<Mechanism> requirements() {
    return Set.of(drive);
  }
}
