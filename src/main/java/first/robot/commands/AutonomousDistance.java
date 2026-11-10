// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.commands;

import java.util.Set;
import org.wpilib.command3.Command;
import org.wpilib.command3.Coroutine;
import org.wpilib.command3.Mechanism;
import first.robot.subsystems.Drivetrain;

public class AutonomousDistance implements Command {
  private final Drivetrain drivetrain;

  /**
   * Creates a new Autonomous Drive based on distance. This will drive out for a specified distance,
   * turn around and drive back.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   */
  public AutonomousDistance(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
  }

  @Override
  public void run(Coroutine coroutine) {
    coroutine.await(new DriveDistance(-0.8, 10, drivetrain));
    coroutine.await(new TurnDegrees(-0.8, 180, drivetrain));
    coroutine.await(new DriveDistance(-0.8, 10, drivetrain));
    coroutine.await(new TurnDegrees(0.8, 180, drivetrain));
  }

  @Override
  public String name() {
    return "AutonomousDistance";
  }

  @Override
  public Set<Mechanism> requirements() {
    return Set.of(drivetrain);
  }
}
