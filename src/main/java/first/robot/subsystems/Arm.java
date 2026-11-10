// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.subsystems;

import org.wpilib.command3.Mechanism;
import org.wpilib.xrp.XRPServo;

public class Arm implements Mechanism {
  private final XRPServo armServo;

  /** Creates a new Arm. */
  public Arm() {
    // Device number 4 maps to the physical Servo 1 port on the XRP
    armServo = new XRPServo(4);
  }

  /**
   * Set the current angle of the arm (0 - 180 degrees).
   *
   * @param angleDeg Desired arm angle in degrees
   */
  public void setAngle(double angleDeg) {
    armServo.setAngle(angleDeg);
  }
}
