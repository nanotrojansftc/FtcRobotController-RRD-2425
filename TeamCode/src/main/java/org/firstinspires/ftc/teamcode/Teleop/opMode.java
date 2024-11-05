package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="opMode", group="Linear OpMode")
public class opMode extends LinearOpMode {
    static final int LEFT_EXTENDER_ENDSTOP = 1;
    static final int RIGHT_EXTENDER_ENDSTOP = 1;
    //Jesus recommended it and said it would work
   static final float EXTENDER_SCALING = 1.0f/3;
    public void runOpMode() {
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        DcMotor rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        DcMotor rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        DcMotor leftExtender = hardwareMap.get(DcMotor.class, "leftExtender");
        DcMotor rightExtender = hardwareMap.get(DcMotor.class, "rightExtender");

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
           if(gamepad2.left_stick_y != 0){
               if(leftExtender.getCurrentPosition() > LEFT_EXTENDER_ENDSTOP){
                   leftExtender.setPower(0);
               }
               else{
                   leftExtender.setPower(gamepad2.left_stick_y * EXTENDER_SCALING);
               }

               if(rightExtender.getCurrentPosition() > RIGHT_EXTENDER_ENDSTOP){
                   rightExtender.setPower(0);
               }
               else{
                   rightExtender.setPower(gamepad2.left_stick_y * EXTENDER_SCALING);
               }


           }


            telemetry.addData("leftFront Pos:", leftFront.getCurrentPosition());
            telemetry.addData("leftBack Pos:", leftBack.getCurrentPosition());
            telemetry.addData("rightBack Pos:", rightBack.getCurrentPosition());
            telemetry.addData("rightFront  Pos:", rightFront.getCurrentPosition());
            telemetry.update();
        }
    }
}
