package eu.compassresearch.core.analysis.modelchecker.formula.graph.event;

import eu.compassresearch.core.analysis.modelchecker.formula.graph.TipoT;

public class CommEv implements Event{
	
	String str1;
	String str2;
	TipoT str3;
	
	
	public CommEv(String str1, String str2, TipoT str3) {
		this.str1 = str1;
		this.str2 = str2;
		this.str3 = str3;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((str1 == null) ? 0 : str1.hashCode());
		result = prime * result + ((str2 == null) ? 0 : str2.hashCode());
		result = prime * result + ((str3 == null) ? 0 : str3.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommEv other = (CommEv) obj;
		if (str1 == null) {
			if (other.str1 != null)
				return false;
		} else if (!str1.equals(other.str1))
			return false;
		if (str2 == null) {
			if (other.str2 != null)
				return false;
		} else if (!str2.equals(other.str2))
			return false;
		if (str3 == null) {
			if (other.str3 != null)
				return false;
		} else if (!str3.equals(other.str3))
			return false;
		return true;
	}

}
