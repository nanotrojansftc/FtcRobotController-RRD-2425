package org.firstinspires.ftc.teamcode.Auto;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public abstract class Drive extends LinearOpMode {
    DcMotor DcMotorA = hardwareMap.get(DcMotor.class, "Drive1");
    DcMotor DcMotorB = hardwareMap.get(DcMotor.class, "Drive2");
    DcMotor DcMotorC = hardwareMap.get(DcMotor.class, "Drive3");
    DcMotor DcMotorD = hardwareMap.get(DcMotor.class, "Drive4");
    DcMotorC.setDirection(DcMotorSimple.Direction.REVERSE);

    public void driveTiles (int input) {

    }


}
