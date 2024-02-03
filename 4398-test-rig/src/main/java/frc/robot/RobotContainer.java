// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.subsystems.TwoMotorSystem;
import frc.robot.subsystems.OneMotorSystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandPS5Controller m_driverController = new CommandPS5Controller(OperatorConstants.kDriverControllerPort);
  private final TwoMotorSystem m_twoSystem = new TwoMotorSystem();
  private final OneMotorSystem m_oneSystem = new OneMotorSystem();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //     .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_driverController
      .povUp()        
      .onTrue(new InstantCommand(() -> m_twoSystem.switchSpeed(true), m_twoSystem));

    m_driverController
      .povDown()
      .onTrue(new InstantCommand(() -> m_twoSystem.switchSpeed(false), m_twoSystem)); 

    m_driverController
      .R2()
      .whileTrue(new RunCommand(
          () -> m_twoSystem.setSpeed(m_twoSystem.getSpeed()),
          m_twoSystem)) 
      .onFalse(new RunCommand(
          () -> m_twoSystem.setSpeed(0.0),
          m_twoSystem));    
          
    m_driverController
      .povRight()        
      .onTrue(new InstantCommand(() -> m_oneSystem.switchSpeed(true), m_oneSystem));

    m_driverController
      .povLeft()
      .onTrue(new InstantCommand(() -> m_oneSystem.switchSpeed(false), m_oneSystem)); 

    m_driverController
      .L2()
      .whileTrue(new RunCommand(
          () -> m_oneSystem.setSpeed(m_oneSystem.getSpeed()),
          m_oneSystem)) 
      .onFalse(new RunCommand(
          () -> m_oneSystem.setSpeed(0.0),
          m_oneSystem));   
          
    m_driverController
      .L1()
      .whileTrue(new RunCommand(
          () -> m_oneSystem.setSpeed(-m_oneSystem.getSpeed()),
          m_oneSystem)) 
      .onFalse(new RunCommand(
          () -> m_oneSystem.setSpeed(0.0),
          m_oneSystem));  
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    //return Autos.exampleAuto(m_exampleSubsystem);
    return new InstantCommand();
  }
}
