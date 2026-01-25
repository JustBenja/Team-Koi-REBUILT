package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.FieldConstants;
import frc.robot.subsystems.SwerveSubsystem;

public class DriveRelativeToHubCommand extends Command {

    private final SwerveSubsystem drivebase;
    private final Supplier<ChassisSpeeds> velocity;

    public DriveRelativeToHubCommand(SwerveSubsystem drivebase, Supplier<ChassisSpeeds> velocity) {
        this.drivebase = drivebase;
        this.velocity = velocity;
        addRequirements(drivebase); // important so it interrupts default drive
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        var vHubDist = drivebase.getVision().getPosition().getTranslation()
                .minus(FieldConstants.Hub.innerCenterPoint.toTranslation2d());

        if (vHubDist.getNorm() > Constants.ShooterConstants.kMaxShootingDist) {
            drivebase.driveFieldOriented(velocity).execute();
        } else {
            drivebase.driveWhileAiming(
                    velocity.get(),
                    new Pose2d(
                            FieldConstants.Hub.innerCenterPoint.getX(),
                            FieldConstants.Hub.innerCenterPoint.getY(),
                            new Rotation2d()
                    ),
                    Constants.ShooterConstants.kMaxShootingDist
            );
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false; // runs until button released
    }
}
