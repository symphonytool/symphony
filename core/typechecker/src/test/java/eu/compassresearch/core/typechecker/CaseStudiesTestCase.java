package eu.compassresearch.core.typechecker;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.compassresearch.ast.program.AFileSource;
import eu.compassresearch.ast.program.PSource;

/**
 * Goal: Run the Type Checker on all case studies expecting success !
 * 
 * Use the environment variables {@code CASESTUDIES} to find the directory where
 * the case studies are located. If this environment variable is not located all
 * tests are skipped.
 * 
 * @author rwl
 * 
 */
@RunWith(value = Parameterized.class)
public class CaseStudiesTestCase extends AbstractTypeCheckerTestCase {

	private static String caseStudyDir = System.getenv().get("CASESTUDIES");
	private static boolean canFindCaseStudies = caseStudyDir != null;

	public CaseStudiesTestCase(List<PSource> cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages) {
		super(cmlSource, parsesOk, typesOk, errorMessages);

	}

	@Parameters(name="{index} : {0}")
	public static Collection<Object[]> parameter() {
		return testData.get(CaseStudiesTestCase.class);
	}

	// Returns the list of CML-files in a given directory
	private static File[] getCmlFiles(File dir) {
		File[] result = null;
		if (dir == null)
			return result;
		if (!dir.isDirectory())
			return null;
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".cml");
			}
		};
		result = dir.listFiles(filter);
		return result;
	}

	/**
	 * 
	 * Given the name of a CaseStudy e.g. the directory name in
	 * compasssvn/Common/CaseStudies/{@value pathRelToCaseStudiesDir} add a test
	 * including all cml-sources of the case study.
	 * 
	 * E.g.: add("SoSMpc/ideal") or add("TelephoneExchange/ClassesVersion")
	 * 
	 * @param pathRelToCaseStudiesDir
	 */
	protected static void add(String pathRelToCaseStudiesDir) {
		String caseStudySource = caseStudyDir + pathRelToCaseStudiesDir;
		File caseStudySourceFile = new File(caseStudySource);
		if (caseStudySourceFile.isDirectory()) {
			List<PSource> sources = new LinkedList<PSource>();
			for (File source : getCmlFiles(caseStudySourceFile)) {
				AFileSource fileSource = new AFileSource();
				fileSource.setFile(source);
				fileSource.setName(source.getName());
				sources.add(fileSource);
			}
			add(sources, true, true);
		}
	}

	/**
	 * This static initializer enumerates all the case studies as a directory.
	 * 
	 */
	static {
		if (canFindCaseStudies) {
			// 0//
			add("/BitRegister");
			// 1// 
			add("/Dwarf");
			// 2// 
			add("/EmergencyResponse/Expert-Led/model");
			// 3//
			add("/EmergencyResponse/Rules-Led/model");
			// 4// 
			add("/GridManager/FarmB");
			// 5// 
			add("/LeaderElection/Election-BigProcess");
			// 6// 
			add("/LeaderElection/Election-NonLeaders");
			// 7// LexNameToken
			add("/LeaderElection/Election-Original");
			// 8//
			add("/LeaderElection/LeaderElection-Ncl");
			// 9// LexNameToken
			add("/LeaderElection");
			// 10// LexNameToken
			add("/Library");
			// 11// LexNameToken
			add("/Parwsum");
			// 12// LexNameToken
			add("/RingBuffer");
			// 13// LexNameToken
			add("/Simpler BitRegister");
			// 14// LexNameToken
			add("/SoSMpc/ideal");
			// 15// LexNameToken
			add("/SoSMpc/protocol");
			// 16// LexNameToken
			add("/SoSMpc/singlesystem");
			// 17// LexNameToken
			add("/TelephoneExchange/ClassesVersion");
			// 18// LexNameToken
			add("/TelephoneExchange/Original");
			// 19// LexNameToken
			add("/TrafficManager/CityAndCars/City");
			// 20// LexNameToken
			add("/TrafficManager/CityAndCars/City-Working");
			// 21// Maybe a model error, multiple def of var i
			add("/TrafficManager/Junctions");
			// 22// LexNameToken
			add("/TravelAgent/Hotel1");
			// 23// Syntax bug
			add("/TravelAgent/Hotel2");
			// 23// Jeremy and Zoe working on master students
			//add("/Alarm");
			//24 //
			add("/MiniMondex");
			//add("/Test");

		} else {
			// ensure that the sources is initialised before the parameterized test runner gets to it
			add(new LinkedList<PSource>(),true,true);
			System.out.println("CASESTUDIES Not defined skipping tests.");
		}
	}

}
