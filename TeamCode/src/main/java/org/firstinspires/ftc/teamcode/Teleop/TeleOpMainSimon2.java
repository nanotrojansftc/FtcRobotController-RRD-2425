package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.*;
import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.resources3_NanoTrojans;

import java.util.concurrent.TimeUnit;

@TeleOp(name ="TeleOpMainSimon2", group = "TeleOp")

public class TeleOpMainSimon2 extends LinearOpMode {


    private controls3_NanoTrojans g2control;
    private resources3_NanoTrojans resources;
    private resources_base_NanoTrojans resourcesbase;
    private DriveControl_NanoTorjan driveControl;
    //private DriveControl_Base driveControl;
    BNO055IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {
        resources = new resources3_NanoTrojans(hardwareMap);
        resourcesbase = new resources_base_NanoTrojans(hardwareMap);

//        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
//        parameters.loggingEnabled = true;
//        parameters.loggingTag = "IMU";
//        imu = hardwareMap.get(BNO055IMU.class, "imu");
//        imu.initialize(parameters);


        telemetry.addLine("Initialized");
        telemetry.addLine("Claw Initial Position");
        telemetry.update();

        //driveControl = new DriveControl_Base(hardwareMap);

        driveControl = new DriveControl_NanoTorjan(resourcesbase.leftFront, resourcesbase.rightFront, resourcesbase.leftBack, resourcesbase.rightBack);
        g2control = new controls3_NanoTrojans(resources.lsRight, resources.lsLeft, resources.claw, resources.arm, resources.wrist, resources.elbow, resources.llift, resources.rlift);

        waitForStart();
        Thread baseControlThread = new Thread(new baseControl());
//        Thread hlsThread = new Thread(new hls());
        Thread lsControlThread = new Thread(new lsControl());
        Thread wristThread = new Thread(new wrist());
        Thread liftThread = new Thread(new lift());
        Thread elbowThread = new Thread(new elbow());
        Thread armThread = new Thread(new arm());
        Thread clawThread = new Thread(new claw());
        Thread comboThread = new Thread(new combo());
        //Start 2  threads
        baseControlThread.start();
        lsControlThread.start();
        wristThread.start();
        liftThread.start();
        elbowThread.start();
        armThread.start();
        clawThread.start();
        comboThread.start();




        //empty thead do nothing at this time
        while (opModeIsActive()) {
        }

    }

    public class baseControl implements Runnable {
        @Override
        public void run() {
            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {
                driveControl.driveRobot(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            } //end of while loop

        }//end of run
    }//end of class baseControl
    private class lsControl implements Runnable {
        boolean clawClosed = false;

        @Override
        public void run() {

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {

                double lspower = gamepad2.right_stick_y;
                resources.lsRight.setPower(-lspower);
                resources.lsLeft.setPower(lspower);

            }//end of while
        }//end of run
    }//end of thread lscontrol
    public class combo implements Runnable{
        @Override
        public void run(){
            //
            if (gamepad2.dpad_down){
                //claw open
                resources.claw.setPosition(0);
                //
                resources.elbow.setPosition(0.5);
            }




        }
    }

    public class wrist implements Runnable{
        @Override
        public void run() {
            waitForStart();


            while (!Thread.interrupted() && opModeIsActive()) {
                //turn right

                    if (gamepad2.dpad_right) {
                        resources.wrist.setPosition(0.43);
                    }


                //turn left
                if (gamepad2.dpad_left) {
                    resources.wrist.setPosition(1);

                }
                //turn 90 degrees
                if (gamepad2.dpad_up){
                    resources.wrist.setPosition(0.765);
                }

            }
        }
    }

    private class lift implements Runnable {

        @Override
        public void run() {

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {

                double liftb = gamepad2.left_stick_y;
                resources.llift.setPower(-liftb);
                resources.rlift.setPower(liftb);

            }//end of while
        }//end of run
    }//end of thread lscontrol

    public class claw implements Runnable{
        @Override
        public void run() {

            waitForStart();

            while (!Thread.interrupted() && opModeIsActive()) {
                //closed
                if (gamepad2.left_bumper) {
                    resources.claw.setPosition(0);
                }
                //open
                if (gamepad2.right_bumper) {
                    resources.claw.setPosition(0.6);

                }
            }
        }
    }
    public class arm implements Runnable{
        @Override
        public void run() {

            waitForStart();

            while (!Thread.interrupted() && opModeIsActive()) {

                // align with linear slides/ hit the ground/pick up pixels
                if (gamepad2.y) {
                    resources.arm.setPosition(0.5);

                }
                //slightly off the floor position/pick up pixels from wall position
                if (gamepad2.x){
                    resources.arm.setPosition(0.2);
                }
                if (gamepad2.dpad_down){
                    resources.arm.setPosition(0.8);
                }

            }
        }
    }
    public class elbow implements Runnable{
        @Override
        public void run() {

            waitForStart();

            while (!Thread.interrupted() && opModeIsActive()) {
                //fully straight
                if (gamepad2.a) {
                    resources.elbow.setPosition(0.5);
                }
                //90 degrees
                if (gamepad2.b) {
                    resources.elbow.setPosition(0);

                }
            }
        }
    }




}//end of big class









