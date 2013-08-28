package eu.compassresearch.core.analysis.modelchecker.formula.graph.event;


public class IOCom implements Event {
		
		private String number;
		private String str;
		
		
		public IOCom(String number,String str) {
			
			this.number = number;
			this.str = str;
		}
		
		@Override
		public String toString() {
			return str;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((number == null) ? 0 : number.hashCode());
			result = prime * result + ((str == null) ? 0 : str.hashCode());
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
			IOCom other = (IOCom) obj;
			if (number == null) {
				if (other.number != null)
					return false;
			} else if (!number.equals(other.number))
				return false;
			if (str == null) {
				if (other.str != null)
					return false;
			} else if (!str.equals(other.str))
				return false;
			return true;
		}

}
