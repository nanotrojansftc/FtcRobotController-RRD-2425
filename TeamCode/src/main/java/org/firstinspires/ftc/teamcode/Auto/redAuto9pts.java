package org.firstinspires.ftc.teamcode.Auto;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Drive;

@Autonomous(name="redAuto (9Pts scoring)", group="Autonomous")
public  class redAuto9pts extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();
        Drive drive = new Drive(hardwareMap);
            if(opModeIsActive() && !isStopRequested()){
            drive.setRotateDegrees(45);
            drive.driveTiles(1.3f);
            drive.setRotateDegrees(-45);
            drive.driveTiles(1.5f);
            drive.setRotateDegrees(180);
            drive.driveTiles(2.5f);
            drive.driveTiles(-2.5f);
            drive.strafeTiles(1);
            drive.driveTiles(2.5f);
            }
            while (opModeIsActive()){
            telemetry.addLine(drive.gatherMotorPos());
            telemetry.update();
            }
    }
}