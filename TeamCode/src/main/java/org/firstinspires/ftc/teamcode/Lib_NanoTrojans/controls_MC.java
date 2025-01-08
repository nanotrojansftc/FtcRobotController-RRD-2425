package org.firstinspires.ftc.teamcode.Lib_NanoTrojans;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class controls_MC {
    // Constants for encoder counts and wheel measurements
    static final double COUNTS_PER_REVOLUTION = 537.7; // Encoder counts per revolution
    static final double WHEEL_DIAMETER_MM = 96.0; // Wheel diameter in millimeters
    static final double MM_PER_REVOLUTION = WHEEL_DIAMETER_MM * Math.PI; // Wheel circumference
    static final double COUNTS_PER_MM = COUNTS_PER_REVOLUTION / MM_PER_REVOLUTION; // Counts per millimeter
    static final double COUNTS_PER_INCH = COUNTS_PER_MM * 25.4; // Counts per inch

    //private DcMotor intake = null;
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
    //private CRServo robotLift = null;


    public controls_MC(DcMotor lsR, DcMotor lsL, Servo lhorizontal, Servo rhorizontal, DcMotor intake
                       , Servo blocker, Servo ril, Servo lil, Servo claw, Servo ra, Servo la)
    {
        //intake= intak;
        lsRight=lsR;
        lsLeft=lsL;
        lhs = lhorizontal;
        rhs = rhorizontal;
        claw =claw;

        //robotLift=robotL;
//        dcArm =arm;
    }
    public void closeclaw()
    {
        //for the claw, it is a regular motor so you set positions; you just have to keep tweaking the code and test out positions that you input.
        claw.setPosition(0.6);
    }
    public void openclaw()
    {
        //for the claw, it is a regular motor so you set positions; you just have to keep tweaking the code and test out positions that you input.
        claw.setPosition(0);
    }
    }