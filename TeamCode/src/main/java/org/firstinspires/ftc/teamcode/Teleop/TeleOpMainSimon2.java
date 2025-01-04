package org.firstinspires.ftc.teamcode.Teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import static android.os.SystemClock.sleep;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.Drawing;
import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.*;
import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.resources3_NanoTrojans;
import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.concurrent.TimeUnit;

@TeleOp(name ="TeleOpMainSimon2", group = "TeleOp")

public class TeleOpMainSimon2 extends LinearOpMode {

    private controls3_NanoTrojans g2control;
    private resources3_NanoTrojans resources;
    private resources_base_NanoTrojans resourcesbase;
    private DriveControl_NanoTorjan driveControl;
    //private DriveControl_Base driveControl;
    BNO055IMU imu;
    //        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
    //        parameters.loggingEnabled = true;
    //        parameters.loggingTag = "IMU";
    //        imu = hardwareMap.get(BNO055IMU.class, "imu");
    //        imu.initialize(parameters);
    public int rliftpos;
    public int lliftpos;
    public int rlspos;
    public int llspos ;

    boolean lsStoped = false;

    @Override
    public void runOpMode() throws InterruptedException {

        resources = new resources3_NanoTrojans(hardwareMap);
        resourcesbase = new resources_base_NanoTrojans(hardwareMap);

        telemetry.addLine("Initialized");
        telemetry.addLine("Claw Initial Position");
        telemetry.update();

        driveControl = new DriveControl_NanoTorjan(resourcesbase.leftFront, resourcesbase.rightFront, resourcesbase.leftBack, resourcesbase.rightBack);
        g2control = new controls3_NanoTrojans(resources.lsRight, resources.lsLeft, resources.claw, resources.arm, resources.wrist, resources.elbow, resources.llift, resources.rlift);

        waitForStart();

        Thread baseControlThread = new Thread(new baseControl());
        // Thread hlsThread = new Thread(new hls());
        Thread lsControlThread = new Thread(new lsControl());
        Thread wristThread = new Thread(new wrist());
        Thread liftThread = new Thread(new lift());
        Thread elbowThread = new Thread(new elbow());
        Thread armThread = new Thread(new arm());
        Thread clawThread = new Thread(new claw());
        Thread comboThread = new Thread(new combo());
        //Start 2  threads
        //baseControlThread.start();

        liftThread.start();
        lsControlThread.start();

        armThread.start();
        elbowThread.start();
        wristThread.start();
        clawThread.start();

        comboThread.start();

        //MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        //base control thread, let's use road runner's base control which has breaks
        while (!Thread.interrupted() && opModeIsActive())
        {
            driveControl.driveRobot(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        }

    }//end of runOpMode

    public class baseControl implements Runnable {
        @Override
        public void run() {
            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {
                driveControl.driveRobot(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            } //end of while loop

        }//end of run
    }//end of class baseControl

    public class baseControlRR implements Runnable {
        @Override
        public void run() {

           MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
           waitForStart();

           while (opModeIsActive()) {
               drive.setDrivePowers(new PoseVelocity2d(
                       new Vector2d(
                               -gamepad1.left_stick_y,
                               -gamepad1.left_stick_x
                       ),
                       -gamepad1.right_stick_x
               ));

               drive.updatePoseEstimate();

               telemetry.addData("x", drive.pose.position.x);
               telemetry.addData("y", drive.pose.position.y);
               telemetry.addData("heading (deg)", Math.toDegrees(drive.pose.heading.toDouble()));
               telemetry.update();

               TelemetryPacket packet = new TelemetryPacket();
               packet.fieldOverlay().setStroke("#3F51B5");
               Drawing.drawRobot(packet.fieldOverlay(), drive.pose);
               FtcDashboard.getInstance().sendTelemetryPacket(packet);

           }

        }//end of run
    }//end of class baseControl

    private class lsControl implements Runnable {
        boolean clawClosed = false;

        @Override
        public void run() {

            double lspower;
            double stoppedPowder=0;
            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {

                lspower = gamepad2.right_stick_y;
                rlspos = resources.lsLeft.getCurrentPosition();
                llspos = resources.lsRight.getCurrentPosition();
                telemetry.addData("rls pos", rlspos);
                telemetry.addData("lls pos", llspos);
                telemetry.addData("rlift pos", rliftpos);
                telemetry.addData("llift pos", lliftpos);
                telemetry.addData("lspower", lspower);
                telemetry.addData("isLSStoped ", lsStoped);
                telemetry.update();

                if (rliftpos<=-500 && rlspos<=-1300){
                    if(!lsStoped)
                    {
                        lsStoped = true;
                        stoppedPowder = gamepad2.right_stick_y;
                        resources.lsRight.setPower(0);
                        resources.lsLeft.setPower(0);
                        sleep(1000);
                        telemetry.addLine("stop now");
                        telemetry.update();
                    }
                    else
                    {
                        if(gamepad2.right_stick_y > 0.1)
                        {
                            rlspos = resources.lsLeft.getCurrentPosition();
                            llspos = resources.lsRight.getCurrentPosition();
                            lspower = gamepad2.right_stick_y;
                            resources.lsRight.setPower(-lspower);
                            resources.lsLeft.setPower(lspower);
                            sleep(500);
                            if(rliftpos>-1000 && rlspos>-2600) {
                                lsStoped = false;
                            }
                        }

                    }

                }
                else{
                    telemetry.addLine("go to run state");
                    telemetry.update();
                    lspower = gamepad2.right_stick_y;
                    resources.lsRight.setPower(-lspower);
                    resources.lsLeft.setPower(lspower);
                }


            }//end of while
        }//end of run
    }//end of thread lscontrol



    private class lift implements Runnable {

        @Override
        public void run() {

            double liftb;
            double lspower;
            boolean ReadPositon = true;
            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {
//                int rlspos = resources.lsLeft.getCurrentPosition();
//                int llspos = resources.lsRight.getCurrentPosition();
//                resources.llift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                resources.rlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                resources.llift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                resources.rlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                liftb = gamepad2.left_stick_y;
                resources.llift.setPower(-liftb);
                resources.rlift.setPower(-liftb);

                rliftpos = resources.rlift.getCurrentPosition();
                lliftpos = resources.llift.getCurrentPosition();

                telemetry.addData("rlift pos", rliftpos);
                telemetry.addData("llift pos", lliftpos);
                telemetry.update();

                rlspos = resources.lsLeft.getCurrentPosition();
                llspos = resources.lsRight.getCurrentPosition();

                telemetry.addData("right linear slide", rlspos);
                telemetry.addData("left linear slide", llspos);
                telemetry.update();
                sleep(50);
                if (rliftpos<=-1000 && rlspos<=-600){
                      resources.lsRight.setPower(0);
                      resources.lsLeft.setPower(0);
                      sleep(1000);
                      telemetry.addLine("stop now");
                      telemetry.update();

                }
                else{
                    lspower = gamepad2.right_stick_y;
                    resources.lsRight.setPower(-lspower);
                    resources.lsLeft.setPower(lspower);
                }

            }//end of while
        }//end of run
    }//end of thread lscontrol

    public class arm implements Runnable{
        @Override
        public void run() {

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {

                // align with linear slides/ hit the ground/pick up pixels// was y
                if (gamepad2.dpad_up) {
                    resources.arm.setPosition(0.3);
                }
                //slightly off the floor position/pick up pixels from wall position, was x
                if (gamepad2.dpad_right){
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
                    resources.elbow.setPosition(0.08);

                }
            }
        }
    }

    public class wrist implements Runnable {
        @Override
        public void run() {
            waitForStart();

            while (!Thread.interrupted() && opModeIsActive()) {
                //turn right
                if (gamepad2.right_trigger > 0) {
                    resources.wrist.setPosition(0.43);
                }

                //turn left
                if (gamepad2.left_trigger > 0) {
                    resources.wrist.setPosition(1);

                }
                //turn 90 degrees
//                if (gamepad2.dpad_up){
//                    resources.wrist.setPosition(0.765);
//                }

            }
        }
    }

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

    public class combo implements Runnable{
        @Override
        public void run(){
            waitForStart();

            while (!Thread.interrupted() && opModeIsActive()) {
                if (gamepad2.dpad_right) {
                    //claw wrist left
                    resources.wrist.setPosition(0.43);
                    //elbow straight
                    resources.elbow.setPosition(0.5);
                    //arm flip to control hubs
                    resources.arm.setPosition(0.2);
                }
                if (gamepad2.dpad_left) {
                    //claw wrist right
                    resources.wrist.setPosition(1);
                    //elbow straight
                    resources.elbow.setPosition(0.5);
                    //arm flip to opposite of control hubs
                    resources.arm.setPosition(0.8);
                }
            }

        }
    }


}//end of big class









