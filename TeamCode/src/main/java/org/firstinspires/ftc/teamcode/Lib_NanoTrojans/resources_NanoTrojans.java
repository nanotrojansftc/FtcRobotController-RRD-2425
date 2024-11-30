package org.firstinspires.ftc.teamcode.Lib_NanoTrojans;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class resources_NanoTrojans {
    public DcMotor lsRight = null;
    public DcMotor lsLeft = null;
    public CRServo rintakelift = null;
    public CRServo lintakelift = null;

    //servo motors
//    public Servo rhsl = null;
//    public Servo lhsl = null;
    public CRServo rhsl = null;
    //2 claws servo motors
    public CRServo lhsl = null;
    public Servo claw = null;

    //2 arms servo motors
    public CRServo intakewheels = null;
    public Servo casket = null;



public resources_NanoTrojans(HardwareMap hardwareMap){
    lsRight = hardwareMap.dcMotor.get("lsRight");
    lsLeft = hardwareMap.dcMotor.get("lsLeft");

    claw = hardwareMap.servo.get("claw");
    intakewheels = hardwareMap.crservo.get("intake");

    rintakelift = hardwareMap.crservo.get("rintakelift");
    lintakelift = hardwareMap.crservo.get("lintakelift");

    lintakelift = hardwareMap.crservo.get("lintakelift");
    rintakelift = hardwareMap.crservo.get("rintakelift");
    rhsl = hardwareMap.crservo.get("rhsl");
    lhsl = hardwareMap.crservo.get("lhsl");
    casket = hardwareMap.servo.get("casket");

}


}
