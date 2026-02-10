package org.firstinspires.ftc.teamcode.Utilities;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware {
    public DcMotor frontLeft, backLeft, frontRight, backRight, intake, mid1, outtake;
    public Servo gate;
    public IMU imu;
    public void init (HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotor.class, "LF");
        backLeft = hardwareMap.get(DcMotor.class, "LB");
        frontRight = hardwareMap.get(DcMotor.class, "RF");
        backRight = hardwareMap.get(DcMotor.class, "RB");
        intake = hardwareMap.get(DcMotor.class, "intake");
        mid1 = hardwareMap.get(DcMotor.class, "mid1");
        outtake = hardwareMap.get(DcMotor.class, "flywheel");
        gate = hardwareMap.get(Servo.class, "gate");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        outtake.setDirection(DcMotorSimple.Direction.REVERSE);
        mid1.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        ));
        imu.initialize(parameters);
    }
}
