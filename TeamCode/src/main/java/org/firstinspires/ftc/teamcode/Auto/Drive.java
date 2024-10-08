package org.firstinspires.ftc.teamcode.Auto;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class Drive extends LinearOpMode {
    HardwareMap hMap = null;
    static DcMotor DcMotorA = null;
    static DcMotor DcMotorB = null;
    static DcMotor DcMotorC = null;
    static DcMotor DcMotorD = null;

    public void init(HardwareMap hardwareMap){
        hMap = hardwareMap;

        DcMotorA = hMap.get(DcMotor.class, "Drive1");
        DcMotorB = hMap.get(DcMotor.class, "Drive2");
        DcMotorC = hMap.get(DcMotor.class, "drive3");
        DcMotorD = hMap.get(DcMotor.class, "Drive4");
        DcMotorC.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotorA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DcMotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DcMotorC.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DcMotorD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DcMotorA.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DcMotorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DcMotorC.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DcMotorD.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //Servo gripServo = hardwareMap.get(Servo.class, "S0");
    }

    public static void driveTiles(int input) {
        DcMotorA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        DcMotorB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        DcMotorC.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        DcMotorD.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        DcMotorA.setTargetPosition(1);
        DcMotorB.setTargetPosition(1);
        DcMotorC.setTargetPosition(1);
        DcMotorD.setTargetPosition(1);



    }


}
