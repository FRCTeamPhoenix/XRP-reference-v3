// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.commands;

import java.util.Set;
import org.wpilib.command3.Command;
import org.wpilib.command3.Coroutine;
import org.wpilib.command3.Mechanism;
import first.robot.subsystems.Drivetrain;

/*
 * Creates a new TurnTime command. This command will turn your robot for a
 * desired rotational velocity and time.
 */
public class TurnTime implements Command {
  private final double duration;
  private final double rotationalVelocity;
  private final Drivetrain drive;

  /**
   * Creates a new TurnTime.
   *
   * @param velocity The velocity which the robot will turn. Negative is in reverse.
   * @param time How much time to turn in seconds
   * @param drive The drive subsystem on which this command will run
   */
  public TurnTime(double velocity, double time, Drivetrain drive) {
    rotationalVelocity = velocity;
    duration = time * 1000.0;
    this.drive = drive;
  }

  @Override
  public void run(Coroutine coroutine) {
    long endTimeMs = System.currentTimeMillis() + (long) duration;
    drive.arcadeDrive(0, 0);
    while (System.currentTimeMillis() < endTimeMs) {
      drive.arcadeDrive(0, rotationalVelocity);
      coroutine.yield();
    }
    drive.arcadeDrive(0, 0);
  }

  @Override
  public String name() {
    return "TurnTime";
  }

  @Override
  public Set<Mechanism> requirements() {
    return Set.of(drive);
  }
}
