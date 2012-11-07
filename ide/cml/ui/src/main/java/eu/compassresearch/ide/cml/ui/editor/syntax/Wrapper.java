package eu.compassresearch.ide.cml.ui.editor.syntax;



public  class Wrapper<T> {
	
	public static <T> Wrapper<T> newInstance(T value, String str) {
		return new Wrapper<T>(value, str);
	}

	public Wrapper(T value, String str) {
		this.description = str;
		this.value = value;
	}

	public T value;
	private String description;
	
	public boolean isClass(Class<?> cls) {
		return cls.isInstance(value);
	}

	public String toString() {
		return description;
	}

	public String getDescription() {
	    return description;
	}
}