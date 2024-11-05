package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="opMode", group="Linear OpMode")
public class opMode extends LinearOpMode {
    public void runOpMode() {
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        DcMotor rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();
        while (opModeIsActive()){

            if (gamepad1.left_stick_y != 0){
                leftFront.setPower(gamepad1.left_stick_y);
                leftBack.setPower(gamepad1.left_stick_y);
                rightBack.setPower(gamepad1.left_stick_y);
                rightFront.setPower(gamepad1.left_stick_y);
            }

            else if (gamepad1.right_stick_x != 0){
                leftFront.setPower(-gamepad1.right_stick_x);
                leftBack.setPower(-gamepad1.right_stick_x);
                rightBack.setPower(gamepad1.right_stick_x);
                rightFront.setPower(gamepad1.right_stick_x);

            }

            else if (gamepad1.left_stick_y == 0) {
                leftFront.setPower(0);
                leftBack.setPower(0);
                rightBack.setPower(0);
                rightFront.setPower(0);
            }
           if (gamepad1.dpad_left){
                leftFront.setPower(1);
                leftBack.setPower(-1);
                rightBack.setPower(1);
                rightFront.setPower(-1);
            }
           if (gamepad1.dpad_right){
                leftFront.setPower(-1);
                leftBack.setPower(1);
                rightBack.setPower(-1);
                rightFront.setPower(1);
            }
           if (gamepad1.a){
               leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
               leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
               rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
               rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
               leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
               leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
               rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
               rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
           }
            telemetry.addData("leftFront Pos:", leftFront.getCurrentPosition());
            telemetry.addData("leftBack Pos:", leftBack.getCurrentPosition());
            telemetry.addData("rightBack Pos:", rightBack.getCurrentPosition());
            telemetry.addData("rightFront  Pos:", rightFront.getCurrentPosition());
            telemetry.update();
        }
    }
}
