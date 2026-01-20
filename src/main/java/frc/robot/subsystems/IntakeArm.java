package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.MAXMotionConfig.MAXMotionPositionMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.IntakeArmConstants;

public class IntakeArm extends SubsystemBase {
    private final DutyCycleEncoder m_absoluteEncoder;
    private final SparkMax m_motor;
    private SparkClosedLoopController m_controller;
    private final RelativeEncoder m_relativeEncoder;
    private final ArmFeedforward aff;
    
    public IntakeArm() 
    {
        // set absolute encoder
        m_absoluteEncoder = new DutyCycleEncoder(
        IntakeArmConstants.kAbsoluteEncoderID, 
        IntakeArmConstants.ABS_ENCODER_RANGE, 
        IntakeArmConstants.ABS_ENCODER_OFFSET
        );
         
        // set motor
        m_motor = new SparkMax(IntakeArmConstants.kMotorID, MotorType.kBrushless);

        // set config pid & ff
        SparkMaxConfig config = new SparkMaxConfig();
        
        config.closedLoop
        .outputRange(-1, 1)
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder);
        
        config.closedLoop
        .pidf(
            IntakeArmConstants.kP,
            IntakeArmConstants.kI,
            IntakeArmConstants.kD,
            IntakeArmConstants.kFF
        )
        .maxMotion
            .positionMode(MAXMotionPositionMode.kMAXMotionTrapezoidal)
            .maxAcceleration(IntakeArmConstants.kMAXMotionAcceleration)
            .cruiseVelocity(IntakeArmConstants.kMAXVelocity)
            .allowedProfileError(IntakeArmConstants.kTolerance);


        // set motor config
        m_motor.configure(config,
        ResetMode.kNoResetSafeParameters,
        PersistMode.kNoPersistParameters);

        // set relative encoder
        m_relativeEncoder = m_motor.getEncoder();

        // set motor contoller
        m_controller = m_motor.getClosedLoopController();

        // set arm feedforward
        aff = new ArmFeedforward(
        IntakeArmConstants.kS,
        IntakeArmConstants.kG,
        IntakeArmConstants.kV,
        IntakeArmConstants.kA
        );


       // set the relative position to the duty position
        m_relativeEncoder.setPosition(m_absoluteEncoder.get());
    }
      
    public Command IntakeArmCommand(double angle) {

        return runOnce(() -> {
            setAngle(angle);
        });
    }

    public void setAngle(double angle){
    
        double arbffvolts = aff.calculate(Math.toRadians(angle),
        Math.toRadians(m_relativeEncoder.getPosition()),
        Math.toRadians(m_relativeEncoder.getVelocity())
        );
        
        m_controller.setReference(angle, ControlType.kMAXMotionPositionControl, ClosedLoopSlot.kSlot0, arbffvolts);
    }

    public double getAngle() 
    {
        return m_relativeEncoder.getPosition();
    }

     @Override
    public void periodic() {
    }

    @Override
    public void simulationPeriodic() {
    }
}