package eu.compassresearch.core.analysis.modelchecker.graphBuilder.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.GivenProc;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.ProcDef;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.State;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.BBinding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.NullBinding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.SingleBind;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.event.BasicEvent;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.event.CommEv;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.event.Event;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.event.IOCommEv;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.event.Tau;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.event.Tock;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.expression.EqualBooleanExpression;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.expression.LessThanBooleanExpression;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.expression.NotEqualBooleanExpression;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.param.DPar;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.param.NoPar;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.param.Param;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.param.SPar;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Assing;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Chaos;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.ConditionalChoice;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Divergence;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.ExtChoice;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.ExtraChoice;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.GenPar;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Hide;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.IPar;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.IParll;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.IntChoice;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Let;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Operation;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Par;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Parll;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Prefix;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Process;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.ProcessCall;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.SeqComposition;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Skip;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Stop;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.UntimedInterrupt;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.UntimedTimeout;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.VarDeclaration;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.transition.Transition;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.IR;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Int;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Nat;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.ProdType;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Str;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.TypeValue;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Void;

public class Utilities {
	
	//adicionar  
	//primitive tIntrpt        ::= (lProc : CMLProcess, t:Natural, rProc : CMLProcess).   //Timed Interrupt 
    //primitive tTimeout       ::= (lProc : CMLProcess, t:Natural, rProc : CMLProcess).   //timed Timeout  
		    
	public static final String DEADLOCK = "Deadlock";
	public static final String LIVELOCK = "Livelock";
	public static final String NONDETERMINISM = "Nondeterminism";
	public static final String DEADLOCK_STATE_COLOUR = "\"#FF9696\"";
	public static final String NONDETERMINISTIC_STATE_COLOUR = "\"#FFC864\"";
	public static final String LIVELOCK_STATE_COLOUR = "\"#64FFFF\"";
	
	protected static Hashtable<String, Constructor> constructors;
	
	
	
	static{
		constructors = new Hashtable<String, Constructor>();
		//BASICPROCESS
		constructors.put(Constructor.Skip.id, Constructor.Skip);
		constructors.put(Constructor.Stop.id, Constructor.Stop);
		constructors.put(Constructor.Chaos.id, Constructor.Chaos);
		constructors.put(Constructor.Div.id, Constructor.Div);
		//PROCESS
		constructors.put(Constructor.Prefix.id, Constructor.Prefix);
		constructors.put(Constructor.Hide.id, Constructor.Hide);
		constructors.put(Constructor.SeqComposition.id, Constructor.SeqComposition);
		constructors.put(Constructor.ProcessCall.id, Constructor.ProcessCall);
		constructors.put(Constructor.Operation.id, Constructor.Operation);
		constructors.put(Constructor.Assing.id, Constructor.Assing);
		constructors.put(Constructor.UntimedInterrupt.id, Constructor.UntimedInterrupt);
		constructors.put(Constructor.UntimedTimeout.id, Constructor.UntimedTimeout);
		

		//CHOICE
		constructors.put(Constructor.IntChoice.id, Constructor.IntChoice);
		constructors.put(Constructor.ExtChoice.id, Constructor.ExtChoice);
		constructors.put(Constructor.ExtraChoice.id, Constructor.ExtraChoice);
		constructors.put(Constructor.CondChoice.id, Constructor.CondChoice);
		
		//PARAM
		constructors.put(Constructor.Spar.id, Constructor.Spar);
		constructors.put(Constructor.NoPar.id, Constructor.NoPar);
		constructors.put(Constructor.Dpar.id, Constructor.Dpar);
		
		
		//PARALLEL
		constructors.put(Constructor.Par.id, Constructor.Par);
		constructors.put(Constructor.GenPar.id, Constructor.GenPar);
		
		//some of these constructores will be removed
		constructors.put(Constructor.IPar.id, Constructor.IPar);
		constructors.put(Constructor.IParll.id, Constructor.IParll);
		constructors.put(Constructor.Parll.id, Constructor.Parll);
		
		constructors.put(Constructor.State.id, Constructor.State);
		constructors.put(Constructor.Transition.id, Constructor.Transition);
		constructors.put(Constructor.GivenProc.id, Constructor.GivenProc);
		constructors.put(Constructor.ProcDef.id, Constructor.ProcDef);
		
		//VARIABLE DECLARATION
		constructors.put(Constructor.VarDecl.id, Constructor.VarDecl);
		constructors.put(Constructor.Let.id, Constructor.Let);
		
		//EVENT
		constructors.put(Constructor.BasicEvent.id, Constructor.BasicEvent);
		constructors.put(Constructor.IOComm.id, Constructor.IOComm);
		constructors.put(Constructor.Tau.id, Constructor.Tau);
		constructors.put(Constructor.Tock.id, Constructor.Tock);
		constructors.put(Constructor.CommEv.id, Constructor.CommEv);

		
		//BINDING
		constructors.put(Constructor.NullBind.id, Constructor.NullBind);
		constructors.put(Constructor.BBind.id, Constructor.BBind);
		constructors.put(Constructor.SingleBind.id, Constructor.SingleBind);
		
		//TYPE
		constructors.put(Constructor.IntType.id, Constructor.IntType);
		constructors.put(Constructor.StrType.id, Constructor.StrType);
		constructors.put(Constructor.NatType.id, Constructor.NatType);
		constructors.put(Constructor.IRType.id, Constructor.IRType);
		constructors.put(Constructor.Void.id, Constructor.Void);
		constructors.put(Constructor.TypeValue.id, Constructor.TypeValue);
		constructors.put(Constructor.ProdType.id, Constructor.ProdType);
		
		//EXPRESSION
		constructors.put(Constructor.EqualExpression.id, Constructor.EqualExpression);
		constructors.put(Constructor.NotEqualExpression.id, Constructor.NotEqualExpression);
		constructors.put(Constructor.LessThanExpression.id, Constructor.LessThanExpression);
		constructors.put(Constructor.GreaterThanExpression.id, Constructor.GreaterThanExpression);
		
		
	}
	
	public enum Constructor {
		Stop("Stop"), Skip("Skip"), Chaos("Chaos"), Div("Div"), Tau("tau"), Tock("tock"), NoPar(
				"nopar"), NullBind("nBind"), Par("par"), IPar("iPar"), Parll(
				"parll"), IParll("iParll"), Prefix("Prefix"), BasicEvent(
				"BasicEv"), IOComm("IOComm"), Hide("hide"), BBind("BBinding"), State(
				"State"), Transition("trans"), ProcessCall("proc"), SeqComposition(
				"seqC"), Spar("SPar"), Dpar("Dpar"), Operation("operation"), Assing(
				"assign"), IntChoice("iChoice"), ExtChoice("eChoice"), CondChoice(
				"condChoice"), ExtraChoice("extraChoice"), EqualExpression("EQ"), NotEqualExpression(
				"NEQ"), LessThanExpression("LT"), GreaterThanExpression("GT"), IntType(
				"Int"), NatType("Nat"), StrType("Str"), IRType("IR"), GivenProc(
				"GivenProc"), ProcDef("ProcDef"), CommEv("CommEv"),
				SingleBind("SingleBind"), Void("void"), VarDecl("var"), Let("let"), 
				GenPar("genPar"), TypeValue("TypeValue"), UntimedInterrupt("intrpt"), 
				UntimedTimeout ("uTimeout"), ProdType("ProdType");
		
		String id;
		
		Constructor(String id){
			this.id = id;
		}
		
	}
	
	public static StringBuilder readScriptFromFile(String filePath) throws IOException {
		 StringBuilder text = new StringBuilder();
		 InputStream is = Utilities.class.getResourceAsStream(filePath);
		 InputStreamReader isr = new InputStreamReader(is);
		 BufferedReader br = new BufferedReader(isr);
		 String line = "";
		 while((line=br.readLine()) != null ){
			 text.append(line);
			 text.append("\n");
		 }
		 is.close();
		 br.close();
		 text.append("\n");
		 return text;
	 }
	
	public static StringBuilder readScriptFromAbsoluteFile(String filePath) throws IOException {
		 StringBuilder text = new StringBuilder();
		 FileReader fr = new FileReader(filePath);
		 BufferedReader br = new BufferedReader(fr);
		 String line = "";
		 while((line=br.readLine()) != null ){
			 text.append(line);
			 text.append("\n");
		 }
		 fr.close();
		 br.close();
		 text.append("\n");
		 return text;
	 }
	
	//determina que construtor est� sendo usado em uma string
	public static Constructor determineConstructor(String content){
		StringBuilder constructor = new StringBuilder();
		Constructor realConstructor = null;
		
		int currCharIndex = 0;
		content = content.trim();
		if(content.length() > 0){
			char currChar = content.charAt(currCharIndex);
			while(  currChar != '(' && currCharIndex != content.length()){
				constructor.append(currChar);
				currCharIndex++;
				if(currCharIndex < content.length()){
					currChar = content.charAt(currCharIndex);
				}
			}
		}
		
		realConstructor = constructors.get(constructor.toString());  
		if(realConstructor == null){
			if(constructor.toString().length() > 0){
				realConstructor = constructors.get(Constructor.TypeValue.id);
			}
		}
		return realConstructor;
		
	}
	
	//extrai o construtor e os seus parenteses, se for o caso 
	public static String extractConstructor(String content){
		String result = "";
		content = content.trim();
		Constructor constructor = determineConstructor(content);
		if(constructor != Constructor.TypeValue){
			if (constructor.equals(Constructor.Stop)
				|| constructor.equals(Constructor.Skip)
				|| constructor.equals(Constructor.Chaos)
				|| constructor.equals(Constructor.Div)
				|| constructor.equals(Constructor.Tau)
				|| constructor.equals(Constructor.Tock)
				|| constructor.equals(Constructor.NoPar)
				|| constructor.equals(Constructor.NullBind)
				|| constructor.equals(Constructor.Void)) {
				
				//result = content.substring(constructor.id.length() + 1, content.length()-1);
				result = "";
			}else{
				result = content.substring(constructor.id.length()+1,content.length()-1);
			}
		}else{
			result = content;
		}
		
		return result;
	}
	
	//dado os argumentos de um construtor, extrai os termos como strings
	public static LinkedList<String> extractAllTerms(String arguments){
		LinkedList<String> result = new LinkedList<String>();
		extractTerms(result,arguments);
		return result;
	}
	
	private static void extractTerms(LinkedList<String> terms, String arguments){
		StringBuilder currentTerm = new StringBuilder();
		int currIndex = 0;
		int leftParen = 0;
		int leftSquareBracket = 0;
		int leftBracket = 0;
		if(arguments.length() > 0){
			char currChar = arguments.charAt(currIndex);
			if((currChar == ',')){
				arguments = arguments.substring(1);
				extractTerms(terms, arguments);
			}else{
				boolean finish = false;
				while (currIndex != arguments.length() && !finish) {
					if(currChar == '('){
						leftParen++;
					}
					if(currChar == '['){
						leftSquareBracket++;
					}
					if(currChar == '{'){
						leftBracket++;
					}
					if(currChar == ')'){
						leftParen--;
						if(leftParen == 0 ){
							finish = true;
						}
					}
					if(currChar == ']'){
						leftSquareBracket--;
						//if(leftParen == 0 ){
						//	finish = true;
						//}
					}
					if(currChar == '}'){
						leftBracket--;
					}
					if((currChar == '\"')){
						currChar = ' ';
					}
					if((currChar == ',') && (leftParen == 0) && (leftSquareBracket == 0) && (leftBracket == 0)){
						break;
					}

					currentTerm.append(currChar);
					currIndex++;
					
					if(currIndex != arguments.length()){
						currChar = arguments.charAt(currIndex);
					}
				}
				terms.add(currentTerm.toString());
				arguments = arguments.substring(currentTerm.length());
				extractTerms(terms, arguments);
			}
		}
	}
	
	
	public static Object createObject(String content){
		Object result = null;
		Constructor c = determineConstructor(content);
		content = extractConstructor(content);
		content = content.replaceAll(" ","");
		result = createObject(c, content);
		
		return result;
	}
	
	private static Object createObject(Constructor constructor, String terms) {
		Object result = new Object();
		LinkedList<String> arguments = extractAllTerms(terms);
		Process auxProcess;
		Event auxEvent;

		String str;
		Type typ;
		String nmbr;
		Param param;
		Type type;
		int number;
		switch (constructor) {
		case Stop:
			result = new Stop();
			break;
		case Skip:
			result = new Skip();
			break;
		case Chaos:
			result = new Chaos();
			break;
		case Div:
			result = new Divergence();
			break;
		case Tau:
			result = new Tau();
			break;
		case Tock:
			result = new Tock();
			break;
		case NoPar:
			result = new NoPar();
			break;
		case NullBind:
			result = new NullBinding();
			break;
		case Void:
			result = new Void();
			break;
		case IntType:
			result = new Int(buildInteger(arguments.pop()));
			break;
		case NatType:
			result = new Nat(buildNatural(arguments.pop()));
			break;
		case IRType:
			result = new IR(buildReal(arguments.pop()));
			break;
		case StrType:
			result = new Str(arguments.pop());
			break;
		case TypeValue:
			result = new TypeValue(arguments.pop().trim());
			break;
		case ProdType:
			Type first = (Type) createObject(arguments.pop().trim());
			Type second = (Type) createObject(arguments.pop().trim());
			result = new ProdType(first,second);
			break;
		case VarDecl:
			str = arguments.pop().trim();
			String typeStr =  arguments.pop().trim();
			auxProcess = (Process) createObject(arguments.pop().trim());
			result = new VarDeclaration(str,typeStr,auxProcess);
			break;
		case Let:
			str = arguments.pop().trim();
			auxProcess = (Process) createObject(arguments.pop().trim());
			result = new Let(str,auxProcess);
			break;
		case Assing:
			nmbr = arguments.pop().trim();
			result = new Assing(nmbr);
			break;
			
		case Operation:
			str = arguments.pop().trim();
			type = (Type) createObject(arguments.pop().trim());
			result = new Operation(str,type);
			break;
		case ProcessCall:
			str = arguments.pop().trim();
			type = (Type) createObject(arguments.pop().trim());
			result = new ProcessCall(str,type);
			break;
		case SeqComposition:
			auxProcess = (Process) createObject(arguments.pop().trim());
			Process process = (Process) createObject(arguments.pop().trim());
			result = new SeqComposition(auxProcess,process);
			break;
		case IntChoice:
			auxProcess = (Process) createObject(arguments.pop().trim());
			process = (Process) createObject(arguments.pop().trim());
			result = new IntChoice(auxProcess,process);
			break;
		case UntimedInterrupt:
			auxProcess = (Process) createObject(arguments.pop().trim());
			process = (Process) createObject(arguments.pop().trim());
			result = new UntimedInterrupt(auxProcess,process);
			break;
		case UntimedTimeout:
			auxProcess = (Process) createObject(arguments.pop().trim());
			process = (Process) createObject(arguments.pop().trim());
			result = new UntimedTimeout(auxProcess,process);
			break;
		case ExtChoice:
			auxProcess = (Process) createObject(arguments.pop().trim());
			process = (Process) createObject(arguments.pop().trim());
			result = new ExtChoice(auxProcess,process);
			break;
		case ExtraChoice:
			//number = buildInteger(arguments.pop());
			Binding leftBiding = (Binding) createObject(arguments.pop().trim());
			process = (Process) createObject(arguments.pop().trim());
			Binding rightBiding = (Binding) createObject(arguments.pop().trim());
			Process rightProcess = (Process) createObject(arguments.pop().trim());
			result = new ExtraChoice(leftBiding,process,rightBiding,rightProcess);
			
			break;
		case CondChoice:
			number = Integer.parseInt(arguments.pop().trim());
			auxProcess = (Process) createObject(arguments.pop().trim());
			process = (Process) createObject(arguments.pop().trim());
			result = new ConditionalChoice(number,auxProcess,process);
			break;
		case EqualExpression:
			typ = (Type) createObject(arguments.pop());
			Type typ2 = (Type) createObject(arguments.pop());
			result = new EqualBooleanExpression(typ,typ2);
			break;
		case NotEqualExpression:
			typ = (Type) createObject(arguments.pop());
			typ2 = (Type) createObject(arguments.pop());
			result = new NotEqualBooleanExpression(typ,typ2);
			break;
		case LessThanExpression:
			typ = (Type) createObject(arguments.pop());
			typ2 = (Type) createObject(arguments.pop());
			result = new LessThanBooleanExpression(typ,typ2);
			break;
		case GreaterThanExpression:
			typ = (Type) createObject(arguments.pop());
			typ2 = (Type) createObject(arguments.pop());
			result = new LessThanBooleanExpression(typ,typ2);
			break;
		case Transition:
			State source = (State) createObject(arguments.pop());
			auxEvent = (Event) createObject(arguments.pop());
			State target = (State) createObject(arguments.pop());
			result = new Transition(source, auxEvent, target);
			break;
		case State:
			//number = Integer.parseInt(arguments.pop());
			Binding binding = (Binding) createObject(arguments.pop());
			//String srt = arguments.pop();
			auxProcess = (Process) createObject(arguments.pop());
			result = new State(binding, auxProcess);
			break;
		case Prefix:
			auxEvent = (Event) createObject(arguments.pop());
			auxProcess = (Process) createObject(arguments.pop());
			result = new Prefix(auxEvent, auxProcess);
			break;
		case BBind:
			//String procName = arguments.pop();
			SingleBind singleBind = (SingleBind)createObject(arguments.pop());
			Binding tail = (Binding)createObject(arguments.pop());
			result = new BBinding(null,singleBind,tail);
			break;
			
		case SingleBind:
			str = arguments.pop();
			Type varValue = (Type)createObject(arguments.pop());
			result = new SingleBind(str,varValue);
			break;
			
		case CommEv:
			String begin = arguments.pop();
			String middle = "";
			if(arguments.size() > 1){
				middle = arguments.pop();
			}
			Type tipo = (Type) createObject(arguments.pop());
			result = new CommEv(begin,middle,tipo);
			break;
		case Hide:
			auxProcess = (Process) createObject(arguments.pop());
			str = arguments.pop();
			result = new Hide(auxProcess, str);
			break;
		case BasicEvent:
			String name = arguments.pop();
			result = new BasicEvent(name);
			break;
		case IOComm:
			nmbr = arguments.pop();
			str = arguments.pop();
			String exp = arguments.pop();
			Type valueType = (Type) createObject(arguments.pop());
			result = new IOCommEv(nmbr, str, exp, valueType);
			break;
		case Spar:
			typ = (Type) createObject(arguments.pop());
			result = new SPar(typ);
			break;
		case Dpar:
			typ = (Type) createObject(arguments.pop());
			typ2 = (Type) createObject(arguments.pop());
			result = new DPar(typ,typ2);
			break;
		case GivenProc:
			result = new GivenProc(arguments.pop().trim()); 
			break;
		case ProcDef:
			str = arguments.pop().trim();
			type = (Type) createObject(arguments.pop().trim());
			process = (Process) createObject(arguments.pop().trim());
			result = new ProcDef(str,type,process);
			break;
		case IPar:
			process = (Process) createObject(arguments.pop());
			str = arguments.pop();
			auxProcess = (Process) createObject(arguments.pop());
			result = new IPar(process,str,auxProcess);
			break;
		case IParll:
			binding = (Binding) createObject(arguments.pop());
			process = (Process) createObject(arguments.pop());
			str = arguments.pop();
			leftBiding = (Binding) createObject(arguments.pop());
			auxProcess = (Process) createObject(arguments.pop());
			result = new IParll(binding,process,str,auxProcess,leftBiding);
			break;
		case Par:
			binding = (Binding) createObject(arguments.pop());
			process = (Process) createObject(arguments.pop());
			str = arguments.pop();
			leftBiding = (Binding) createObject(arguments.pop());
			auxProcess = (Process) createObject(arguments.pop());
			result = new Par(binding,process,str,leftBiding,auxProcess);
			break;
		
		case GenPar:
			process = (Process) createObject(arguments.pop().trim());
			str = arguments.pop().trim();
			auxProcess = (Process) createObject(arguments.pop().trim());
			result = new GenPar(process,str,auxProcess);
			break;
			
		case Parll:
			process = (Process) createObject(arguments.pop());
			str = arguments.pop();
			String auxStr = arguments.pop();
			 String str3 = arguments.pop();
			auxProcess = (Process) createObject(arguments.pop());
			result = new Parll(process,str,auxStr,str3,auxProcess);
			break;
			
		default:
			break;
		}

		return result;
	}
	
	private static int buildInteger(String symbolicInteger){
		int result = 0;
		symbolicInteger = symbolicInteger.replace("_","");
		symbolicInteger = symbolicInteger.replace("<","");
		symbolicInteger = symbolicInteger.replace(">","");
		result = Integer.parseInt(symbolicInteger);
		return result;
	}
	
	private static int buildNatural(String symbolicNatural){
		int result = 0;
		symbolicNatural = symbolicNatural.replace("_","");
		symbolicNatural = symbolicNatural.replace("<","");
		symbolicNatural = symbolicNatural.replace(">","");
		result = Integer.parseInt(symbolicNatural);
		return result;
	}
	
	private static double buildReal(String symbolicNatural){
		double result = 0;
		symbolicNatural = symbolicNatural.replace("_","");
		symbolicNatural = symbolicNatural.replace("<","");
		symbolicNatural = symbolicNatural.replace(">","");
		result = Double.parseDouble(symbolicNatural);
		return result;
	}

	
	/*
	public static void main(String[] args) {
		Utilities u = new Utilities();
		String[] examples = {
//				"Skip",
//				"Stop",
				"Prefix(BasicEv(\"a\"),Stop)",
//				"Prefix(BasicEv(\"a\"),Prefix(BasicEv(\"b\"),Skip))",
				"trans(State(0,BBinding(0,0,0),Cards,proc(Card,SPar(Int(1)))),tau,State(0,BBinding(0,0,0),Card,seqC(schema(Init),proc(Cycle,SPar(Int(1))))))"
		}; 
		for (int i = 0; i < examples.length; i++) {
			String current = examples[i]; //pega uma das strings
			Constructor c = u.determineConstructor(current); //encontra o construtor
			current = u.extractConstructor(current); //extrai ele da string
			LinkedList<String> terms = u.extractAllTerms(current); //pega os termos internos do construtor
			 
			int k = 0;
		}
		
		
	}
	*/
}
