package org.firstinspires.ftc.teamcode.Auto;

//RR-specific imports

// Non-RR imports
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Robot.Drive;
@Autonomous(name="RoadRunnerAuto", group="Robot")
// Don't edit!!!!!!!!!!!!!!!!!!!!!!
public abstract class RoadRunnerAuto extends LinearOpMode {
    // instantiate your MecanumDrive at a particular pose.
    Pose2d initialPose = new Pose2d(11.8, 61.7, Math.toRadians(90));
    MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);


    @Override
    public void runOpMode() {
        waitForStart();
        Action trajectoryAction1 = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(46.96, -5.61), Math.toRadians(254.48))
                .build();
        Actions.runBlocking(
                new SequentialAction(
                        trajectoryAction1
                )
        );
    }
    }
