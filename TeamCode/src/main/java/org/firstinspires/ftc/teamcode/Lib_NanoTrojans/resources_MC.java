package org.firstinspires.ftc.teamcode.Lib_NanoTrojans;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class resources_MC {
    public DcMotor lsRight = null;
    public DcMotor lsLeft = null;
    public Servo rhs = null;
    public Servo lhs = null;

    public DcMotor intake = null;

    //servo motors
    public Servo blocker = null;
    public Servo ril = null;
    public Servo lil = null;
//    //2 claws servo motors
//    public Servo lhsl = null;
    public Servo claw = null;
    public Servo ra = null;
    public Servo la = null;



    //2 arms servo motors

//    public Servo rarm = null;
//    public Servo larm = null;
//    public Servo clawup = null;



public resources_MC(HardwareMap hardwareMap){
    lsRight = hardwareMap.dcMotor.get("lsRight");
    lsLeft = hardwareMap.dcMotor.get("lsLeft");

    intake = hardwareMap.dcMotor.get("intake");

    rhs = hardwareMap.servo.get("righthorizontal");
    lhs = hardwareMap.servo.get("lefthorizontal");

    blocker = hardwareMap.servo.get("blocker");

    lil = hardwareMap.servo.get("lintake");
    ril = hardwareMap.servo.get("rintake");

    claw = hardwareMap.servo.get("claw");

    la  = hardwareMap.servo.get("leftarmservo");
    ra = hardwareMap.servo.get("rightarmservo");



//
//    lintakelift = hardwareMap.crservo.get("lintakelift");
//    rintakelift = hardwareMap.crservo.get("rintakelift");

//    larm = hardwareMap.servo.get("larm");
//    rarm = hardwareMap.servo.get("rarm");
//    clawup = hardwareMap.servo.get("clawup");

}


}
