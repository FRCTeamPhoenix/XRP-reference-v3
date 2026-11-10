// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.commands;

import java.util.Set;
import org.wpilib.command3.Command;
import org.wpilib.command3.Coroutine;
import org.wpilib.command3.Mechanism;
import first.robot.subsystems.Drivetrain;

public class AutonomousTime implements Command {
  private final Drivetrain drivetrain;

  /**
   * Creates a new Autonomous Drive based on time. This will drive out for a period of time, turn
   * around for time (equivalent to time to turn around) and drive forward again. This should mimic
   * driving out, turning around and driving back.
   *
   * @param drivetrain The drive subsystem on which this command will run
   */
  public AutonomousTime(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
  }

  @Override
  public void run(Coroutine coroutine) {
    coroutine.await(new DriveTime(-0.8, 2.0, drivetrain));
    coroutine.await(new TurnTime(-0.8, 2.1, drivetrain));
    coroutine.await(new DriveTime(-0.8, 2.0, drivetrain));
    coroutine.await(new TurnTime(0.8, 2.1, drivetrain));
  }

  @Override
  public String name() {
    return "AutonomousTime";
  }

  @Override
  public Set<Mechanism> requirements() {
    return Set.of(drivetrain);
  }
}
