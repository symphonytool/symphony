package eu.compassresearch.ide.ui.editor.syntax;

public class Wrapper<T>
{

	public static <T> Wrapper<T> newInstance(T value, String str)
	{
		return new Wrapper<T>(value, str);
	}

	public Wrapper(T value, String str)
	{
		this.description = str;
		this.value = value;
	}

	public T value;
	private String description;

	public boolean isClass(Class<?> cls)
	{
		return cls.isInstance(value);
	}

	public String toString()
	{
		return description;
	}

	public String getDescription()
	{
		return description;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		Wrapper other = (Wrapper) obj;
		if (value == null)
		{
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}