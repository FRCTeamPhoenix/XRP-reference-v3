// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot;

import org.wpilib.command3.Command;
import org.wpilib.command3.Trigger;
import org.wpilib.command3.button.JoystickButton;
import org.wpilib.driverstation.Gamepad;
import org.wpilib.driverstation.GenericHID;
import org.wpilib.driverstation.Joystick;
import first.robot.commands.ArcadeDrive;
import first.robot.commands.AutonomousDistance;
import first.robot.commands.AutonomousTime;
import first.robot.subsystems.Arm;
import first.robot.subsystems.Drivetrain;
import org.wpilib.tunable.Selectable;
import org.wpilib.tunable.Tunables;
import org.wpilib.xrp.XRPOnBoardIO;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain drivetrain = new Drivetrain();
  private final XRPOnBoardIO onboardIO = new XRPOnBoardIO();
  private final Arm arm = new Arm();

  // Assumes a gamepad plugged into channel 0
  private final Joystick controller = new Joystick(0);

  // Create SmartDashboard chooser for autonomous routines
  private final Selectable<Command> chooser = new Selectable<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * org.wpilib.driverstation.Joystick} or {@link Gamepad}), and then passing it to a {@link
   * org.wpilib.command3.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Default command is arcade drive. This will run unless another command
    // is scheduled over it.
    drivetrain.setDefaultCommand(getArcadeDriveCommand());

    // Example of how to use the onboard IO
    Trigger userButton = new Trigger(onboardIO::getUserButtonPressed);
    userButton
        .onTrue(Command.noRequirements(coroutine -> {
          System.out.println("USER Button Pressed");
        }).named("User Button Pressed"))
        .onFalse(Command.noRequirements(coroutine -> {
          System.out.println("USER Button Released");
        }).named("User Button Released"));

    JoystickButton joystickAButton = new JoystickButton(controller, 1);
    joystickAButton
        .onTrue(arm.run(coroutine -> arm.setAngle(45.0)).named("Arm set 45 degrees"))
        .onFalse(arm.run(coroutine -> arm.setAngle(0.0)).named("Arm set 0 degrees"));

    JoystickButton joystickBButton = new JoystickButton(controller, 2);
    joystickBButton
        .onTrue(arm.run(coroutine -> arm.setAngle(90.0)).named("Arm set 90 degrees"))
        .onFalse(arm.run(coroutine -> arm.setAngle(0.0)).named("Arm set 0 degrees"));

    // Setup SmartDashboard options
    chooser.addDefault("Auto Routine Distance", new AutonomousDistance(drivetrain));
    chooser.add("Auto Routine Time", new AutonomousTime(drivetrain));
    Tunables.publish("Auto Chooser", chooser);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //return chooser.getSelected();
    return new AutonomousTime(drivetrain);
  }

  /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   *
   * @return the command to run in teleop
   */
  public Command getArcadeDriveCommand() {
    return new ArcadeDrive(
        drivetrain, () -> -controller.getRawAxis(1), () -> -controller.getRawAxis(2));
  }
}
