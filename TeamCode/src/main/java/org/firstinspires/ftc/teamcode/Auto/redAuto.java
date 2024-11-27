package org.firstinspires.ftc.teamcode.Auto;

//RR-specific imports

// Non-RR imports

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Drive;
@Autonomous(name="redAuto", group="Autonomous")
// Don't edit!!!!!!!!!!!!!!!!!!!!!
public  class redAuto extends LinearOpMode {

    @Override
    public void runOpMode() {

            waitForStart();
        Drive drive = new Drive(hardwareMap);

        if(opModeIsActive() && !isStopRequested()){
            drive.setRotateDegrees(-45);
            drive.driveTiles(1);
            drive.setRotateDegrees(45);
            drive.driveTiles(2);
            drive.setRotateDegrees(-180);
            drive.driveTiles(3);
            drive.driveTiles(-3);
            drive.strafeTiles(1);
            drive.driveTiles(3);
            drive.driveTiles(-3);
            drive.strafeTiles(0.5F);
            drive.driveTiles(3);
        }
        while (opModeIsActive()){
            telemetry.addLine(drive.gatherMotorPos());
            telemetry.update();
        }







}}

