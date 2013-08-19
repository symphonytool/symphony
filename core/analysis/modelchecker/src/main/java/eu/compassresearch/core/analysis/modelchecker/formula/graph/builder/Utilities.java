package eu.compassresearch.core.analysis.modelchecker.formula.graph.builder;

import java.util.HashMap;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.formula.graph.event.BasicEvent;
import eu.compassresearch.core.analysis.modelchecker.formula.graph.event.Event;
import eu.compassresearch.core.analysis.modelchecker.formula.graph.process.Prefix;
import eu.compassresearch.core.analysis.modelchecker.formula.graph.process.Skip;
import eu.compassresearch.core.analysis.modelchecker.formula.graph.process.Stop;


public class Utilities {
	
	protected HashMap<String, Constructor> constructors;
	
	public Utilities(){
		constructors = new HashMap<String, Constructor>();
		constructors.put(Constructor.Skip.id, Constructor.Skip);
		constructors.put(Constructor.Stop.id, Constructor.Stop);
		constructors.put(Constructor.Prefix.id, Constructor.Prefix);
		constructors.put(Constructor.BasicEvent.id, Constructor.BasicEvent);
		
	}
	
	enum Constructor { Stop("Stop"),Skip("Skip"), Prefix("Prefix"), BasicEvent("BasicEv");
		
		String id;
		
		Constructor(String id){
			this.id = id;
		}
		
	}
	
	//determina que construtor est� sendo usado em uma string
	public Constructor determineConstructor(String content){
		StringBuilder constructor = new StringBuilder();
		
		int currCharIndex = 0;
		
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
		Constructor constructor = determineConstructor(content);
		
		if (constructor.equals(Constructor.Stop) 
				|| constructor.equals(Constructor.Skip)){
			
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
			if(currChar == ','){
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
		result = createObject(c, content);
		return result;
	}
	
	public Object createObject(Constructor constructor, String terms){
		Object result = new Object();
		
		switch (constructor) {
			case Stop:
				result = new Stop();  
				break;

			case Skip:
				result = new Skip();  
				break;

			case Prefix:
				LinkedList<String> arguments = this.extractAllTerms(terms);
				Event event = (Event)this.createObject(arguments.getFirst());
				eu.compassresearch.core.analysis.modelchecker.formula.graph.process.Process process =  (eu.compassresearch.core.analysis.modelchecker.formula.graph.process.Process) this.createObject(arguments.getLast());
				result = new Prefix(event,process);  
				break;

			case BasicEvent:
				String name = terms.replaceAll("\"","");
				result = new BasicEvent(name);  
				break;
				
			default:
				break;
		}
		
		return result;
	}
	public static void main(String[] args) {
		Utilities u = new Utilities();
		String[] examples = {
				"Skip",
				"Stop",
				"Prefix(BasicEv(\"a\"),Stop)",
				"Prefix(BasicEv(\"a\"),Prefix(BasicEv(\"b\"),Skip))"
		};
		for (int i = 0; i < examples.length; i++) {
			Object o = u.createObject(examples[i]);
			int k = 0;
		}
		for (int i = 0; i < examples.length; i++) {
			String current = examples[i]; //pega uma das strings
			Constructor c = u.determineConstructor(current); //encontra o construtor
			current = u.extractConstructor(current); //extrai ele da string
			LinkedList<String> terms = u.extractAllTerms(current); //pega os termos internos do construtor
			
			int k = 0;
		}
	}
}
