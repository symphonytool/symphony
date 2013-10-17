package eu.compassresearch.core.typechecker.api;

import java.util.List;

import org.overture.ast.types.PType;

public interface ITypeComparator
{

	/**
	 * Check whether a list of types for e.g. a pattern is assignment compatible with a list of types for e.g. an
	 * expression list. Obviously the position in the list DOES matter and the lists must have equal size.
	 * 
	 * @param to
	 *            - list of var types
	 * @param from
	 *            - list of expr types
	 * @return true whenever to and from are of the same size and for each element in to and from compatible(toElement,
	 *         fromElement) returns true. E.g. the conjunction over all pairs.
	 */
	public abstract boolean compatible(List<PType> to, List<PType> from);

	/**
	 * Check whether from is assignment compatible with to. E.g. is it okay to a := b when a:to and b:from.
	 * 
	 * @param to
	 *            - the left hand side
	 * @param from
	 *            - the right hand side
	 * @return true whenever an instance of <em>to</em> can be assigned to an instance of <em>from</em>.
	 */
	public abstract boolean compatible(PType to, PType from);

	public abstract boolean compatible(PType to, PType from, boolean paramOnly);

	/**
	 * Given two types, sub and sup, determine whether or not sub <: sup. This method determines the sub type relation
	 * between two types. It is denoted: "A <: B" and we say A is a sub type of B. The Sub type relation defines a
	 * partial ordering on the set of types: The Sub type relation is reflexive meaning A <: A The Sub type relation is
	 * transitive meaning A <: B /\ B <: C => A <: C The Sub type relation is antisymmetric meaning A <: B /\ B <: A <=>
	 * A == B
	 * 
	 * @return true <=> A <: B, false <=> not A <: B
	 * @param sub
	 *            - expected sub type
	 * @param sup
	 *            - expected sup type
	 */
	public abstract boolean isSubType(PType sub, PType sup);

}