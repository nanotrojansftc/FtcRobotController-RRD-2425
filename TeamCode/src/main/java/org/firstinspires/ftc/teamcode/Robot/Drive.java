package org.firstinspires.ftc.teamcode.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive{
    HardwareMap hMap = null;
    DcMotor dcMotorA = null;
    DcMotor dcMotorB = null;
    DcMotor dcMotorC = null;
    DcMotor dcMotorD = null;
    //hi

            public Drive(HardwareMap hardwareMap){
                hMap = hardwareMap;

                dcMotorA = hMap.get(DcMotorEx.class, "leftFront");
                dcMotorB = hMap.get(DcMotorEx.class, "leftBack");
                dcMotorC = hMap.get(DcMotorEx.class, "rightBack");
                dcMotorD = hMap.get(DcMotorEx.class, "rightFront");
                dcMotorC.setDirection(DcMotorSimple.Direction.REVERSE);
                dcMotorD.setDirection(DcMotorSimple.Direction.REVERSE);
                dcMotorA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                dcMotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                dcMotorC.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                dcMotorD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                dcMotorA.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                dcMotorB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                dcMotorC.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                dcMotorD.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

   public void driveTiles(int Tiles) {

       dcMotorA.setTargetPosition(-993 * Tiles);
       dcMotorB.setTargetPosition(-1044 * Tiles);
       dcMotorC.setTargetPosition(-1037 * Tiles);
       dcMotorD.setTargetPosition(-1061 * Tiles);
       dcMotorA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       dcMotorB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       dcMotorC.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       dcMotorD.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       dcMotorA.setPower(1);
       dcMotorB.setPower(1);
       dcMotorC.setPower(1);
       dcMotorD.setPower(1);
       /*if (dcMotorA.getCurrentPosition() == -993 * Tiles
               && dcMotorB.getCurrentPosition() == -1044 * Tiles
               && dcMotorC.getCurrentPosition() == -1037 * Tiles
               && dcMotorD.getCurrentPosition() == -1061 * Tiles){
                    dcMotorA.setPower(0);
                    dcMotorB.setPower(0);
                    dcMotorC.setPower(0);
                    dcMotorD.setPower(0);
                    dcMotorA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    dcMotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    dcMotorC.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    dcMotorD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



       }
       */


    }

   public void setRotateDegrees(int Deg) {
       dcMotorA.setTargetPosition(23 * Deg);
       dcMotorB.setTargetPosition(18 *Deg);
       dcMotorC.setTargetPosition(-20 *Deg);
       dcMotorD.setTargetPosition(-20 *Deg);
       dcMotorA.setPower(1);
       dcMotorB.setPower(1);
       dcMotorC.setPower(1);
       dcMotorD.setPower(1);
       dcMotorA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       dcMotorB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       dcMotorC.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       dcMotorD.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (dcMotorA.getCurrentPosition() == 23 * Deg
                    && dcMotorB.getCurrentPosition() == 18 * Deg
                    && dcMotorC.getCurrentPosition() == -20 * Deg
                    && dcMotorD.getCurrentPosition() == -20 * Deg){
                        dcMotorA.setPower(0);
                        dcMotorB.setPower(0);
                        dcMotorC.setPower(0);
                        dcMotorD.setPower(0);
                        dcMotorA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        dcMotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        dcMotorC.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        dcMotorD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


       }
    }

   public String gatherMotorPos(){
        return "dcMotorA Position " + dcMotorA.getCurrentPosition() +
                "\ndcMotorB Position " + dcMotorB.getCurrentPosition() +
                "\ndcMotorC Position " + dcMotorC.getCurrentPosition() +
                "\nDcMotorD Position " + dcMotorD.getCurrentPosition();


    }


}

