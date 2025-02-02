package org.firstinspires.ftc.teamcode.Auto_NanoTrojans;

//RR-specific imports

// Non-RR imports

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Drive;

@Autonomous(name="NT_Test", group="Autonomous")
// Don't edit!!!!!!!!!!!!!!!!!!!!!
public  class NT_Test extends LinearOpMode {

    @Override
    public void runOpMode() {

        waitForStart();
        Drive drive = new Drive(hardwareMap);
        boolean stop = false;

        while (opModeIsActive() && !isStopRequested() && !stop) {

                drive.driveTiles(1);
                drive.setRotateDegrees(90);
//                drive.driveTiles(1);
//                drive.setRotateDegrees(45);
//                drive.driveTiles(1);
//                drive.setRotateDegrees(-45);
//                drive.driveTiles(6);
//                drive.driveTiles(-1);


                  stop = true;


        }

    }





}

