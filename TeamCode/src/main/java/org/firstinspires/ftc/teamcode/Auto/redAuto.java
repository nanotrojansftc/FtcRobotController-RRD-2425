package org.firstinspires.ftc.teamcode.Auto;

//RR-specific imports

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Drive;

@Autonomous(name="redAuto", group="Robot")
// Don't edit!!!!!!!!!!!!!!!!!!!!!!
public abstract class redAuto extends LinearOpMode {
    @Override

    public void runOpMode() {
        Drive.init(hardwareMap);


        if(opModeIsActive()){

            Drive.driveTiles(1);
            Drive.rotateDegrees(45);

        }


    }

}
