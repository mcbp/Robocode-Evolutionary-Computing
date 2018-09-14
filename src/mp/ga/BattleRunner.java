package mp.ga;

import robocode.control.*;
import robocode.control.events.*;
import robocode.BattleResults;

//
// Application that demonstrates how to run two sample robots in Robocode using the
// RobocodeEngine from the robocode.control package.
//
// @author Flemming N. Larsen
//
public class BattleRunner {
	
	final static String[] opbots = {
			"sample.RamFire",
			"sample.MyFirstRobot",
			"sample,sample.SpinBot"
	};
	RobocodeEngine engine;
	BattlefieldSpecification battlefield;
	BattleObserver battleObserver;
	
	public BattleRunner() {
		//Engine
        engine = new RobocodeEngine(new java.io.File("C:/Robocode"));
        RobocodeEngine.setLogMessagesEnabled(false);
        engine.setVisible(false);
        
        //Battlefield size
        battlefield = new BattlefieldSpecification(800, 600);
        
        //Observe battle
        battleObserver = new BattleObserver();
        engine.addBattleListener(battleObserver);
    }
	
	public double runBattle(String GAbot, int rounds) {

        //engine = new RobocodeEngine(new java.io.File("C:/Robocode"));
		double scoreTotal = 0;
		BattleResults[] results;
		
		for (int i = 0; i < opbots.length; i++) {
			
	        RobotSpecification[] selectedRobots = engine.getLocalRepository(GAbot + "," + opbots[i]);
	        BattleSpecification battleSpec = new BattleSpecification(rounds, battlefield, selectedRobots);
	        engine.runBattle(battleSpec, true);

	        //Get results of GAbot
	        results = battleObserver.getResults();
	        
	        scoreTotal += results[0].getScore();
			
		}
		engine.close();

        return scoreTotal/opbots.length;	
    }
	
	public double[] runBattle(String GAbot[], String opbot, int rounds ) {
		
		engine = new RobocodeEngine(new java.io.File("C:/Robocode"));
		double scoreList[] = new double[GAbot.length];
		BattleResults[] results;
		
		//Loop for all GAbots
		for (int i = 0; i < GAbot.length; i++) {
			
	        // Run the battle
	        RobotSpecification[] selectedRobots = engine.getLocalRepository(GAbot[i] + "," + opbot);
	        BattleSpecification battleSpec = new BattleSpecification(rounds, battlefield, selectedRobots);
	        engine.runBattle(battleSpec, true);
	        
	        results = battleObserver.getResults();
	        scoreList[i] = results[0].getScore();
		}
		return scoreList;
	}
}

//
// Our private battle listener for handling the battle event we are interested in.
//
class BattleObserver extends BattleAdaptor {

	robocode.BattleResults[] results;
	
    // Called when the battle is completed successfully with battle results
    public void onBattleCompleted(BattleCompletedEvent e) {
    	results = e.getIndexedResults();
        //System.out.println("-- Battle has completed --");
        
        // Print out the sorted results with the robot names
        //System.out.println("Battle results:");
        //for (robocode.BattleResults result : e.getSortedResults()) {
            //System.out.println("  " + result.getTeamLeaderName() + ": " + result.getScore());
        //}
    }

    // Called when the game sends out an information message during the battle
    public void onBattleMessage(BattleMessageEvent e) {
        //System.out.println("Msg> " + e.getMessage());
    }

    // Called when the game sends out an error message during the battle
    public void onBattleError(BattleErrorEvent e) {
        //System.out.println("Err> " + e.getError());
    }
    
    public BattleResults[] getResults() {
    	return results;
    }
}