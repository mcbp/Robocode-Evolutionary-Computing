package sampleex;
import robocode.*;
public class GA_99_27_MichaelPriest extends Robot {
	public static final double MAX_TURN_RATE = 10.0;
	public static final double GUN_TURN_RATE = 20.0;
	public static final double RADAR_TURN_RATE = 45.0;
	public static final double MAX_BULLET_POWER = 3.0;
	public void run() {
		while(true) {
			turnGunLeft(Double.POSITIVE_INFINITY);
		}
	}
	public void onScannedRobot(ScannedRobotEvent e) {
		ahead((e.getVelocity()-e.getHeading()-0.5)*2*500);
		turnLeft(0.43351276773603653*e.getVelocity()*MAX_TURN_RATE);
		turnGunLeft(0.35028808888774043-e.getVelocity()*GUN_TURN_RATE);
		fire(0.9999888373977383*MAX_BULLET_POWER);
	}
}
