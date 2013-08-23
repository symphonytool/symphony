package eu.compassresearch.core.analysis.modelchecker.graphBuilder.util;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.*;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.BBinding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.Binding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.binding.NullBinding;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.event.*;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.expression.EqualBooleanExpression;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.expression.LessThanBooleanExpression;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.expression.NotEqualBooleanExpression;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.param.DPar;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.param.NoPar;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.param.Param;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.param.SPar;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Assing;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.ConditionalChoice;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Divergence;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.ExtChoice;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.ExtraChoice;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Hide;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.IPar;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.IParll;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.IntChoice;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Par;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Parll;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Prefix;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Process;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.ProcessCall;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Schema;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.SeqComposition;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Skip;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.process.Stop;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.transition.Transition;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.IR;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Int;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Nat;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Str;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.T1;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.T2;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.T3;
import eu.compassresearch.core.analysis.modelchecker.graphBuilder.type.Type;


import java.util.Hashtable;
import java.util.LinkedList;

public class Utilities {
	
	protected Hashtable<String, Constructor> constructors;
	
	
	
	public Utilities(){
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
		constructors.put(Constructor.Schema.id, Constructor.Schema);
		constructors.put(Constructor.Assing.id, Constructor.Assing);

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
		constructors.put(Constructor.IPar.id, Constructor.IPar);
		constructors.put(Constructor.IParll.id, Constructor.IParll);
		constructors.put(Constructor.Parll.id, Constructor.Parll);
		
		constructors.put(Constructor.State.id, Constructor.State);
		constructors.put(Constructor.Transition.id, Constructor.Transition);
		constructors.put(Constructor.GivenProc.id, Constructor.GivenProc);
		constructors.put(Constructor.ProcDef.id, Constructor.ProcDef);
		
		//EVENT
		constructors.put(Constructor.BasicEvent.id, Constructor.BasicEvent);
		constructors.put(Constructor.IOComm.id, Constructor.IOComm);
		constructors.put(Constructor.Tau.id, Constructor.Tau);
		constructors.put(Constructor.CommEv.id, Constructor.CommEv);

		
		//BINDING
		constructors.put(Constructor.NullBind.id, Constructor.NullBind);
		constructors.put(Constructor.BBind.id, Constructor.BBind);
		
		//TYPE
		constructors.put(Constructor.IntType.id, Constructor.IntType);
		constructors.put(Constructor.StrType.id, Constructor.StrType);
		constructors.put(Constructor.NatType.id, Constructor.NatType);
		constructors.put(Constructor.IRType.id, Constructor.IRType);
		constructors.put(Constructor.T1.id, Constructor.T1);
		constructors.put(Constructor.T2.id, Constructor.T2);
		constructors.put(Constructor.T3.id, Constructor.T3);

		
		//EXPRESSION
		constructors.put(Constructor.EqualExpression.id, Constructor.EqualExpression);
		constructors.put(Constructor.NotEqualExpression.id, Constructor.NotEqualExpression);
		constructors.put(Constructor.LessThanExpression.id, Constructor.LessThanExpression);
		constructors.put(Constructor.GreaterThanExpression.id, Constructor.GreaterThanExpression);
		
		
	}
	
	public enum Constructor {
		Stop("Stop"), Skip("Skip"), Chaos("Chaos"), Div("Div"), Tau("tau"), NoPar(
				"nopar"), NullBind("nBind"), Par("par"), IPar("iPar"), Parll(
				"parll"), IParll("iParll"), Prefix("Prefix"), BasicEvent(
				"BasicEv"), IOComm("IOComm"), Hide("hide"), BBind("BBinding"), State(
				"State"), Transition("trans"), ProcessCall("proc"), SeqComposition(
				"seqC"), Spar("SPar"), Dpar("Dpar"), Schema("schema"), Assing(
				"assign"), IntChoice("iChoice"), ExtChoice("eChoice"), CondChoice(
				"condChoice"), ExtraChoice("extraChoice"), EqualExpression("EQ"), NotEqualExpression(
				"NEQ"), LessThanExpression("LT"), GreaterThanExpression("GT"), IntType(
				"Int"), NatType("Nat"), StrType("Str"), IRType("IR"), GivenProc(
				"GivenProc"), ProcDef("ProcDef"), CommEv("CommEv"),T1("T1"),T2("T2"),T3("T3");
		
		String id;
		
		Constructor(String id){
			this.id = id;
		}
		
	}
	
	//determina que construtor est� sendo usado em uma string
	public Constructor determineConstructor(String content){
		StringBuilder constructor = new StringBuilder();
		
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
		
		return constructors.get(constructor.toString());
		
	}
	
	//extrai o construtor e os seus parenteses, se for o caso 
	public String extractConstructor(String content){
		String result = "";
		content = content.trim();
		Constructor constructor = determineConstructor(content);
		
		if (constructor.equals(Constructor.Stop)
			|| constructor.equals(Constructor.Skip)
			|| constructor.equals(Constructor.Chaos)
			|| constructor.equals(Constructor.Div)
			|| constructor.equals(Constructor.Tau)
			|| constructor.equals(Constructor.NoPar)
			|| constructor.equals(Constructor.NullBind)) {
			
			//result = content.substring(constructor.id.length() + 1, content.length()-1);
			result = "";
		}else{
			result = content.substring(constructor.id.length()+1,content.length()-1);
		}
		
		return result;
	}
	
	//dado os argumentos de um construtor, extrai os termos como strings
	public LinkedList<String> extractAllTerms(String arguments){
		LinkedList<String> result = new LinkedList<String>();
		extractTerms(result,arguments);
		return result;
	}
	
	private void extractTerms(LinkedList<String> terms, String arguments){
		StringBuilder currentTerm = new StringBuilder();
		int currIndex = 0;
		int leftParen = 0;
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
					if(currChar == ')'){
						leftParen--;
						if(leftParen == 0){
							finish = true;
						}
					}
					if((currChar == '\"')){
						currChar = ' ';
					}
					if((currChar == ',') && (leftParen == 0)){
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
	
	
	public Object createObject(String content){
		Object result = new Object();
		Constructor c = this.determineConstructor(content);
		content = this.extractConstructor(content);
		content = content.replaceAll(" ","");
		result = createObject(c, content);
		return result;
	}
	
	private Object createObject(Constructor constructor, String terms) {
		Object result = new Object();
		LinkedList<String> arguments = this.extractAllTerms(terms);
		Process auxProcess;
		Event auxEvent;

		String str;
		Type typ;
		String nmbr;
		Param param;
		int number;
		switch (constructor) {
		case Stop:
			result = new Stop();
			break;
		case Skip:
			result = new Skip();
			break;
		case Div:
			result = new Divergence();
			break;
		case Tau:
			result = new Tau();
			break;
		case NoPar:
			result = new NoPar();
			break;
		case NullBind:
			result = new NullBinding();
			break;
		case IntType:
			result = new Int(this.buildInteger(arguments.pop()));
			break;
		case NatType:
			result = new Nat(createObject(arguments.pop()));
			break;
		case IRType:
			result = new IR(createObject(arguments.pop()));
			break;
		case StrType:
			result = new Str(arguments.pop());
			break;
		case T1:
			result = new T1(arguments.pop());
			break;
		case T2:
			result = new T2(arguments.pop());
			break;
		case T3:
			result = new T3(arguments.pop());
			break;
		case Assing:
			nmbr = arguments.pop();
			result = new Assing(nmbr);
			break;
		case Schema:
			str = arguments.pop();
			result = new Schema(str);
			break;
		case ProcessCall:
			str = arguments.pop();
			param = (Param) createObject(arguments.pop());
			result = new ProcessCall(str,param);
			break;
		case SeqComposition:
			auxProcess = (Process) createObject(arguments.pop());
			Process process = (Process) createObject(arguments.pop());
			result = new SeqComposition(auxProcess,process);
			break;
		case IntChoice:
			auxProcess = (Process) createObject(arguments.pop());
			process = (Process) createObject(arguments.pop());
			result = new IntChoice(auxProcess,process);
			break;
		case ExtChoice:
			auxProcess = (Process) createObject(arguments.pop());
			process = (Process) createObject(arguments.pop());
			result = new ExtChoice(auxProcess,process);
			break;
		case ExtraChoice:
			number = this.buildInteger(arguments.pop());
			Binding leftBiding = (Binding) createObject(arguments.pop());
			process = (Process) createObject(arguments.pop());
			Binding rightBiding = (Binding) createObject(arguments.pop());
			Process rightProcess = (Process) createObject(arguments.pop());
			result = new ExtraChoice(leftBiding,process,rightBiding,rightProcess);
			
			break;
		case CondChoice:
			number = Integer.parseInt(arguments.pop());
			auxProcess = (Process) createObject(arguments.pop());
			process = (Process) createObject(arguments.pop());
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
			State source = (State) this.createObject(arguments.pop());
			auxEvent = (Event) this.createObject(arguments.pop());
			System.out.println();
			State target = (State) this.createObject(arguments.pop());
			result = new Transition(source, auxEvent, target);
			break;
		case State:
			number = Integer.parseInt(arguments.pop());
			Binding binding = (Binding) createObject(arguments.pop());
			String srt = arguments.pop();
			auxProcess = (Process) createObject(arguments.pop());
			result = new State(number, binding, srt, auxProcess);
			break;
		case Prefix:
			auxEvent = (Event) this.createObject(arguments.pop());
			auxProcess = (Process) this.createObject(arguments.pop());
			result = new Prefix(auxEvent, auxProcess);
			break;
		case BBind:
			String begin = arguments.pop();
			String middle = arguments.pop();
			String end = arguments.pop();
			result = new BBinding(begin,middle,end);
			break;
		case CommEv:
			begin = arguments.pop();
			middle = arguments.pop();
			Type tipo = (Type) createObject(arguments.pop());
			result = new CommEv(begin,middle,tipo);
			break;
		case Hide:
			auxProcess = (Process) createObject(arguments.pop());
			srt = arguments.pop();
			result = new Hide(auxProcess, srt);
			break;
		case BasicEvent:
			String name = arguments.pop();
			result = new BasicEvent(name);
			break;
		case IOComm:
			nmbr = arguments.pop();
			str = arguments.pop();
			result = new IOCom(nmbr, str);
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
			result = new GivenProc(arguments.pop()); 
			break;
		case ProcDef:
			str = arguments.pop();
			param = (Param) createObject(arguments.pop());
			process = (Process) createObject(arguments.pop());
			result = new ProcDef(str,param,process);
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
			auxProcess = (Process) createObject(arguments.pop());
			leftBiding = (Binding) createObject(arguments.pop());
			result = new Par(binding,process,str,auxProcess,leftBiding);
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
	
	private int buildInteger(String symbolicInteger){
		int result = 0;
		symbolicInteger = symbolicInteger.replace("_","");
		symbolicInteger = symbolicInteger.replace("<","");
		symbolicInteger = symbolicInteger.replace(">","");
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
