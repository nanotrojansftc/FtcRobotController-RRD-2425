package org.firstinspires.ftc.teamcode.Auto;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Robot.Drive;
@Autonomous(name="blueAuto (9Pts scoring)", group="Autonomous")
public  class blueAuto9pts extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();
        Drive drive = new Drive(hardwareMap);
            if(opModeIsActive() && !isStopRequested()){
                drive.driveTiles(1);
                /*drive.strafeTiles(.85f);
                drive.driveTiles(1.5f);
                drive.setRotateDegrees(180);
                drive.strafeTiles(-10/24f);
                drive.driveTiles(2.25f);
                drive.driveTiles(-2.25f);
                drive.strafeTiles(11/24f);
                drive.driveTiles(2.25f);
                */
            }
            while (opModeIsActive()){
            telemetry.addLine(drive.gatherMotorPos());
            telemetry.update();
            }
    }
}