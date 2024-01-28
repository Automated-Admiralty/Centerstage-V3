package org.firstinspires.ftc.teamcode.COMMON.TOOLS;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
@TeleOp
public class PIDTUNNINGCLASS extends OpMode {
    private PIDController controller;
    public static double p = 0, i = 0, d = 0;
    public static double f = 0;

    public static int target = 0;

    private final double ticks_per_deg =384.5/360;
    //TODO Change motor to change what we are tunning.
    private DcMotorEx LeftSlide;
    private DcMotorEx RightSlide;
    @Override
    public void init() {
        controller = new PIDController(p,i,d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        LeftSlide = hardwareMap.get(DcMotorEx.class,"LeftSlide");
        LeftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RightSlide = hardwareMap.get(DcMotorEx.class,"RightSlide");
        RightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        controller.setPID(p,i,d);
        int armPos = LeftSlide.getCurrentPosition();
        double pid = controller.calculate(armPos,target);
        double ff = Math.cos(Math.toRadians(target/ticks_per_deg))*f;
        double power = pid + ff;

        LeftSlide.setPower(power);
        RightSlide.setPower(power);

        telemetry.addData("pos", armPos);
        telemetry.addData( "target", target);
        telemetry.update();
    }
}
