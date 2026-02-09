package org.firstinspires.ftc.teamcode.Opmodes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Utilities.Hardware;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;


@Autonomous
public class CloseBlue extends OpMode {
    private Follower follower;
    private Timer pathTimer, opModeTimer;

    public enum PathState {
        Drive_StartPos_ShootPos,
        Shoot_Preload,
        Drive_ShootPos_AlignedPos,
        Drive_AlignedPos_LoadedPos,
        Drive_LoadedPos_ShootPos,
        Shoot_Secondary,
        Drive_ShootPos_OffLaunchPos
    }
    PathState pathstate;
    private final Pose StartPos = new Pose(19.884, 122.829, Math.toRadians(139));
    private final Pose ShootPos = new Pose(53.684, 89.028, Math.toRadians(134));
    private final Pose AlignedPos = new Pose(43.729, 83.241, Math.toRadians(180));
    private final Pose LoadedPos = new Pose(20, 83.241, Math.toRadians(180));
    private final Pose OffLaunchPos = new Pose(49.437, 71.665, Math.toRadians(180));
    Hardware robot = new Hardware();

    private PathChain driveStartPosShootPos, driveShootPosAlignedPos, driveAlignedPosLoadedPos, driveLoadedPosShootPos, driveShootPosOffLaunchPos;

    public void buildPaths() {
        driveStartPosShootPos = follower.pathBuilder()
                .addPath(new BezierLine(StartPos, ShootPos))
                .setLinearHeadingInterpolation(StartPos.getHeading(), ShootPos.getHeading())
                .build();
        driveShootPosAlignedPos = follower.pathBuilder()
                .addPath(new BezierLine(StartPos, AlignedPos))
                .setLinearHeadingInterpolation(ShootPos.getHeading(), AlignedPos.getHeading())
                .build();
        driveAlignedPosLoadedPos = follower.pathBuilder()
                .addPath(new BezierLine(AlignedPos, LoadedPos))
                .setLinearHeadingInterpolation(AlignedPos.getHeading(), LoadedPos.getHeading())
                .build();
        driveLoadedPosShootPos = follower.pathBuilder()
                .addPath(new BezierLine(LoadedPos, ShootPos))
                .setLinearHeadingInterpolation(LoadedPos.getHeading(), ShootPos.getHeading())
                .build();
        driveShootPosOffLaunchPos = follower.pathBuilder()
                .addPath(new BezierLine(ShootPos, OffLaunchPos))
                .setLinearHeadingInterpolation(ShootPos.getHeading(), OffLaunchPos.getHeading())
                .build();
    }

    public void statePathUpdate() {
        switch (pathstate) {
            case Drive_StartPos_ShootPos:
                telemetry.addLine("Drive_StartPos_ShootPos");
                follower.followPath(driveStartPosShootPos, true);
                setPathState(PathState.Shoot_Preload);
                break;
            case Shoot_Preload:
                if (!follower.isBusy()) {
                    telemetry.addLine("ShootPreload");
                    robot.outtake.setPower(0.8);
                    //Timer Delay
                    robot.gate.setPosition(0.2);
                    robot.mid1.setPower(1);
                    //Timer Delay
                    robot.outtake.setPower(0);
                    robot.gate.setPosition(0.65);
                    robot.mid1.setPower(0);
                    setPathState(PathState.Drive_ShootPos_AlignedPos);
                }
                break;
            case Drive_ShootPos_AlignedPos:
                if (!follower.isBusy()) {
                    telemetry.addLine("Drive_ShootPos_AlignedPos");
                    follower.followPath(driveShootPosAlignedPos, true);
                    setPathState(PathState.Drive_AlignedPos_LoadedPos);
                }
                break;
            case Drive_AlignedPos_LoadedPos:
                if (!follower.isBusy()) {
                    telemetry.addLine("Drive_AlignedPos_LoadedPos");
                    follower.followPath(driveAlignedPosLoadedPos, true);
                    setPathState(PathState.Drive_LoadedPos_ShootPos);
                }
                break;
            case Drive_LoadedPos_ShootPos:
                if (!follower.isBusy()) {
                    telemetry.addLine("Drive_LoadedPos_ShootPos");
                    follower.followPath(driveLoadedPosShootPos, true);
                    setPathState(PathState.Shoot_Secondary);
                }
                break;
            case Shoot_Secondary:
                if (!follower.isBusy()) {
                    telemetry.addLine("Shoot_Secondary");
                    robot.outtake.setPower(0.8);
                    //Timer Delay
                    robot.gate.setPosition(0.2);
                    robot.mid1.setPower(1);
                    //Timer Delay
                    robot.outtake.setPower(0);
                    robot.gate.setPosition(0.65);
                    robot.mid1.setPower(0);
                    setPathState(PathState.Drive_ShootPos_OffLaunchPos);
                }
                break;
            case Drive_ShootPos_OffLaunchPos:
                if (!follower.isBusy()) {
                    telemetry.addLine("Drive_ShootPos_OffLaunchPos");
                    follower.followPath(driveShootPosOffLaunchPos, true);
                }
                break;
            default:
                telemetry.addLine("Done");
                break;
        }
    }

    public void setPathState(PathState newState) {
        pathstate = newState;
        pathTimer.resetTimer();
    }
    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.gate.setPosition(0.65);
        pathstate = PathState.Drive_StartPos_ShootPos;
        pathTimer = new Timer();
        opModeTimer = new Timer();

        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setPose(StartPos);

    }
    public void start() {
        opModeTimer.resetTimer();
        setPathState(pathstate);
    }

    @Override
    public void loop() {
        follower.update();
        statePathUpdate();
        telemetry.addData("PathState", pathstate.toString());
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading",follower.getPose().getHeading());
        telemetry.addData("Path time", pathTimer.getElapsedTimeSeconds());
    }
}
