package org.firstinspires.ftc.teamcode.Lib_NanoTrojans;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class resources2_NanoTrojans {
    public DcMotor lsRight = null;
    public DcMotor lsLeft = null;
    public Servo rintakelift = null;
    public Servo lintakelift = null;

    //servo motors
    public Servo rhsl = null;
    public Servo lhsl = null;
//    public Servo rhsl = null;
//    //2 claws servo motors
//    public Servo lhsl = null;
    public Servo claw = null;
    public Servo arm = null;
    public Servo clawup = null;

    //2 arms servo motors
    public CRServo intakewheels = null;
//    public Servo rarm = null;
//    public Servo larm = null;
//    public Servo clawup = null;



public resources2_NanoTrojans(HardwareMap hardwareMap){
    lsRight = hardwareMap.dcMotor.get("lsRight");
    lsLeft = hardwareMap.dcMotor.get("lsLeft");

    claw = hardwareMap.servo.get("claw");
    arm = hardwareMap.servo.get("arm");
    clawup  = hardwareMap.servo.get("clawup");
    intakewheels = hardwareMap.crservo.get("intake");

    rintakelift = hardwareMap.servo.get("rintakelift");
    lintakelift = hardwareMap.servo.get("lintakelift");
//
//    lintakelift = hardwareMap.crservo.get("lintakelift");
//    rintakelift = hardwareMap.crservo.get("rintakelift");
    rhsl = hardwareMap.servo.get("rhsl");
    lhsl = hardwareMap.servo.get("lhsl");
//    larm = hardwareMap.servo.get("larm");
//    rarm = hardwareMap.servo.get("rarm");
//    clawup = hardwareMap.servo.get("clawup");

}


}
