package org.firstinspires.ftc.teamcode.Auto;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Robot.Drive;
@Autonomous(name="redAuto (3Pts scoring)", group="Autonomous")
public  class redAuto3pts extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();
        Drive drive = new Drive(hardwareMap);
            if(opModeIsActive() && !isStopRequested()){
            drive.strafeTiles(-1f);
            }
            while (opModeIsActive()){
            telemetry.addLine(drive.gatherMotorPos());
            telemetry.update();
            }

    }
}