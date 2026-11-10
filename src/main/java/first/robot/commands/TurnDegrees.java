// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.commands;

import java.util.Set;
import org.wpilib.command3.Command;
import org.wpilib.command3.Coroutine;
import org.wpilib.command3.Mechanism;
import first.robot.subsystems.Drivetrain;

public class TurnDegrees implements Command {
  private final Drivetrain drive;
  private final double degrees;
  private final double velocity;

  /**
   * Creates a new TurnDegrees. This command will turn your robot for a desired rotation (in
   * degrees) and rotational velocity.
   *
   * @param velocity The velocity which the robot will drive. Negative is in reverse.
   * @param degrees Degrees to turn. Leverages encoders to compare distance.
   * @param drive The drive subsystem on which this command will run
   */
  public TurnDegrees(double velocity, double degrees, Drivetrain drive) {
    this.degrees = degrees;
    this.velocity = velocity;
    this.drive = drive;
  }

  @Override
  public void run(Coroutine coroutine) {
    drive.arcadeDrive(0, 0);
    drive.resetEncoders();
    double targetDistance = Math.PI * 6.102 / 360 * degrees;
    while (getAverageTurningDistance() < targetDistance) {
     drive.arcadeDrive(0, velocity);
     coroutine.yield();
    }
    drive.arcadeDrive(0, 0);
  }

  @Override
  public String name() {
    return "TurnDegrees";
  }

  @Override
  public Set<Mechanism> requirements() {
    return Set.of(drive);
  }

  private double getAverageTurningDistance() {
    double leftDistance = Math.abs(drive.getLeftDistanceInch());
    double rightDistance = Math.abs(drive.getRightDistanceInch());
    return (leftDistance + rightDistance) / 2.0;
  }
}
