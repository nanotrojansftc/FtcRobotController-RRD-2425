package org.firstinspires.ftc.teamcode.Auto;

//RR-specific imports

// Non-RR imports

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Drive;
@Autonomous(name="redAuto", group="Autonomous")
// Don't edit!!!!!!!!!!!!!!!!!!!!!!
public  class redAuto extends LinearOpMode {
    Drive drive = new Drive(hardwareMap);

    @Override
    public void runOpMode() {

            waitForStart();

            if (opModeIsActive()){

                drive.driveTiles(1);
                drive.setRotateDegrees(45);
            }







}}

