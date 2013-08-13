package eu.compassresearch.ide.rttmbt;

import org.eclipse.core.expressions.PropertyTester;

public class RttMbtPropertyTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		System.out.println("RttMbtPropertyTester: property:" + property);		
		if (property.equals("isRttMbtProject")) {
			if (receiver != null) {
				System.out.println("isRttMbtProject: receiver:" + receiver.toString());
			}
			if (args != null) {
				System.out.println("isRttMbtProject: args:" + args.toString());
			}
			if (expectedValue != null) {
				System.out.println("isRttMbtProject: receiver:" + expectedValue.toString());
			}
			return true;
		}
		if (property.equals("isTestGenerationContext")) {
			return false;
		}
		if (property.equals("isRttTestprocedure")) {
			return false;
		}
		return true;
	}

}
