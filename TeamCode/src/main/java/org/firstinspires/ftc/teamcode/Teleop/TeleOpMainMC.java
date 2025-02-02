package org.firstinspires.ftc.teamcode.Teleop;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Drawing;
import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.DriveControl_NanoTorjan;
import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.controls_MC;
import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.resources_MC;
import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.resources_base_NanoTrojans;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@TeleOp(name ="TeleOpMainMC", group = "TeleOp")

public class TeleOpMainMC extends LinearOpMode {

    private controls_MC control;
    private resources_MC resources;
    private resources_base_NanoTrojans resourcesbase;
    private DriveControl_NanoTorjan driveControl;
    //private DriveControl_Base driveControl;
    BNO055IMU imu;
    //        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
    //        parameters.loggingEnabled = true;
    //        parameters.loggingTag = "IMU";
    //        imu = hardwareMap.get(BNO055IMU.class, "imu");
    //        imu.initialize(parameters);


    boolean lsStoped = false;
    public int rhspos;
    public int lhspos ;
    boolean canNotMoveUp = false;



    @Override
    public void runOpMode() throws InterruptedException {

        resources = new resources_MC(hardwareMap);
        resourcesbase = new resources_base_NanoTrojans(hardwareMap);

        telemetry.addLine("Initialized");
        telemetry.addLine("Claw Initial Position");
        telemetry.update();

        driveControl = new DriveControl_NanoTorjan(resourcesbase.leftFront, resourcesbase.rightFront, resourcesbase.leftBack, resourcesbase.rightBack);
        control = new controls_MC(resources.lsRight, resources.lsLeft, resources.lhs, resources.rhs, resources.intake
                , resources.blocker, resources.ril, resources.lil, resources.claw, resources.ra, resources.la, resources.hanger, resources.backclaw);

        waitForStart();

        //Thread baseControlThread = new Thread(new baseControl());
        // Thread hlsThread = new Thread(new hls());
        Thread lsControlThread = new Thread(new lsControl());
        Thread liftThread = new Thread(new lift());
        Thread armThread = new Thread(new arm());

        //Start 2  threads
        //baseControlThread.start();
        lsControlThread.start();
        armThread.start();
        liftThread.start();


        //MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        //base control thread, let's use road runner's base control which has breaks
        while (!Thread.interrupted() && opModeIsActive())
        {
            driveControl.driveRobot(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
        }

    }//end of runOpMode


    private class lsControl implements Runnable {
        boolean clawClosed = false;

        @Override
        public void run() {

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {
                // LINEAR SLIDES STUFF
                double lspower = gamepad2.right_stick_y;
                resources.lsRight.setPower(lspower);
                resources.lsLeft.setPower(-lspower);
                // HORIZONTAL CONTROL STUFF
                double hlspower = gamepad2.left_stick_y;
                resources.rhs.setPower(0.5*hlspower);
                resources.lhs.setPower(0.5*-hlspower);
            }//end of while
        }//end of run
    }//end of thread lscontrol


    public class arm implements Runnable{
        @Override
        public void run() {

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {
//              ARM STUFF

                if (gamepad2.dpad_up){
                    //resources.ra.setPosition(0.3);
                    //resources.la.setPosition(0.7);
                    control.armup();
                }
                //
                if (gamepad2.dpad_down){
//                    control.openclaw();
                    control.armdown();
                    control.hsretract();

                }
                // HANG STUFF
                if(!canNotMoveUp) {
                    if (gamepad1.left_trigger>0){
                        control.hangeron();
                    }
                    if (gamepad1.right_trigger>0){
                        control.hangerreverse();
                    }
                    else{
                        control.hangeroff();
                    }
                    if(gamepad1.dpad_down){
                        control.hangeron();
                        sleep(1600);
                        control.hangeroff();
                    }

                }
                //INTAKE STUFF
                double intake = gamepad2.right_stick_x;
                resources.intake.setPower(intake);

            }
        }
    }
    //done with intake lift
    public class lift implements Runnable {
        @Override
        public void run() {

            waitForStart();

            while (!Thread.interrupted() && opModeIsActive()) {
                // INTAKE LIFT STUFF HERE
                //up
                if (gamepad2.dpad_left) {


                    control.intakeup();
                }
                if (gamepad2.dpad_right)/*down*/ {
                control.intakedown();
                }
                // CLAW STUFF
                if (gamepad2.left_bumper) {
                    control.openclaw();
                    //control.openclaw();
                }
                //close
                if (gamepad2.right_bumper) {
                    control.closeclaw();


                }
                if (gamepad2.x){
                    control.bclawopen();

                }
                if (gamepad2.y){
                    control.bclawclose();
                }


            }
        }
    }

        //claw done






}//end of big class









