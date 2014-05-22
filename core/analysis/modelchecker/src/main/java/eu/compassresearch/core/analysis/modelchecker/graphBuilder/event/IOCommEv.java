package eu.compassresearch.core.analysis.modelchecker.graphBuilder.event;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;


public class IOCommEv implements Event {
		
	private String number;
	private String str;
	private String exp;
	private Type valueType;
	
	
	
	public IOCommEv(String number, String str, String exp, Type valueType) {
		super();
		this.number = number;
		this.str = str;
		this.exp = exp;
		this.valueType = valueType;
	}

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

	
		
		
		public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public Type getValueType() {
		return valueType;
	}

	public void setValueType(Type valueType) {
		this.valueType = valueType;
	}

		@Override
		public String toString() {
			String result = str;
			if(this.valueType.toString().length() > 0){
				result = result + this.valueType.toString();
			}
			return result;
		}
		
		@Override
		public int hashCode() {
			return ("ev" + number.toString()).hashCode();
		}
		@Override
		public boolean equals(Object obj) {
			boolean result = false;
			if(obj instanceof IOCommEv){
				IOCommEv other = (IOCommEv) obj;
				result = this.getNumber().equals(other.getNumber()) && this.getStr().equals(other.getStr());
			}
			return result;
		}

}
