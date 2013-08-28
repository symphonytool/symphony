package eu.compassresearch.theoremprover.thms;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.types.AFieldField;
import eu.compassresearch.theoremprover.utils.ThmTypeUtil;

public class ThmRecType extends ThmDecl {

	public String name;
	public LinkedList<AFieldField> fields;
	public String invariant = "";
	

	public ThmRecType(String name, LinkedList<AFieldField> fields)
	{
		this.name = name;
		this.fields = fields;
	}
	
	public ThmRecType(String name, LinkedList<AFieldField> fields, String inv)
	{
		this.name = name;
		this.fields = fields;
		setInv(inv);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setInv(String inv) {
		this.invariant = inv;
	}

	public String getName() {
		return name;
	}
	
	public String getInv() {
		return invariant;
	}
	
	public String toString(){

		StringBuilder sb = new StringBuilder();
		
		sb.append("typedef " + name + "_Tag = \"{True}\" by auto \n");
		sb.append("instantiation " + name + "_Tag :: tag\n");
		sb.append("begin \n");
		sb.append(ThmTypeUtil.isaType + " \"tagName_" + name + "_Tag (x::" + name + "_Tag) = \'\'"+ name +"\'\'\" \n");
		sb.append("instance  \n");
		sb.append("by (intro_classes, metis (full_types) Abs_" + name +"_Tag_cases singleton_iff) \n");
		sb.append("end \n\n");

		int count = 1;
		for (AFieldField field: fields)
		{
			String fldNm = field.getTag();
			String fldTp = ThmTypeUtil.getIsabelleType(field.getType());
	
			sb.append(ThmTypeUtil.isaAbbr + "\"" + fldNm +"_fld " + ThmTypeUtil.isaEquiv + " MkField TYPE(" + name + "_Tag) #" + count+ ThmTypeUtil.typeDelim + fldTp + ThmTypeUtil.typeDelim + "\"\n");  
		
			count ++;
		}
		sb.append("\n");

		for (AFieldField field: fields)
		{
			String fldNm = field.getTag();
			//String fldTp = ThmTypeUtil.getIsabelleType(field.getType());
		
			sb.append(ThmTypeUtil.isaAbbr + "\"" + fldNm + " " + ThmTypeUtil.isaEquiv + " SelectRec " + fldNm + "_fld\"\n");  
				
		}
		sb.append("\n");
				
		sb.append(ThmTypeUtil.isaType +"\n");
		sb.append("\"" + name + " " + ThmTypeUtil.isaEquiv + ThmTypeUtil.typeDelim + "[");
		
		
		//For each type in the product, add it to the string
		for (Iterator<AFieldField> itr = fields.listIterator(); itr.hasNext(); ) {
			AFieldField fld = itr.next();
			sb.append(fld.getTag() + "_fld");
			
			//If there are remaining fields, add a ","
			if(itr.hasNext()){	
				sb.append(", ");
			}
		}
		sb.append("]" + invariant + ThmTypeUtil.typeDelim + "\"\n" + tacHook(name) + "\n\n");
		sb.append(ThmTypeUtil.isaType + " \"mk_" + name + ThmTypeUtil.isaEquiv + "MkRec "+ name+ "\"\n" + tacHook("mk_"+name));
		
		return sb.toString();
	}
	
}

