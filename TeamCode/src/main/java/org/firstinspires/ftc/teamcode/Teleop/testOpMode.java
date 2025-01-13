package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@TeleOp(name="opMode", group="Linear OpMode")
public class testOpMode extends LinearOpMode {
    static final int LEFT_EXTENDER_ENDSTOP = 1695;
    static final int RIGHT_EXTENDER_ENDSTOP = 1695;
    //Jesus recommended it and said it would work
   static final double EXTENDER_SCALING = 1.0/3;
   static final double WRIST_SCALING_DEGREES = 9.0/300;
   static final double GRIPPER_SCALING_DEGREES = 1.0/300;
   private BNO055IMU imu;

    public void runOpMode() {
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        DcMotor rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        DcMotor leftExtender = hardwareMap.get(DcMotor.class, "leftExtender");
        DcMotor rightExtender = hardwareMap.get(DcMotor.class, "rightExtender");
        Servo wristServo = hardwareMap.get(Servo.class, "wristServo");
        Servo gripperServo = hardwareMap.get(Servo.class, "gripperServo");
        leftExtender.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

        while (!isStopRequested() && !imu.isGyroCalibrated()) {
            telemetry.addLine("IMU not calibrated...");
            telemetry.update();
            idle();
        }

        while (opModeIsActive()) {
            // Get stick inputs from gamepad
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y; // Invert y to match coordinate system
            double turn = gamepad1.right_stick_x;

            // Get the current heading, taking into account the initial heading
            double initialHeading = 0;
            double currentHeading = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle - initialHeading;


            // Apply rotation matrix
            double rotatedX = x * Math.cos(-currentHeading) - y * Math.sin(-currentHeading);
            double rotatedY = x * Math.sin(-currentHeading) + y * Math.cos(-currentHeading);

            // Calculate motor speeds
            double frontLeftSpeed = rotatedX + rotatedY + turn;
            double frontRightSpeed = -rotatedX + rotatedY - turn;
            double backLeftSpeed = -rotatedX + rotatedY + turn;
            double backRightSpeed = rotatedX + rotatedY - turn;

            // Set motor powers, making sure they are in range
            leftFront.setPower(Math.max(-1.0, Math.min(frontLeftSpeed, 1.0)));
            rightFront.setPower(Math.max(-1.0, Math.min(frontRightSpeed, 1.0)));
            leftBack.setPower(Math.max(-1.0, Math.min(backLeftSpeed, 1.0)));
            rightBack.setPower(Math.max(-1.0, Math.min(backRightSpeed, 1.0)));

            // Add telemetry data for debugging
            telemetry.addData("Heading", Math.toDegrees(currentHeading));
            telemetry.addData("x", x);
            telemetry.addData("y", y);
            telemetry.addData("rotatedX", rotatedX);
            telemetry.addData("rotatedY", rotatedY);
            telemetry.addData("frontLeft", leftFront.getPower());
            telemetry.addData("frontRight", rightFront.getPower());
            telemetry.addData("backLeft", leftBack.getPower());
            telemetry.addData("backRight", rightBack.getPower());

        }
    }
}









