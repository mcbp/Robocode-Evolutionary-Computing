package mp.ga;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Bot {
	
	final static String PATH = new String("C:\\robocode\\robots\\sampleex");
	final static String PACKAGE = new String("sampleex");
	final static String JARS = new String("C:\\robocode\\libs\\robocode.jar");
	
	final static String OPERATORS[] = {
			"+",
			"-",
			"*",
			"/"
	};
	
	final static String EVENTS[] = {
			"e.getBearing()",
			"e.getDistance()",
			"e.getEnergy()",
			"e.getHeading()",
			"e.getVelocity()",
	};

	String botName = new String();
	String code = new String();
	double genome[] = new double[GeneticAlgorithm.CHROMO_STRUCTURE_LENGTH];
	String phenome[] = new String[GeneticAlgorithm.CHROMO_STRUCTURE_LENGTH];
	
	public Bot(String botName, double[] genome) {
		this.botName = botName;
		this.genome = genome;
	}
	
	public void convertValues() {
		//1,4,7 OPERATORS
		for (int i = 1; i < GeneticAlgorithm.CHROMO_STRUCTURE_LENGTH; i+=3) {
			if (genome[i] < 0.25) {
				phenome[i] = OPERATORS[0]; //add
			} else if (genome[i] >= 0.25 && genome[i] < 0.50) {
				phenome[i] = OPERATORS[1]; //minus
			} else if (genome[i] >= 0.50 && genome[i] < 0.75) {
				phenome[i] = OPERATORS[2]; //multiply
			} else {
				phenome[i] = OPERATORS[3]; //divide
			}
		}
		//0,2,3,5,6,8 EVENTS
		for (int i = 0; i < GeneticAlgorithm.CHROMO_STRUCTURE_LENGTH; i++) {
			if ((i+2) % 3 != 0) {
				if (genome[i] < 0.50) {
					phenome[i] = String.valueOf(genome[i]*2);	//double value, *2 so effective range is 0-1
				} else if (genome[i] >= 0.50 && genome[i] < 0.60) {
					phenome[i] = EVENTS[0];	//Bearing
				} else if (genome[i] >= 0.60 && genome[i] < 0.70) {
					phenome[i] = EVENTS[1];	//Distance
				} else if (genome[i] >= 0.70 && genome[i] < 0.80) {
					phenome[i] = EVENTS[2];	//Energy
				} else if (genome[i] >= 0.80 && genome[i] < 0.90) {
					phenome[i] = EVENTS[3];	//Heading
				} else {
					phenome[i] = EVENTS[4];	//Velocity
				}
			}
		}
		//9 always equals double
		phenome[9] = String.valueOf(genome[9]);
	}
	
	
	public void writeCode() {
		convertValues();
		code = 	"package " + PACKAGE + ";" +

				"\nimport robocode.*;" +
		
				"\npublic class " + botName + " extends Robot {" +
				
				"\n	public static final double MAX_TURN_RATE = 10.0;" + 
				"\n	public static final double GUN_TURN_RATE = 20.0;" + 
				"\n	public static final double RADAR_TURN_RATE = 45.0;" + 
				"\n	public static final double MAX_BULLET_POWER = 3.0;" +
				
				"\n	public void run() {" +
		
				"\n		while(true) {" +
				"\n			turnGunLeft(Double.POSITIVE_INFINITY);" +
				"\n		}" +
						
				"\n	}" +
		
				"\n	public void onScannedRobot(ScannedRobotEvent e) {" +
						
				"\n		ahead((" + phenome[0] + phenome[1] + phenome[2] + "-0.5)*2*500);" +
				"\n		turnLeft(" + phenome[3] + phenome[4] + phenome[5] + "*MAX_TURN_RATE);" +
				"\n		turnGunLeft(" + phenome[6] + phenome[7] + phenome[8] + "*GUN_TURN_RATE);" +
				"\n		fire(" + phenome[9] + "*MAX_BULLET_POWER);" +
						
				"\n	}" +
		
				"\n}"
				;
		//System.out.println(code);
	}
	
	public void compileCode() {
		//Write to file
		try {
			FileWriter stream = new FileWriter(PATH + "\\" + botName + ".java");
			BufferedWriter out = new BufferedWriter(stream);
			out.write(code);
			out.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		//Compile file
		try {
			String command = "javac -cp " + JARS + " " + PATH + "\\" + botName + ".java";
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			if(process.exitValue() != 0)
				System.out.println(command + "exited with value " + process.exitValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void printMsg(String name, InputStream ins) throws Exception {
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		while((line = in.readLine()) != null){
			System.out.println(name + " " + line);
		}
	}
	
	public static void clearBots(int gen) {
		File javaFile;
		File classFile;
		
		for (int i = 0; i < GeneticAlgorithm.POPULATION_SIZE; i++) {
			javaFile = new File(PATH + "\\" + "GA_" + gen + "_" + i + ".java");
			classFile = new File(PATH + "\\" + "GA_" + gen + "_" + i + ".class");
			javaFile.delete();
			classFile.delete();
		}
	}
	

}
