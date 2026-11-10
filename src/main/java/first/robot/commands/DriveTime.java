// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.commands;

import java.util.Set;
import org.wpilib.command3.Command;
import org.wpilib.command3.Coroutine;
import org.wpilib.command3.Mechanism;
import first.robot.subsystems.Drivetrain;

public class DriveTime implements Command {
  private final double duration;
  private final double velocity;
  private final Drivetrain drive;

  /**
   * Creates a new DriveTime. This command will drive your robot for a desired velocity and time.
   *
   * @param velocity The velocity which the robot will drive. Negative is in reverse.
   * @param time How much time to drive in seconds
   * @param drive The drivetrain subsystem on which this command will run
   */
  public DriveTime(double velocity, double time, Drivetrain drive) {
    this.velocity = velocity;
    duration = time * 1000.0;
    this.drive = drive;
  }

  @Override
  public void run(Coroutine coroutine) {
    long endTimeMs = System.currentTimeMillis() + (long) duration;
    drive.arcadeDrive(0, 0);
    while (System.currentTimeMillis() < endTimeMs) {
      drive.arcadeDrive(velocity, 0);
      coroutine.yield();
    }
    drive.arcadeDrive(0, 0);
  }

  @Override
  public String name() {
    return "DriveTime";
  }

  @Override
  public Set<Mechanism> requirements() {
    return Set.of(drive);
  }
}
