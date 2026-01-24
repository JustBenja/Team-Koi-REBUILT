package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HoodSubsystem extends SubsystemBase {

    public enum HoodStates {
        MOVING,
        ATSETPOINT
    }

    private Servo servoYehodi, servoGoy;
    private HoodStates state;

    public HoodSubsystem() {
        servoYehodi = new Servo(Constants.HoodConstants.kServoIdYehodi);
        servoGoy = new Servo(Constants.HoodConstants.kServoIdGoy);

        setAngle(Constants.HoodConstants.kStartingPos);
    }

    public void setAngle(double degrees) {
        // makes sure we don't surpass the physical limits of the servos range of motion
        degrees = MathUtil.clamp(degrees, Constants.HoodConstants.kMinDeg, Constants.HoodConstants.kMaxDeg);

        // turns the degrees into "percentage" (0.0-1.0)
        double normalized = (degrees - Constants.HoodConstants.kMinDeg)
                / (Constants.HoodConstants.kMaxDeg - Constants.HoodConstants.kMinDeg);

        // calculates the needed PWM value for the final set function

        // Normal servo
        double leftPwm = Constants.HoodConstants.kServoMin
                + normalized * (Constants.HoodConstants.kServoMax - Constants.HoodConstants.kServoMin);

        // Inverted servo
        double rightPwm = Constants.HoodConstants.kServoMin
                + (1.0 - normalized) * (Constants.HoodConstants.kServoMax - Constants.HoodConstants.kServoMin);

        servoYehodi.set(leftPwm);
        servoGoy.set(rightPwm);

        state = HoodStates.MOVING;
        Timer.delay(Constants.HoodConstants.kServoDelay);
        state = HoodStates.ATSETPOINT;
    }

    public HoodStates getState(){
        return state;
    }

    public Command HoodMethodCommand(double angle) {
        return runOnce(
                () -> {
                    setAngle(angle);
                });
    }
}