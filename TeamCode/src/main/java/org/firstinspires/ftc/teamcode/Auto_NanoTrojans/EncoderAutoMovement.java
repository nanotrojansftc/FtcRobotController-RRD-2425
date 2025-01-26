package org.firstinspires.ftc.teamcode.Auto_NanoTrojans;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.controls_MC;
import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.resources_MC;
import org.firstinspires.ftc.teamcode.Lib_NanoTrojans.resources_base_NanoTrojans;
import org.firstinspires.ftc.teamcode.Teleop.TeleOpMainMC;

@Autonomous(name = "Encoder Auto Movement", group = "Linear Opmode")
public class EncoderAutoMovement extends LinearOpMode {

    // Declare motors
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    // Constants for motion calculations
    static final double COUNTS_PER_MOTOR_REV = 537.6;  // REV HD Hex Motor
    static final double DRIVE_GEAR_REDUCTION = 1.0;   // No gear reduction
    static final double WHEEL_DIAMETER_INCHES = 4.0;  // Wheel diameter in inches
    static final double COUNTS_PER_INCH =
            (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                    (WHEEL_DIAMETER_INCHES * Math.PI);

    private resources_MC resources;
    private controls_MC control;
    private resources_base_NanoTrojans resourcesbase;

    private boolean lockhls = true;

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
        while (opModeIsActive() && !isStopRequested()) {

            //lock the hls
            Thread hlsControlLockThread = new Thread(new EncoderAutoMovement.hlsLock());
            hlsControlLockThread.start();

            //blocker up
            resources.blocker.setPosition(0.58);

            //close claw
            //resources.claw.setPosition(0.6);


            //***  move forward to the cage
            // Drive forward 5 feet (60 inches)
            drive(0.75, 24);
            sleep(1000);



            //**   move away from the cage
            drive(0.75,-5);
            sleep(1000);

            sleep(500);

            //**   strafe to the right
            strafe(0.75, -32);

            sleep(500);
            drive(0.75, 15);

            sleep(500);
            strafe(0.75,-8);

            sleep(500);
            drive(0.75,-40);

            drive(0.75, 45);


            strafe(0.75,-15);

            drive(0.75,-45);
            drive(0.75,40);

            //Third Strafe
//            strafe(0.4, -17);
//            drive(0.75, -50);

//            resources.hanger.setPower(1);
//            drive(1,50);
//            rightTurn(1,-30);
//            drive(0.75,30);
//            sleep(1000);
//            resources.hanger.setPower(0);
//            requestOpModeStop();
        }


        resources.rhs.setPower(0.5);
        resources.lhs.setPower(-0.5);

        // Drive forward 5 feet (60 inches)
        drive(0.75, 25);
        sleep(2000);
        drive(0.75,-5);
        sleep(500);


        strafe(0.75, -30);

        sleep(500);
        drive(0.75, 30);

        sleep(500);
        strafe(0.75,-8);

        sleep(500);
        drive(0.75,-53);

        drive(0.75, 53);

        strafe(0.75,-18);

        drive(0.75,-50);
        drive(0.75,10);
    }

    private class hlsLock implements Runnable {
        boolean clawClosed = false;
        //double hspower1 , hspower2;

        @Override
        public void run() {
            boolean lock = false;

            waitForStart();
            while (!Thread.interrupted() && opModeIsActive() && lockhls)
            {
                //prevent hls to move out
                resources.rhs.setPower(0.5);
                resources.lhs.setPower(-0.5);
                //blocker up
                resources.blocker.setPosition(0.58);
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
