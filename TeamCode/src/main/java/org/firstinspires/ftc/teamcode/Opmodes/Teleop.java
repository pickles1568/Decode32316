package org.firstinspires.ftc.teamcode.Opmodes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Utilities.Hardware;

@TeleOp
public class Teleop extends OpMode {
    final boolean Debug = true;

    Hardware robot = new Hardware();

    @Override
    public void init() {
        robot.init(hardwareMap);



    }


    @Override
    public void loop() {
        robot.outtake.setPower(0.65);
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double rx = gamepad1.right_stick_x;

        if (Debug) {
            telemetry.addData("Y drive", y);
            telemetry.addData("X drive", x);
            telemetry.addData("RX drive", rx);
        }
        if (gamepad1.y) {robot.imu.resetYaw();}
        double botHeading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        if (Debug) {telemetry.addData("Heading", robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));}
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;
        if (Debug) {
            telemetry.addData("Front Left Power", frontLeftPower);
            telemetry.addData("Back Left Power", backLeftPower);
            telemetry.addData("Front Right Power", frontRightPower);
            telemetry.addData("Back Right Power", backRightPower);
        }
        if (gamepad2.left_trigger == 1) {
            robot.gate.setPosition(0.65);
            robot.intake.setPower(1);
            robot.mid1.setPower(1);
        }
        else {
            robot.intake.setPower(0);
            robot.mid1.setPower(0);
        }
        if (gamepad2.right_trigger == 1) {
            robot.gate.setPosition(0.2);
            robot.mid1.setPower(1);
        }
        robot.frontLeft.setPower(frontLeftPower);
        robot.backLeft.setPower(backLeftPower);
        robot.frontRight.setPower(frontRightPower);
        robot.backRight.setPower(backRightPower);
        telemetry.update();
    }
}
