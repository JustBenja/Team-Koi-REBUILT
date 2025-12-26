package frc.robot;

public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class ShooterConstants {
    public static final int M_MOTOR_ID = 0;
    public static final int S_MOTOR_ID = 0;
    public static final boolean kSecondaryInverted = true;
    
    public static final double kGearRatio = 1.0;
    public static final double kTolerance = 50.0;
    
    public static final double kP = 0.0001;
    public static final double kI = 0.0;
    public static final double kD = 0.0;
    public static final double kFF = 0.0;
    
    public static final double kS = 0.1;
    public static final double kV = 0.12;
    public static final double kA = 0.01;
}

  public static class ArmConstants {
    public static final int MOTOR_ID = 0;
    public static final int AB_ENCDR_ID = 0;
    public static final double kGearRatio = 100.0;
    public static final double kEncoderOffset = 0.0;
    public static final double kMinAngle = -5.0;
    public static final double kMaxAngle = 120.0;
    public static final double kTolerance = 3.0;
    public static final double kMovingVelocity = 20.0;
    public static final double kP = 0.02;
    public static final double kI = 0.0;
    public static final double kD = 0.0;
    public static final double kFF = 0.0;
    public static final double kS = 0.1;
    public static final double kG = 0.5;
    public static final double kV = 2.5;
    public static final double kA = 0.1;
    public static final double kMaxVelocity = 90.0;
    public static final double kMaxAcceleration = 180.0;
}
}
