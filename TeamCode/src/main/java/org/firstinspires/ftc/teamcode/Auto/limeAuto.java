package org.firstinspires.ftc.teamcode.Auto;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Robot.Drive;
@Disabled
@Autonomous(name="redAuto (3Pts scoring)", group="Autonomous")
public  class limeAuto extends LinearOpMode {
    @Override

    public void runOpMode() {
        waitForStart();
        Drive drive = new Drive(hardwareMap);
        if(opModeIsActive() && !isStopRequested()){
            drive.driveTiles(1f);
            drive.setRotateDegrees(180);
            drive.driveTiles(1f);

        }
        while (opModeIsActive()){
            telemetry.addLine(drive.gatherMotorPos());
            telemetry.update();
        }

    }
}