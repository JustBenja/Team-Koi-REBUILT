package frc.robot.subsystems;

import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import frc.robot.utils.LimelightHelpers;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
    private AHRS m_gyro;
    private SwerveDrivePoseEstimator m_poseEstimator;

    public Vision() {
        m_gyro = new AHRS(NavXComType.kMXP_SPI);
        
    }

    @Override
    public void periodic() {

    }

    public void simulationPeriodic()
    {

    }
}
