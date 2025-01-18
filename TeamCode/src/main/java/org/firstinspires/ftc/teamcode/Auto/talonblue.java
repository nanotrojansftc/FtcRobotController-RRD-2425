package org.firstinspires.ftc.teamcode.Auto;

//RR-specific imports

// Non-RR imports

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.*;

import org.firstinspires.ftc.teamcode.Robot.Drive;

@Autonomous(name="talonb", group="Autonomous")
// Don't edit!!!!!!!!!!!!!!!!!!!!!
public  class talonblue extends LinearOpMode {
        //private controls3_NanoTrojans g2control;
        //private resources3_NanoTrojans resources;
        private resources_base_NanoTrojans resourcesbase;
        private DriveControl_NanoTorjan driveControl;

    @Override
    public void runOpMode() {
            waitForStart();
        Drive drive = new Drive(hardwareMap);
//            resources = new resources3_NanoTrojans(hardwareMap);
//            resourcesbase = new resources_base_NanoTrojans(hardwareMap);
//            driveControl = new DriveControl_NanoTorjan(resourcesbase.leftFront, resourcesbase.rightFront, resourcesbase.leftBack, resourcesbase.rightBack);
//            g2control = new controls3_NanoTrojans(resources.lsRight, resources.lsLeft, resources.claw, resources.arm, resources.wrist, resources.elbow, resources.llift, resources.rlift);


            while (opModeIsActive() && !isStopRequested()){
                //make angle of linear slides vertical
//                    resources.llift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                    resources.rlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                    resources.llift.setTargetPosition(900);
//                    resources.rlift.setTargetPosition(-900);
//                    resources.llift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    resources.rlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                    resources.llift.setPower(1);
//                    resources.rlift.setPower(1);
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
                drive.stop();



                telemetry.addLine(drive.gatherMotorPos());
                telemetry.update();

        }







}}

