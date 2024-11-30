package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;





import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.*;


import java.util.concurrent.TimeUnit;

@TeleOp(name ="TeleOpMain1_talon", group = "TeleOp")

public class TeleOpMain3_25 extends LinearOpMode {

    private final int READ_PERIOD = 1;

    //private DriveControl_NanoTorjan driveControl;
//    private DriveControl_NanoTorjan driveControl;
    private controls2_NanoTrojans g2control;
    private resources2_NanoTrojans resources;

//    private resources_base_NanoTrojans resourcesbase;
    boolean horizontalls = false;
    boolean ls = false;
    double clawpos = 0;
    double lhslpos = 0;
    double rhslpos = 0;
    double casketpos = 0;
    double ractivepos = 0;
    double lactivepos = 0;

    BNO055IMU imu;
    public Servo claw=null;
//    private  (HardwareMap hardwareMap){
//        claw = hardwareMap.servo.get("claw");
//    }



    @Override
    public void runOpMode() throws InterruptedException {
        resources = new resources2_NanoTrojans(hardwareMap);
        //resourcesbase = new resources_base_NanoTrojans(hardwareMap);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);
        rateLimit.expire();

        telemetry.addLine("Initialized");
        telemetry.addLine("Claw Initial Position");
        telemetry.update();


        //driveControl = new DriveControl_NanoTorjan(resourcesbase.leftFront, resourcesbase.rightFront, resourcesbase.leftBack, resourcesbase.rightBack);
        //g2control = new controls2_NanoTrojans(resources.claw);

        waitForStart();
//        Thread baseControlThread = new Thread(new baseControl());
//        Thread hlsThread = new Thread(new hls());
//        Thread lsControlThread = new Thread(new lsControl());
//        Thread clawupThread = new Thread(new clawup());
//
////        Thread activeThread = new Thread(new active());
//        Thread armThread = new Thread(new arm());
        Thread clawThread = new Thread(new claw());
        //Start 2  threads
//        baseControlThread.start();
//        hlsThread.start();
//        lsControlThread.start();
////        activeThread.start();
//        armThread.start();
        clawThread.start();
//        clawupThread.start();

        //empty thead do nothing at this time
        while (opModeIsActive()) {
        }

    }

//    private class active implements Runnable {
//        boolean clawClosed = false;
//
//        @Override
//        public void run() {
//
//            waitForStart();
//        }//end of run
//    }//end of thread active intake
    public class claw implements Runnable{
        @Override
        public void run() {
            boolean casketup = false;
            boolean clawopen = false;
            waitForStart();

            while (!Thread.interrupted() && opModeIsActive()) {
                if (gamepad2.left_bumper) {
                    resources.claw.setPosition(0.0);
                }
                if (gamepad2.right_bumper) {
                    resources.claw.setPosition(1);

                }
            }
        }
    }


    private class arm implements Runnable {

        @Override
        public void run() {
            boolean casketup = false;
            boolean clawopen = false;
            waitForStart();
            while (!Thread.interrupted() && opModeIsActive()) {

//

                //for debugging
//
            }//end of while loop
        }//end of run
    }// end of class arm

}//end of big class









