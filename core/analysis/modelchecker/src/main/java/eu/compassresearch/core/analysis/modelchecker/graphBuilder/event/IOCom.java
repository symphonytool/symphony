package eu.compassresearch.core.analysis.modelchecker.graphBuilder.event;


public class IOCom implements Event {
		
		public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

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
		public boolean equals(Object obj) {
			boolean result = false;
			if(obj instanceof IOCom){
				IOCom other = (IOCom) obj;
				result = this.getNumber().equals(other.getNumber()) && this.getStr().equals(other.getStr());
			}
			return result;
		}

}
