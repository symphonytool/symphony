package eu.compassresearch.core.analysis.theoremprover.thms;

import java.util.Iterator;
import java.util.LinkedList;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.AFieldField;

import eu.compassresearch.core.analysis.theoremprover.utils.ThmTypeUtil;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmStringVisitor;
import eu.compassresearch.core.analysis.theoremprover.visitors.string.ThmVarsContext;

public class ThmRecType extends ThmDecl {

	//Private components of record types
	private String name;
	private String invCall = "";
	private String invDefn = "";
	private String fields = "";
	private String tags = "";
	private String defn = "";
	
	public ThmRecType(String name, LinkedList<AFieldField> flds)
	{
		this.name = name;
		this.tags = createTagsStr();
		this.fields = createFieldsStr(flds);
		this.defn = createDefnStr(flds);
	}
	
	public ThmRecType(String name, LinkedList<AFieldField> flds, String invDefn, String invname, LinkedList<String> invparams)
	{
		this.name = name;
		this.tags = createTagsStr();
		this.fields = createFieldsStr(flds);
		this.invCall = createInvCall(invname, invparams);
		this.invDefn = invDefn + "\n\n";
		this.defn = createDefnStr(flds);
	}

	private String createTagsStr() {
		StringBuilder sbtag = new StringBuilder();
	
		sbtag.append("typedef " + name + "_Tag = \"{True}\" by auto \n");
		sbtag.append("instantiation " + name + "_Tag :: tag\n");
		sbtag.append("begin \n");
		sbtag.append(ThmTypeUtil.isaType + " \"tagName_" + name + "_Tag (x::" + name + "_Tag) = \'\'"+ name +"\'\'\" \n");
		sbtag.append("instance  \n");
		sbtag.append("by (intro_classes, metis (full_types) Abs_" + name +"_Tag_cases singleton_iff) \n");
		sbtag.append("end \n\n");
	
		String tagStr = sbtag.toString();
		
		return tagStr;
	}
	
	
	private String createFieldsStr(LinkedList<AFieldField> flds) {
		
		StringBuilder sbfield = new StringBuilder();
		
		int count = 1;
		for (AFieldField field: flds)
		{
			String fldNm = field.getTag();
			String fldTp = "";
			try {
				fldTp = field.getType().apply(new ThmStringVisitor(), new ThmVarsContext());
			} catch (AnalysisException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//ThmTypeUtil.getIsabelleType(field.getType());
	
			sbfield.append(ThmTypeUtil.isaAbbr + "\"" + fldNm +"_fld " + ThmTypeUtil.isaEquiv + " MkField TYPE(" + name + "_Tag) #" + count+ ThmTypeUtil.typeDelim + fldTp + ThmTypeUtil.typeDelim + "\"\n");  
		
			count ++;
		}
		sbfield.append("\n");

		for (AFieldField field: flds)
		{
			String fldNm = field.getTag();
		
			sbfield.append(ThmTypeUtil.isaAbbr + "\"" + fldNm + " " + ThmTypeUtil.isaEquiv + " SelectRec " + fldNm + "_fld\"\n");  
				
		}
		sbfield.append("\n");
				
		String fldStr = sbfield.toString();
		return fldStr;
	}

	private String createInvCall(String invname, LinkedList<String> invparams) {
		if (invname != "")
		{
			//generate the list of parameters
			String paramList = "";
			String funccalllist = "";
			for (Iterator<String> p = invparams.listIterator(); p.hasNext(); ) {
				String pname = p.next();
				paramList = paramList + pname + " ";
				funccalllist = funccalllist + "&"+ pname;
				if(p.hasNext())
				{
					funccalllist = funccalllist + ", ";
				}
			}
			//make the invariant call
			String invString  = " inv " + paramList + "== " + invname +"(" + funccalllist + ")";
			
			return invString;
		}
		else return "";
	}

	private String createDefnStr(LinkedList<AFieldField> flds) {
		StringBuilder sbdefn = new StringBuilder();
		
		sbdefn.append(ThmTypeUtil.isaType +"\n");
		sbdefn.append("\"" + name + " " + ThmTypeUtil.isaEquiv + ThmTypeUtil.typeDelim + "[");
		
		
		//For each type in the product, add it to the string
		for (Iterator<AFieldField> itr = flds.listIterator(); itr.hasNext(); ) {
			AFieldField fld = itr.next();
			sbdefn.append(fld.getTag() + "_fld");
			
			//If there are remaining fields, add a ","
			if(itr.hasNext()){	
				sbdefn.append(", ");
			}
		}
		sbdefn.append("]" + invCall + ThmTypeUtil.typeDelim + "\"\n" + tacHook(name) + "\n\n");
		sbdefn.append(ThmTypeUtil.isaType + " \"mk_" + name + ThmTypeUtil.isaEquiv + "MkRec "+ name+ "\"\n" + tacHook("mk_"+name));
		
		String defnStr = sbdefn.toString();
		return defnStr;
	}
	
	/*****
	 * Return a string for the record type. This string is rather complex, as needs to declare each field as a tag
	 *****/
	@Override
	public String toString(){

		return tags + fields + invDefn + defn;
	}
	
}

