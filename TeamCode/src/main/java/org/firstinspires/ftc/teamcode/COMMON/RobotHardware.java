package org.firstinspires.ftc.teamcode.COMMON;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class RobotHardware {

    public HardwareMap hardwareMap;
    //Drivetrain
    public DcMotorEx dtFrontRightMotor;
    public DcMotorEx dtBackRightMotor;
    public DcMotorEx dtFrontLeftMotor;
    public DcMotorEx dtBackLeftMotor;

    //Lift
    public DcMotorEx LeftSlide;
    public DcMotorEx RightSlide;

    //Intake Motor
    public DcMotorEx Intake;

    //Outake + Claw
    public Servo Claw;
    public Servo ClawPivotLeft;
    public Servo ClawPivotRight;

    public Servo MiniArmLeft;
    public Servo MiniArmRight;




    public void RobotHardware(){
        dtFrontRightMotor = hardwareMap.get(DcMotorEx.class, "dtFrontRightMotor");
        dtFrontLeftMotor = hardwareMap.get(DcMotorEx.class, "dtFrontLeftMotor");
        dtBackRightMotor = hardwareMap.get(DcMotorEx.class, "dtBackRightMotor");
        dtBackLeftMotor = hardwareMap.get(DcMotorEx.class, "dtBackLeftMotor");

        Intake = hardwareMap.get(DcMotorEx.class, "IntakeMotor");

        LeftSlide = hardwareMap.get(DcMotorEx.class, "LeftSlide");
        RightSlide = hardwareMap.get(DcMotorEx.class, "RightSlide");

        Claw = hardwareMap.get(Servo.class, "Claw");
        ClawPivotLeft = hardwareMap.get(Servo.class,"ClawPivotLeft");
        ClawPivotRight = hardwareMap.get(Servo.class,"ClawPivotRight");
        MiniArmLeft = hardwareMap.get(Servo.class,"MiniArmLeft");
        MiniArmRight = hardwareMap.get(Servo.class,"MiniArmRight");


    }
}
