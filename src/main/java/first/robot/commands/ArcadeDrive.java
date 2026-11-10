// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.commands;

import java.util.Set;
import java.util.function.Supplier;
import org.wpilib.command3.Command;
import org.wpilib.command3.Coroutine;
import org.wpilib.command3.Mechanism;
import first.robot.subsystems.Drivetrain;

public class ArcadeDrive implements Command {
  private final Drivetrain drivetrain;
  private final Supplier<Double> xaxisVelocitySupplier;
  private final Supplier<Double> zaxisRotateSupplier;

  /**
   * Creates a new ArcadeDrive. This command will drive your robot according to the velocity
   * supplier lambdas. This command does not terminate.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   * @param xaxisVelocitySupplier Lambda supplier of forward/backward velocity
   * @param zaxisRotateSupplier Lambda supplier of rotational velocity
   */
  public ArcadeDrive(
      Drivetrain drivetrain,
      Supplier<Double> xaxisVelocitySupplier,
      Supplier<Double> zaxisRotateSupplier) {
    this.drivetrain = drivetrain;
    this.xaxisVelocitySupplier = xaxisVelocitySupplier;
    this.zaxisRotateSupplier = zaxisRotateSupplier;
  }

  @Override
  public void run(Coroutine coroutine) {
    while (true) {
      drivetrain.arcadeDrive(xaxisVelocitySupplier.get(), zaxisRotateSupplier.get());
      coroutine.yield();
    }
  }

  @Override
  public String name() {
    return "ArcadeDrive";
  }

  @Override
  public Set<Mechanism> requirements() {
    return Set.of(drivetrain);
  }
}
