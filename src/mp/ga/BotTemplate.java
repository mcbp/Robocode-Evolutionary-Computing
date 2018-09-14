package mp.ga;

import robocode.HitByBulletEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class BotTemplate extends Robot {

	public void run() {

		while(true) {
			turnGunLeft(Double.POSITIVE_INFINITY);
		}
		
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		
		ahead(1);
		turnLeft(1);
		turnGunLeft(1);
		turnRadarLeft(1);
		fire(1);
		
	}

}
