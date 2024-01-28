package org.firstinspires.ftc.teamcode.OPMODE;

import static java.lang.Math.abs;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.COMMON.RobotHardware;
import org.firstinspires.ftc.teamcode.COMMON.TOOLS.PIDCONTROLLERTOOL;
import java.lang.Math;

@TeleOp
public class Tele extends LinearOpMode {

    Gamepad currentGamepad1 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();

    // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
    public int SlideTarget = 0;

    public enum SlideState{
        RETRACTED(0),
        EXTEND1(300),
        EXTEND2(600),
        EXTEND3(900),
        EXTEND4(1200),
        MAXEXTEND(1500);
        private final int ticks;
        private SlideState(final int ticks) { this.ticks = ticks; }

        // TODO: add the rest for yourself here
    }
    public int SlideStateCounter = 0;
    SlideState CurrentSlideState = SlideState.RETRACTED;
    @Override
    public void runOpMode() throws InterruptedException {
        RobotHardware Robot = new RobotHardware(hardwareMap);

        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);
        PIDCONTROLLERTOOL SlideController = new PIDCONTROLLERTOOL(.0555,0,.001,.02,384.5/360,Robot.LeftSlide);//TODO tune these values in the test file
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // Store gamepad
            previousGamepad1.copy(currentGamepad1);
            //Slides
            if (currentGamepad1.left_bumper && !previousGamepad1.a) {
                if(SlideStateCounter != 5){
                    SlideStateCounter += 1;
                }else if(SlideStateCounter == 5){
                    SlideStateCounter = SlideStateCounter;
                }

            }
            if(currentGamepad1.right_bumper && !previousGamepad1.right_bumper){
                if(SlideStateCounter != 0) {
                    SlideStateCounter -= 1;
                }
            }
            Robot.LeftSlide.setPower(SlideController.calculatePid(SlideTarget));
            Robot.RightSlide.setPower(SlideController.calculatePid(SlideTarget));



            if (currentGamepad1.left_bumper && !previousGamepad1.a) SlideStateCounter++;

            if (currentGamepad1.right_bumper && !previousGamepad1.right_bumper) SlideStateCounter = abs(SlideStateCounter - 1);

            Robot.LeftSlide.setPower(SlideController.calculatePid(SlideTarget));
            Robot.RightSlide.setPower(SlideController.calculatePid(SlideTarget));

            CurrentSlideState = SlideState.values()[SlideStateCounter % SlideState.values().length];
            SlideTarget = CurrentSlideState.ticks;







            //Drive
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepad1.options) {
                imu.resetYaw();
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(abs(rotY) + abs(rotX) + abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY -rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            Robot.dtFrontLeftMotor.setPower(frontLeftPower);
            Robot.dtBackLeftMotor.setPower(backLeftPower);
            Robot.dtFrontRightMotor.setPower(-frontRightPower);
            Robot.dtBackRightMotor.setPower(-backRightPower);

            //Store gamepad
            currentGamepad1.copy(gamepad1);

        }
    }
}


