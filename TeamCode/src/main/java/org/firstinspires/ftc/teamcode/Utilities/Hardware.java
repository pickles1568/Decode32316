package org.firstinspires.ftc.teamcode.Utilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware {
    public DcMotor frontLeft, backLeft, frontRight, backRight, intake, mid1, outtake;
    public Servo gate;
    public void init (HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotor.class, "LF");
        backLeft = hardwareMap.get(DcMotor.class, "LB");
        frontRight = hardwareMap.get(DcMotor.class, "RF");
        backRight = hardwareMap.get(DcMotor.class, "RB");
        intake = hardwareMap.get(DcMotor.class, "intake");
        mid1 = hardwareMap.get(DcMotor.class, "mid1");
        outtake = hardwareMap.get(DcMotor.class, "flywheel");
        gate = hardwareMap.get(Servo.class, "gate");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        outtake.setDirection(DcMotorSimple.Direction.REVERSE);
        mid1.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
