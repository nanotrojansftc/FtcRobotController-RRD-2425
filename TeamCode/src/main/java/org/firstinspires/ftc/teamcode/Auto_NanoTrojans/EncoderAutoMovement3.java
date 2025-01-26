package org.firstinspires.ftc.teamcode.Auto_NanoTrojans;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.controls_MC;
import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.resources_MC;
import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.resources_base_NanoTrojans;
import org.firstinspires.ftc.teamcode.Teleop.TeleOpMainMC;

@Autonomous(name = "Encoder Auto Movement3", group = "Linear Opmode")
public class EncoderAutoMovement3 extends LinearOpMode {

    // Declare motors
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    // Constants for motion calculations
    static final double COUNTS_PER_MOTOR_REV = 537.6;  // REV HD Hex Motor
    static final double DRIVE_GEAR_REDUCTION = 1.0;   // No gear reduction
    //static final double WHEEL_DIAMETER_INCHES = 4.0;  // Wheel diameter in inches
    static final double WHEEL_DIAMETER_INCHES = 4;  // Wheel diameter in inches
    static final double COUNTS_PER_INCH =
            (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                    (WHEEL_DIAMETER_INCHES * Math.PI);

    private resources_MC resources;
    private controls_MC control;
    private resources_base_NanoTrojans resourcesbase;

    private boolean runIntake = false;
    private boolean moveLsDown = false;

    private boolean moveLsUp = false;

    private boolean moveArmUp = false;

    private boolean lockHls = false;

    @Override
    public void runOpMode() {
        // Initialize hardware
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        // Set motor directions
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        // Reset encoders and set to RUN_USING_ENCODER mode
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        resources = new resources_MC(hardwareMap);
        resourcesbase = new resources_base_NanoTrojans(hardwareMap);

        waitForStart();
        Thread runIntakeThread = new Thread(new EncoderAutoMovement3.runIntake());
        Thread lsDownThread = new Thread(new EncoderAutoMovement3.lsDown());
        Thread lsUpThread = new Thread(new EncoderAutoMovement3.lsUp());
        Thread armUpThread = new Thread(new EncoderAutoMovement3.armUp());
        Thread hlsLockThread = new Thread(new EncoderAutoMovement3.hlsLock());
        
        runIntakeThread.start();
        lsDownThread.start();
        lsUpThread.start();
        armUpThread.start();
        hlsLockThread.start();
        while (opModeIsActive() && !isStopRequested())
        {
            //*************************  drop the first one into basket *************//
            //close claw
            resources.claw.setPosition(0.6);

            //lock hls use a seperate thread
            lockHls = true;

            //lower blocker
            resources.blocker.setPosition(0.58);

            //drive forward and strafe to basket
            drive(0.75,-10);
            strafe(0.75,-27);
            //liner slides up in different thread during running
            moveLsUp = true;
            sleep(200);
            strafe(0.15,-7);

            //wait for slides to go up fully
            sleep(1600);

            // arm up
            resources.ra.setPosition(0.01);
            //resources.la.setPosition(0.2);
            resources.la.setPosition(0.01);
            //wait for arm to go up fully
            sleep(2000);

            //open claw
            resources.claw.setPosition(0.3);
            sleep(300);

            //arm down
            resources.ra.setPosition(1);
            resources.la.setPosition(1);
            sleep(300);

            //stop LS move up
            moveLsUp = false;

            //move lS down in different thread
            moveLsDown = true;
            sleep(1000);

            //***********************************************************//
            //********* try to grab the Second one from ground ***********//

            //unlock hls use a seperate thread
            lockHls = false;

            // put intake down
            resources.ril.setPosition(0.95);
            resources.lil.setPosition(0.0);
            // strafe right for adjust
            strafe(0.75,16);
            drive(0.5,-4);

            //******* Let's start to pick up the second pixel  ************//
            //push horizontal linear slides forward
            resources.rhs.setPower(-0.205);
            resources.lhs.setPower(0.205);
            //run intake in different thread
            runIntake = true;   //run the intake then move out linear slide
            //drive(0.5,-20);
            sleep(1500);

            // retract horizontal linear slides again
            resources.rhs.setPower(1);
            resources.lhs.setPower(-1);
            //stop intake thread
            runIntake = false;
            // wait a bit
            sleep(800);

            //lower stop
            resources.blocker.setPosition(0.58);

            //give hs a little power
            resources.rhs.setPower(0.1);
            resources.lhs.setPower(-0.1);

            //put intake up
            resources.ril.setPosition(0.4);
            resources.lil.setPosition(0.6);
            // wait a bit
            sleep(200);

            //run a litter bit intake
            resources.intake.setPower(0.5);
            lockHls = true;
            //sleep(500);
            resources.intake.setPower(0);
            sleep (300);

            //close claw
            resources.claw.setPosition(0.6);
            lockHls = false;

            //*********** finish picked up the second pixel ***********//

            // move back towards the basket
            // drive(1,25);
            strafe(1, -17);

            //move up ls
            moveLsUp = true;
            //align with the wall
            strafe(0.15, -5);
            //let's LS move half way
            sleep(2000);

            //stop LS move up
            moveLsUp = false;

            // arm up for the second pixel
            resources.ra.setPosition(0.01);
            resources.la.setPosition(0.01);
            //wait for arm to go up fully
            sleep(2000);

            //open claw
            resources.claw.setPosition(0.3);
            sleep(300);

            //arm down
            resources.ra.setPosition(1);
            resources.la.setPosition(1);

            sleep(300);

            //stop LS move up
            //moveLsUp = false;

            //move lS down in different thread
            moveLsDown = true;
            sleep(1000);

             //******************************************************//
            // ***************  3rd one ******************************
            // THIRD RECTANGULAR PRISM/SAMPLES
            strafe(0.75,6.5);
            //drive(0.75,-5);
            
            // put intake down
            resources.ril.setPosition(0.95);
            resources.lil.setPosition(0.0);

            //******* Let's start to pick up a pixel  ************//


            //push horizontal linear slides forward
            resources.rhs.setPower(-0.275);
            resources.lhs.setPower(0.275);

            //run intake in different thread
            runIntake = true;   //run the intake then move out linear slide

            //drive(0.5,-20);
            sleep(1500);

            // retract horizontal linear slides again
            resources.rhs.setPower(1);
            resources.lhs.setPower(-1);

            //stop intake thread
            runIntake = false;

            // wait a bit
            sleep(800);


            //lower stop
            resources.blocker.setPosition(0.58);

            //keep hls locked
            resources.rhs.setPower(0.1);
            resources.lhs.setPower(-0.1);

            //put intake up
            resources.ril.setPosition(0.4);
            resources.lil.setPosition(0.6);
            // wait a bit
            sleep(500);

            //run a litter bit intake
            resources.intake.setPower(0.5);
            lockHls = true;
            sleep(500);
            resources.intake.setPower(0);
            sleep (300);

            //close claw
            resources.claw.setPosition(0.6);
            lockHls = false;

            //*********** finish picked up the Third pixel ***********//

            // move back towards the basket
            //drive(1,6);
            strafe(0.75,-6);

            //move ls up
            moveLsUp = true;
            sleep(2000);

            //stop LS move up
            moveLsUp = false;

//          arm up
            resources.ra.setPosition(0.01);
            resources.la.setPosition(0.2);
            //wait for arm to go up fully
            sleep(2000);

            //open claw
            resources.claw.setPosition(0.3);
            sleep(500);
            //arm down
            resources.ra.setPosition(1);
            resources.la.setPosition(1);
            // linear slides down

            //moveLsUp = false;
            moveLsDown = true;

            isStopRequested();
            requestOpModeStop();

        }

        // Drive forward 5 feet (60 inches)

    }

    private class hlsLock implements Runnable {
        boolean clawClosed = false;
        //double hspower1 , hspower2;

        @Override
        public void run() {
            boolean lock = false;

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive() )
            {
                if(lockHls) {
                    //prevent hls to move out
                    resources.rhs.setPower(0.4);
                    resources.lhs.setPower(-0.4);
                }
            }//end of while
        }//end of run
    }//end of thread horizontal linear slide control

    private class runIntake implements Runnable {

        @Override
        public void run() {
            boolean lock = false;

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive())
            {
                if( runIntake) {

                    //blocker up
                    resources.blocker.setPosition(1);

                    //Run Intake
                    resources.intake.setPower(0.75);
                }
            }//end of while
        }//end of run
    }//end of thread horizontal linear slide control

    private class lsDown implements Runnable {

        @Override
        public void run() {
            boolean lock = false;

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive())
            {
                if( moveLsDown) {

                    resources.lsRight.setPower(1);
                    resources.lsLeft.setPower(-1);
                    sleep(2000);
                    resources.lsRight.setPower(0);
                    resources.lsLeft.setPower(0);
                    //try to stall ls
                    //resources.lsRight.setPower(-0.1);
                    //resources.lsLeft.setPower(0.1);
                    moveLsDown = false;
                }
            }//end of while
        }//end of run
    }//end of thread horizontal linear slide control

    private class lsUp implements Runnable {

        @Override
        public void run() {
            boolean lock = false;

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive())
            {
                if( moveLsUp) {

                    resources.lsRight.setPower(-1);
                    resources.lsLeft.setPower(1);
                    sleep(2000);

                     //try to stall linear slides
                    resources.lsRight.setPower(-0.1);
                    resources.lsLeft.setPower(0.1);


                    moveLsUp = false;
                }
            }//end of while
        }//end of run
    }//end of thread horizontal linear slide control

    private class armUp implements Runnable {

        @Override
        public void run() {
            boolean lock = false;

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive())
            {
                if( moveArmUp) {

                    // arm up
                    resources.ra.setPosition(0.01);
                    resources.la.setPosition(0.01);
                    //wait for arm to go up fully
                    sleep(2500);
                    moveArmUp = false;
                }
            }//end of while
        }//end of run
    }//end of thread horizontal linear slide control


    /**
     * Drives the robot forward or backward.
     *
     * @param speed Speed of motion (positive for forward, negative for backward)
     * @param distanceInInches Distance to move in inches
     */
    public void drive(double speed, double distanceInInches) {
        int targetPosition = (int) (distanceInInches * COUNTS_PER_INCH);

        leftFront.setTargetPosition(leftFront.getCurrentPosition() + targetPosition);
        rightFront.setTargetPosition(rightFront.getCurrentPosition() + targetPosition);
        leftBack.setTargetPosition(leftBack.getCurrentPosition() + targetPosition);
        rightBack.setTargetPosition(rightBack.getCurrentPosition() + targetPosition);

        // Set to RUN_TO_POSITION mode
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set motor power
        leftFront.setPower(speed);
        rightFront.setPower(speed);
        leftBack.setPower(speed);
        rightBack.setPower(speed);

        // Wait until motion is complete
        while (opModeIsActive() &&
                (leftFront.isBusy() && rightFront.isBusy() &&
                        leftBack.isBusy() && rightBack.isBusy())) {
            telemetry.addData("Path", "Driving to %7d", targetPosition);
            telemetry.update();
        }

        // Stop all motion
        stopMotors();
    }

    /**
     * Strafes the robot to the left or right.
     *
     * @param speed Speed of motion (positive for right, negative for left)
     * @param distanceInInches Distance to strafe in inches
     */
    public void strafe(double speed, double distanceInInches) {
        int targetPosition = (int) (distanceInInches * COUNTS_PER_INCH);

        leftFront.setTargetPosition(leftFront.getCurrentPosition() - targetPosition);
        rightFront.setTargetPosition(rightFront.getCurrentPosition() - targetPosition);
        leftBack.setTargetPosition(leftBack.getCurrentPosition() + targetPosition);
        rightBack.setTargetPosition(rightBack.getCurrentPosition() + targetPosition);

        // Set to RUN_TO_POSITION mode
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set motor power
        leftFront.setPower(speed);
        rightFront.setPower(speed);
        leftBack.setPower(speed);
        rightBack.setPower(speed);

        // Wait until motion is complete
        while (opModeIsActive() &&
                (leftFront.isBusy() && rightFront.isBusy() &&
                        leftBack.isBusy() && rightBack.isBusy())) {
            telemetry.addData("Path", "Strafing to %7d", targetPosition);
            telemetry.update();
        }

        // Stop all motion
        stopMotors();
    }

    public void rightTurn(double speed, double distanceInInches) {
        int targetPosition = (int) (distanceInInches * COUNTS_PER_INCH);

        leftFront.setTargetPosition(leftFront.getCurrentPosition() - targetPosition);
        rightFront.setTargetPosition(rightFront.getCurrentPosition() + targetPosition);
        leftBack.setTargetPosition(leftBack.getCurrentPosition() - targetPosition);
        rightBack.setTargetPosition(rightBack.getCurrentPosition() + targetPosition);

        // Set to RUN_TO_POSITION mode
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set motor power
        leftFront.setPower(speed);
        rightFront.setPower(speed);
        leftBack.setPower(speed);
        rightBack.setPower(speed);

        // Wait until motion is complete
        while (opModeIsActive() &&
                (leftFront.isBusy() && rightFront.isBusy() &&
                        leftBack.isBusy() && rightBack.isBusy())) {
            telemetry.addData("Path", "Strafing to %7d", targetPosition);
            telemetry.update();
        }

        // Stop all motion
        stopMotors();
    }

    public void leftTurn(double speed, double distanceInInches) {
        int targetPosition = (int) (distanceInInches * COUNTS_PER_INCH);

        leftFront.setTargetPosition(leftFront.getCurrentPosition() + targetPosition);
        rightFront.setTargetPosition(rightFront.getCurrentPosition() - targetPosition);
        leftBack.setTargetPosition(leftBack.getCurrentPosition() + targetPosition);
        rightBack.setTargetPosition(rightBack.getCurrentPosition() - targetPosition);

        // Set to RUN_TO_POSITION mode
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set motor power
        leftFront.setPower(speed);
        rightFront.setPower(speed);
        leftBack.setPower(speed);
        rightBack.setPower(speed);

        // Wait until motion is complete
        while (opModeIsActive() &&
                (leftFront.isBusy() && rightFront.isBusy() &&
                        leftBack.isBusy() && rightBack.isBusy())) {
            telemetry.addData("Path", "Strafing to %7d", targetPosition);
            telemetry.update();
        }

        // Stop all motion
        stopMotors();
    }

    /**
     * Stops all motors.
     */
    private void stopMotors() {
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }
}
