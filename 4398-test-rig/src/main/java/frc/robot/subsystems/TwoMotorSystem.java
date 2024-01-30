// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ControlIDConstants;
import frc.robot.Constants.TwoMotorSystemConstants;

public class TwoMotorSystem extends SubsystemBase {
  private CANSparkFlex m_motorOne = new CANSparkFlex(ControlIDConstants.kMotorOneId, MotorType.kBrushless);
  private CANSparkFlex m_motorTwo = new CANSparkFlex(ControlIDConstants.kMotorTwoId, MotorType.kBrushless);

  private double[] m_speeds = {.35, .4, .45, .5, .55, .6, .65, .7, .75, .8, .85, .9, .95, 1};
  private int m_speedsPointer = 1;

  /** Creates a new TwoMotorSystem. */
  public TwoMotorSystem() {
    m_motorOne.setInverted(false);
    m_motorOne.setSmartCurrentLimit(TwoMotorSystemConstants.kTwoMotorCurrentLimit);

    m_motorTwo.setInverted(true);
    m_motorTwo.setSmartCurrentLimit(TwoMotorSystemConstants.kTwoMotorCurrentLimit);

    m_motorTwo.follow(m_motorOne);

    m_motorOne.burnFlash();
    m_motorTwo.burnFlash();
  }

  public void switchSpeed(boolean direction){
    if(direction){
      m_speedsPointer++;

      if(m_speedsPointer > (m_speeds.length - 1)){
        m_speedsPointer = 0;
      }
    }
    else{
      m_speedsPointer--;

      if(m_speedsPointer < 0){
       m_speedsPointer = (m_speeds.length - 1);
      }
    }    
  }

  public double getSpeed(){
    return m_speeds[m_speedsPointer];
  }

  public void setSpeed(double speed){
    m_motorOne.set(speed);    
  } 

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Target Speed", getSpeed() * 100);
  }
}
