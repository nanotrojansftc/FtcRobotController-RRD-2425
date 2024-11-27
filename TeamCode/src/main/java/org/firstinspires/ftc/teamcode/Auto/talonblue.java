package org.firstinspires.ftc.teamcode.Auto;

//RR-specific imports

// Non-RR imports

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Drive;

@Autonomous(name="talonb", group="Autonomous")
// Don't edit!!!!!!!!!!!!!!!!!!!!!
public  class talonblue extends LinearOpMode {

    @Override
    public void runOpMode() {

            waitForStart();
        Drive drive = new Drive(hardwareMap);

        while (opModeIsActive() && !isStopRequested()){
//              hang specimen
                drive.driveTiles(1);
                //go get first box thing
                drive.strafeTiles(-1.5);
                drive.driveTiles(1.5);
                drive.strafeTiles(-1);
                drive.driveTiles(-2.5);
                //get another box
                drive.driveTiles(2.5);
                drive.strafeTiles(-1);
                drive.driveTiles(-2.5);
                //get another box
//                drive.driveTiles(2.5);
//                drive.strafeTiles(-0.5);
//                drive.driveTiles(-2.5);
//              pick up specimen from human player
                drive.driveTiles(1);
//                drive.strafeTiles(1);
                drive.driveTiles(-1);
                // hang specimen
                drive.strafeTiles(3);
                drive.driveTiles(1);
                //get another specimen
                drive.driveTiles(-1);
                drive.strafeTiles(-4);
                drive.driveTiles(-0.5);
                drive.driveTiles(1);
                //hang second specimen
                drive.strafeTiles(4);
                drive.driveTiles(1);
                //stop
                drive.stop(0);



                telemetry.addLine(drive.gatherMotorPos());
                telemetry.update();

        }







}}

