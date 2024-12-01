package org.firstinspires.ftc.teamcode.Lib_NanoTrojans;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class resources3_NanoTrojans {
    public DcMotor lsRight = null;
    public DcMotor lsLeft = null;
    public DcMotor rlift = null;
    public DcMotor llift = null;

    //servo motors
    public Servo arm = null;
    public Servo elbow = null;
//    public Servo rhsl = null;
//    //2 claws servo motors
//    public Servo lhsl = null;
    public Servo claw = null;
    public Servo wrist = null;


    //2 arms servo motors

//    public Servo rarm = null;
//    public Servo larm = null;
//    public Servo clawup = null;



public resources3_NanoTrojans(HardwareMap hardwareMap){
    lsRight = hardwareMap.dcMotor.get("lsRight");
    lsLeft = hardwareMap.dcMotor.get("lsLeft");

    llift = hardwareMap.dcMotor.get("llift");
    rlift = hardwareMap.dcMotor.get("rlift");

    claw = hardwareMap.servo.get("claw");
    arm = hardwareMap.servo.get("arm");
    wrist  = hardwareMap.servo.get("wrist");
    elbow = hardwareMap.servo.get("elbow");


//
//    lintakelift = hardwareMap.crservo.get("lintakelift");
//    rintakelift = hardwareMap.crservo.get("rintakelift");

//    larm = hardwareMap.servo.get("larm");
//    rarm = hardwareMap.servo.get("rarm");
//    clawup = hardwareMap.servo.get("clawup");

}


}
