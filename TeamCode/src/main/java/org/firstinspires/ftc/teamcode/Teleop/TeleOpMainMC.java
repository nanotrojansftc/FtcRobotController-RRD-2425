package org.firstinspires.ftc.teamcode.Teleop;

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
        //control = new controls_MC(resources.lsRight, resources.lsLeft, resources.lhs, resources.rhs, resources.intake
               // , resources.blocker, resources.ril, resources.lil, resources.claw, resources.ra, resources.la);

        waitForStart();

        Thread baseControlThread = new Thread(new baseControl());
        // Thread hlsThread = new Thread(new hls());
        Thread hlsControlThread = new Thread(new hlsControl());
        Thread lsControlThread = new Thread(new lsControl());
        Thread intakeThread = new Thread(new intake());
        Thread liftThread = new Thread(new lift());
        Thread armThread = new Thread(new arm());
        Thread clawThread = new Thread(new claw());
        Thread comboThread = new Thread(new combo());
        //Start 2  threads
        //baseControlThread.start();

        intakeThread.start();
        hlsControlThread.start();
        lsControlThread.start();


        armThread.start();
        liftThread.start();
        clawThread.start();

        comboThread.start();

        //MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        //base control thread, let's use road runner's base control which has breaks
        while (!Thread.interrupted() && opModeIsActive())
        {
            driveControl.driveRobot(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
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

    private class hlsControl implements Runnable {
        boolean clawClosed = false;
        //double hspower1 , hspower2;

        @Override
        public void run() {
            boolean stop = false;
            double rpos =0;
            double lpos = 1;

            double stoppedPower=0;
            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {
                //for now to reset stuff
//                if (gamepad2.dpad_up){
//                    resources.rhs.setPosition(0);
//                    resources.lhs.setPosition(1);
//                }
                double hlspower = gamepad2.left_stick_y;
                resources.rhs.setPower(0.5*hlspower);
                resources.lhs.setPower(0.5*-hlspower);
                if(hlspower<0) {

                    canNotMoveUp = true;
                }
                if (hlspower > 0) {

                    canNotMoveUp = false;
                }


            }//end of while
        }//end of run
    }//end of thread horizontal linear slide control
    private class lsControl implements Runnable {
        boolean clawClosed = false;

        @Override
        public void run() {

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {

                double lspower = gamepad2.right_stick_y;
                resources.lsRight.setPower(lspower);
                resources.lsLeft.setPower(-lspower);
                if (gamepad2.x){
//                    resources.ra.setPosition(0.6);
//                    resources.la.setPosition(0.4);
//                    sleep(500);
//                    resources.claw.setPosition(0);
                    resourcesbase.leftBack.setPower(1);
                    resourcesbase.rightBack.setPower(-1);
                    resourcesbase.leftFront.setPower(1);
                    resourcesbase.rightFront.setPower(-1);
                    sleep(1000);
                    resourcesbase.leftBack.setPower(0);
                    resourcesbase.rightBack.setPower(0);
                    resourcesbase.leftFront.setPower(0);
                    resourcesbase.rightFront.setPower(0);


                }



            }//end of while
        }//end of run
    }//end of thread lscontrol



    private class intake implements Runnable {

        @Override
        public void run() {
            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {
                if (gamepad2.right_trigger > 0) {
                    resources.intake.setPower(100);
                }
                if (gamepad2.left_trigger > 0) {
                    resources.intake.setPower(-1);
                } else {
                    resources.intake.setPower(0);
                }


            }

        }//end of run
    }//end of thread lscontrol

    public class arm implements Runnable{
        @Override
        public void run() {

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {
//                resources.ra.setPosition(0.0);
//                resources.la.setPosition(1);
                if (gamepad2.dpad_down  && !canNotMoveUp){
                    //resources.ra.setPosition(0.3);
                    //resources.la.setPosition(0.7);
                    resources.ra.setPosition(1);
                    resources.la.setPosition(1);

                    resources.rhs.setPower(0.8);
                    resources.lhs.setPower(-0.8);
                }
                //down
                if (gamepad2.dpad_up){
                    resources.ra.setPosition(0.3);
                    resources.la.setPosition(0.3);
                }
//                if (gamepad2.x){
////                    resources.ra.setPosition(0.6);
////                    resources.la.setPosition(0.4);
////                    sleep(500);
////                    resources.claw.setPosition(0);
//                    resourcesbase.leftBack.setPower(1);
//                    resourcesbase.rightBack.setPower(-1);
//                    resourcesbase.leftFront.setPower(1);
//                    resourcesbase.rightFront.setPower(-1);
//                    sleep(1000);
//                    resourcesbase.leftBack.setPower(0);
//                    resourcesbase.rightBack.setPower(0);
//                    resourcesbase.leftFront.setPower(0);
//                    resourcesbase.rightFront.setPower(0);
//
//
//                }
//                if (gamepad2.y){
////                    resources.ra.setPosition(0);
////                    resources.la.setPosition(1);
//                    resources.lsLeft.setPower(-1);
//                    resources.lsRight.setPower(1);
//                    sleep(5000);
////                    resources.lsLeft.setPower(1);
////                    resources.lsRight.setPower(-1);
////                    sleep(1000);
//                    resources.lsLeft.setPower(0);
//                    resources.lsRight.setPower(0);
//                }




            }
        }
    }
    //done with intake lift
    public class lift implements Runnable{
        @Override
        public void run() {

            waitForStart();

            while (!Thread.interrupted() && opModeIsActive()) {
                //up
                if (gamepad2.dpad_left) {


                    resources.blocker.setPosition(0.5);

                    resources.ril.setPosition(0);
                    resources.lil.setPosition(1);                }
                if(gamepad2.dpad_right)/*down*/{
//                    resources.ril.setPosition(0.4);
//                    resources.lil.setPosition(0.7);
                    resources.blocker.setPosition(1);

                    resources.ril.setPosition(0.95);
                    resources.lil.setPosition(0.0);
                }



            }
        }
    }


//claw done
    public class claw implements Runnable{
        @Override
        public void run() {

            waitForStart();

            while (!Thread.interrupted() && opModeIsActive()) {
                //open
                if (gamepad2.left_bumper) {
                    resources.claw.setPosition(0);
                    //control.openclaw();
                }
                //close
                if (gamepad2.right_bumper) {
                    resources.claw.setPosition(0.6);
                    //control.closeclaw();
                    //sleep(200);
                    //resources.rhs.setPower(0.2);
                    //resources.lhs.setPower(-0.2);
//sleep(50);
                    resources.rhs.setPower(-0.8);
                    resources.lhs.setPower(0.8);
                    //resources.rhs.setPower(-0.1);
                    //resources.lhs.setPower(0.1);
                    //resources.rhs.setPower(-0.1);
                    //resources.lhs.setPower(0.1);

                }
            }
        }
    }

    public class combo implements Runnable{
        @Override
        public void run(){
            waitForStart();

           while (!Thread.interrupted() && opModeIsActive()) {
               // lift arm from down to up
               if (gamepad2.x){
//                  close claw
               resources.claw.setPosition(0.6);
               resources.rhs.setPower(-0.8);
               resources.lhs.setPower(0.8);
//                  flip arm
               resources.ra.setPosition(0.1);
               resources.la.setPosition(0.1);
//                  sleep (1000)
//                  open claw


           }
               // put arm down to pick up from intake
               //if gamepad2.dpad_down{
//                  close claw
//                  flip arm down
//                  sleep (1000)
//                  open claw

//           }
            }

        }
    }


}//end of big class









