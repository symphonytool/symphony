package eu.compassresearch.core.interpreter.utility;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.overture.interpreter.values.IntegerValue;
import org.overture.interpreter.values.SetValue;
import org.overture.interpreter.values.TupleValue;
import org.overture.interpreter.values.ValueList;
import org.overture.interpreter.values.ValueSet;

public class TestMath
{

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testGetCrossProductWithTwoSets()
	{

		ValueSet set1 = new ValueSet();
		set1.add(new IntegerValue(1));
		set1.add(new IntegerValue(2));

		ValueSet set2 = new ValueSet();
		set2.add(new IntegerValue(3));
		set2.add(new IntegerValue(4));

		List<SetValue> sets = new LinkedList<SetValue>();
		sets.add(new SetValue(set1));
		sets.add(new SetValue(set2));

		// List<List<Value>> result = Math.getCrossProduct(sets);
		SetValue result = SetMath.getCrossProduct(sets);

		SetValue expectedResult = new SetValue();

		ValueList vl = new ValueList();
		vl.add(new IntegerValue(1));
		vl.add(new IntegerValue(3));
		expectedResult.values.add(new TupleValue(vl));

		vl = new ValueList();
		vl.add(new IntegerValue(1));
		vl.add(new IntegerValue(4));
		expectedResult.values.add(new TupleValue(vl));

		vl = new ValueList();
		vl.add(new IntegerValue(2));
		vl.add(new IntegerValue(3));
		expectedResult.values.add(new TupleValue(vl));

		vl = new ValueList();
		vl.add(new IntegerValue(2));
		vl.add(new IntegerValue(4));
		expectedResult.values.add(new TupleValue(vl));

		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testGetCrossProductWithThreeSets()
	{

		ValueSet set1 = new ValueSet();
		set1.add(new IntegerValue(1));
		set1.add(new IntegerValue(2));

		ValueSet set2 = new ValueSet();
		set2.add(new IntegerValue(3));
		set2.add(new IntegerValue(4));

		ValueSet set3 = new ValueSet();
		set3.add(new IntegerValue(2));
		set3.add(new IntegerValue(5));

		List<SetValue> sets = new LinkedList<SetValue>();
		sets.add(new SetValue(set1));
		sets.add(new SetValue(set2));
		sets.add(new SetValue(set3));

		// List<List<Value>> result = Math.getCrossProduct(sets);
		SetValue result = SetMath.getCrossProduct(sets);

		SetValue expectedResult = new SetValue();

		ValueList vl = new ValueList();
		vl.add(new IntegerValue(1));
		vl.add(new IntegerValue(3));
		vl.add(new IntegerValue(2));
		expectedResult.values.add(new TupleValue(vl));

		vl = new ValueList();
		vl.add(new IntegerValue(1));
		vl.add(new IntegerValue(3));
		vl.add(new IntegerValue(5));
		expectedResult.values.add(new TupleValue(vl));

		vl = new ValueList();
		vl.add(new IntegerValue(1));
		vl.add(new IntegerValue(4));
		vl.add(new IntegerValue(2));
		expectedResult.values.add(new TupleValue(vl));

		vl = new ValueList();
		vl.add(new IntegerValue(1));
		vl.add(new IntegerValue(4));
		vl.add(new IntegerValue(5));
		expectedResult.values.add(new TupleValue(vl));

		vl = new ValueList();
		vl.add(new IntegerValue(2));
		vl.add(new IntegerValue(3));
		vl.add(new IntegerValue(2));
		expectedResult.values.add(new TupleValue(vl));

		vl = new ValueList();
		vl.add(new IntegerValue(2));
		vl.add(new IntegerValue(3));
		vl.add(new IntegerValue(5));
		expectedResult.values.add(new TupleValue(vl));

		vl = new ValueList();
		vl.add(new IntegerValue(2));
		vl.add(new IntegerValue(4));
		vl.add(new IntegerValue(2));
		expectedResult.values.add(new TupleValue(vl));

		vl = new ValueList();
		vl.add(new IntegerValue(2));
		vl.add(new IntegerValue(4));
		vl.add(new IntegerValue(5));
		expectedResult.values.add(new TupleValue(vl));

		Assert.assertEquals(expectedResult, result);
	}

}
