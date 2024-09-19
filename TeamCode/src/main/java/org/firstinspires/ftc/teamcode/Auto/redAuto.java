package org.firstinspires.ftc.teamcode.Auto;

//RR-specific imports

// Non-RR imports
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.MecanumDrive;

// Don't edit!!!!!!!!!!!!!!!!!!!!!!
public abstract class redAuto extends LinearOpMode {
    public static class DcMotorA {
        private DcMotorEx DcMotorA;

        public DcMotorA(HardwareMap hardwareMap) {
            DcMotorA = hardwareMap.get(DcMotorEx.class, "Drive1");
        }
    }

    public class DcMotorB {
        private DcMotorEx DcMotorB;

        public DcMotorB(HardwareMap hardwareMap) {
            DcMotorB = hardwareMap.get(DcMotorEx.class, "Drive2");
        }
    }

    public class DcMotorC {
        private DcMotorEx DcMotorC;

        public DcMotorC(HardwareMap hardwareMap) {
            DcMotorC = hardwareMap.get(DcMotorEx.class, "Drive3");
        }
    }

    public class DcMotorD {
        private DcMotorEx DcMotorD;

        public DcMotorD(HardwareMap hardwareMap) {
            DcMotorD = hardwareMap.get(DcMotorEx.class, "Drive4");
        }
    }

    public void runOpMode() {
        // instantiate your MecanumDrive at a particular pose.
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(11.8, 61.7, Math.toRadians(90)));
    }
}
