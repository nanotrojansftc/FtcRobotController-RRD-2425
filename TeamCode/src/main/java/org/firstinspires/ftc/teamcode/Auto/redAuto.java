package org.firstinspires.ftc.teamcode.Auto;

//RR-specific imports

// Non-RR imports
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.MecanumDrive;
@Autonomous(name="redAuto", group="Robot")
// Don't edit!!!!!!!!!!!!!!!!!!!!!!
public abstract class redAuto extends LinearOpMode {
    @Override

    public void runOpMode() {
    Drive.driveTiles(1);


    }

}
