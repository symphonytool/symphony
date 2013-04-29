package eu.compassresearch.core.typechecker;

import static eu.compassresearch.core.typechecker.TestUtil.addTestProgram;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.PDefinition;

import eu.compassresearch.ast.lex.LexIdentifierToken;
import eu.compassresearch.ast.lex.LexNameToken;
import eu.compassresearch.ast.program.AInputStreamSource;
import eu.compassresearch.ast.program.PSource;
import eu.compassresearch.core.typechecker.api.TypeErrorMessages;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler;
import eu.compassresearch.core.typechecker.api.TypeIssueHandler.CMLTypeError;

@RunWith(value = Parameterized.class)
public class CmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase {


	public CmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages) {
		super(cmlSource, parsesOk, typesOk, errorMessages);
	}

	@Before
	public void setup() {

	}

	@Parameters
	public static Collection<Object[]> parameter() {

		List<Object[]> testData = new LinkedList<Object[]>();

	

		
		

		addTestProgram(testData, "functions test: int * int -> bool test(a,b) == true channels InOut: int * int * int process A = begin state b:int actions A = InOut?a!b?c -> test(a,c) @ Skip end", true, true, new String[0]);
		addTestProgram(testData, "channels a : int process A =  begin state b : int := 2 actions INIT = a!(b+2) -> Skip @ INIT end", true, true, new String[0]);
		addTestProgram(testData, "process A = begin actions B = val n:int @ Skip @ (||| i in set {1,2,3} @ [ { } ] B(i)) end", true, true, new String[0]);
		// 159
		addTestProgram(testData, "process A = begin @ A [[ init <- start ]] end ", true, true, new String[0]);
		// 160
		addTestProgram(testData, "channels startStartRescue acceptStartRescue endStartRescue process P = begin actions A = startStartRescue -> acceptStartRescue -> endStartRescue -> B B = C C = Skip  @ Skip end", true, true, new String[0]);
		// 161
		addTestProgram(testData, "channels startStartRescue acceptStartRescue endStartRescue process P = begin actions A = startStartRescue -> acceptStartRescue -> endStartRescue -> B B = $T(target(C))", true, true, new String[0]);
		// 162
		addTestProgram(testData, "process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b; return a @ a := o(10) end", true, true, new String[0]);
		// 163
		addTestProgram(testData, "process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b+1 elseif a > 1 then a := b - a else a := 0 ; return a @ a := o(9) end", true, true, new String[0]);
		// 164
		addTestProgram(testData, "process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b+1 elseif 1 then a := b - a else a := 0 ; return a @ a := o(9) end", true, false, new String[0]);
		// 165
		addTestProgram(testData, "process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b+1 elseif a > 1 then a:='a' else a := 0 ; return a @ a := o(9) end", true, false, new String[0]);
		// 166
		addTestProgram(testData, "process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b+1 elseif a > 1 then a:=b-a else a := 'l' ; return a @ a := o(9) end", true, false, new String[0]);
		// 167
		addTestProgram(testData, "process K = begin state a:int operations o: int ==> int o(b) == if a = 0 then a:=b+1 elseif a > 1 then a:=b-a else a := 0 ; return a @ a := o('l') end", true, false, new String[0]);
		// 168
		addTestProgram(testData, "channels a, b process A = begin actions INIT = (a -> b -> Skip) \\\\ {|b|} @ INIT end", true, true , new String[0]);
		// This test checks that an explicit operation body of type nat1 can be declared to return int.
		// 169
		addTestProgram(testData, "channels a, b process A = begin functions test : int +> int test(x) == 2  @ a -> Skip end", true, true, new String[0]);
		
		// This test was reported by AKM and checks that functions can be invoked from Guards
		// 170
		addTestProgram(testData, "channels a process A = begin state x : nat := 3 functions isHigherThanTwo : (int) +> bool isHigherThanTwo(y) == y > 2 @ [isHigherThanTwo(x)] & a -> Skip end", true, true, new String[0]);
		
		// Test test checks that the declared section of an dcl is checked.
		// 171
		addTestProgram(testData, "process P = begin @ (dcl z:int := \"wrong type\" @ Skip) end", true, false, new String[0]);
		// This test was reported by AKM and checks that operations cannot be invoked from dcl's
		// 172
		addTestProgram(testData, "channels a : int process A = begin state v : int := 2 operations Test : int ==> int Test(x) == return x + v @ (dcl z : int := test(2) @ a!(z) -> Skip ) end", true,false,new String[0]);
		// This test checks the positive case for invoking a Cml Operation
		// 173
		addTestProgram(testData, "process K = begin state f : int := 0 operations op1: int ==> int op1(a) == return (a+1) @ f := op1(10) end", true, true, new String[0]);
		// 174 //
		addTestProgram(testData, "class test = begin operations op1: int ==> int op1(a) == return (a+1) values k : int = op1(10) end", true, false, new String[0]);
		// 175 //
		addTestProgram(testData, "process T = begin functions f(a:int) r:int pre true post true @ f(2) end", true, true, new String[0]);
		// 176 //
		addTestProgram(testData, "class test = begin operations o: int ==> int o(a) == return a pre a > 0 post a~ = a end ", true, true, new String[0]);
		// 177 //
		addTestProgram(testData, "class t = begin types A :: a : int operations o:A==>int o(b) == return b.a pre b.a > 0 post b.a = b~.a end", true, true, new String[0]);
		// 178 //
		addTestProgram(testData, "class t = begin types A :: a : int operations o:A==>int o(b) == return b.a pre b.a > 0 post b.a = b~.a and 'b.a' = b~.a end", true, false, new String[0]);
		// 179 //
		addTestProgram(testData, "class t = begin types A :: a : int state aa : A operations o:int==>int o(i) == return (aa.a + i) pre i > 0 post 0 = i end", true, true, new String[0]);
		// 180 //
		addTestProgram(testData, "process p = begin types A :: a : int state aa:A operations o:int==>int o(i) == return (aa.a + i) post aa~.a = i @ o(2) end",true,true,new String[0]);
		// 181
		addTestProgram(testData, "types C = set of int inv c == c = {1,2,3}", true, true, new String[0]);
		// 182
		addTestProgram(testData, "class T = begin end class N = begin end types TorN = T | N", true, true, new String[0]);
		// 183
		addTestProgram(testData, "process T = begin operations o:int*int ==> int o(a,b) == return a+b @ o(10,10) end", true, true, new String[0]);
		// 184
		addTestProgram(testData, "types I = int process T = begin operations o:I*I ==> int o(a,b) == return a+b @ o(10,10) end", true, true, new String[0]);
		// 185
		addTestProgram(testData, "types R :: a : int b : int " +
				                  "process P = " +
				                  "  " +
				                  " " +
				                  "begin " +
				                  "  state " +
				                  "    a : int " +
				                  "    k : R   " +
				                  "  actions " +
				                  "    A = cases k : mk_R(a1,b1) -> a:=a1+b1 end" +
				                  " @ A " +
				                  "end", true,true, new String[0]);
		// 186
		addTestProgram(testData, "types Value = int ID = nat process P = begin operations CheckMac: Value * Value * ID ==> bool CheckMac(a,b,c) == return (a*b=c) Me: () ==> bool Me() == (dcl a : Value := 2 @ return CheckMac(a,2,4)) @ Skip end ",false,true,true, new String[0]);
		// 187
		addTestProgram(testData, "process K = begin operations A:int*int ==> bool A(a,b) == return (a=b) @ A(2) end", true, false, new String[0]);
		// 188
		addTestProgram(testData, "process L = begin operations A:int*int*int ==> bool A(a,b,c) == o(0);return (a+b=c) @ A(1,mk_(0,2)) end",true,false, new String[0]);
		// 189
		addTestProgram(testData, "process L = begin state k : int operations K:int*int ==> int K(a,b) == for all i in set {1,2,3} do k := k + o @ Skip end", true, false, new String[0]);
		// 190
		addTestProgram(testData, "process L = begin state l : int @ || i in set {1,2,3} @ [{ }] l:=i end", true, true, new String[0]);
		// 191
		addTestProgram(testData, "process O = begin state o : int @ for all i in set {1,2,3} do o := o + i end", true, true, new String[0]);
		// 192
		addTestProgram(testData, "process M = begin @ Skip end process L = begin @ Stop end process K = || i in set {1,2,3} @ [{ }] (M [| {| inp.k | k in set {5,6,7} |} union {| out.k | k in set {5,6,7} |} |] L)",false, true,true,new String[0]);
		// 193
		addTestProgram(testData, "channels out: nat1 process S1 = || k in set {1,2,3} @ [{ }] begin @ out!k -> Skip end", true, true,new String[0]);
		// 194
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| {  } |] i in set {1,2,3} @ [{ c1 }] c1!i -> Skip end", true, false, new String[0]);
		// 195
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| {a } |] i in set {1,2,3} @ [{    }] c1!i -> Skip end", true, false, new String[0]);
		// 196
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| {c1} |] i in set {1,2,3} @ [{  a }] c1!i -> Skip end", true, true, new String[0]);
		// 197
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| { } |] i in set {1,2,3} @ [{   }] Skip end", true, true, new String[0]);
		// 198
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| {c1, a} |] i in set {1,2,3} @ [{   }] c1!i -> Skip end", true, false, new String[0]);
		// 199
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| { } |] i in set {1,2,3} @ [{  c1, a }] c1!i -> Skip end", true, false, new String[0]);
		// 200
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| { } |] i in set {1,2,3} @ [{  a  }] c1!a -> Skip end", true, false, new String[0]);
		// 201
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| { c1 } |] i in set {1,2,3} @ [{   }] c1!a -> Skip end", true, false, new String[0]);
		// 202
		addTestProgram(testData, "process T = begin operations O: () ==> () O() == Skip @ O() end", true, true, new String[0]);
		// 203
		addTestProgram(testData, "process A = begin @ let b = 2 in [b = 2] & Skip end",true,true,new String[0]);
		// 204
		addTestProgram(testData, "values a : int = 42 process A = begin values b : int = 43 @Skip end", true, true, new String[0]);
		// 205
		addTestProgram(testData, "channels c process A = begin @ ||| k in set {1,2,3} @ [{ }]c!k -> Skip end", true, false, new String[0]);
		// 207
		addTestProgram(testData, "channels c:nat1 process A = begin @ ||| k in set {1,2,3} @ [{ }]c!k -> Skip end", true, true, new String[0]);
		// 208
		addTestProgram(testData,"process K = begin actions INIT = Skip @ INIT() end",true,true,new String[0]);
		// 209
		addTestProgram(testData,"process K = begin operations INIT: () ==> () INIT() == Skip @ INIT() end",true,true,new String[0]);
		// 210
		addTestProgram(testData,"values m: map int to int = {  1 |-> 2 } process K = begin state l:int functions f:map int to int -> int f(m) == m(42) @ l := f(m) end",true,true,new String[0]);
		// 211
		addTestProgram(testData,"values m = {  1 |-> 2 } process K = begin state l:int operations f:map int to int ==> int f(m) == return m(42) @ l := f(m) end",true,true,new String[0]);
		// 212
		addTestProgram(testData, "channels a: int types book = token values mbook = { mk_token(\"Book\") |-> 1} process P = begin @  a!(mbook(book))->Skip end", true,true, new String[0]);
		// 213
		addTestProgram(testData, "types k = int channels a class A = begin end", true, true, new String[0]);
		// 214
		addTestProgram(testData, "types S = seq of char RescueDetails::k : int functions rescueDetailsToString(r : RescueDetails) s: S post s <> [] process P = begin actions MERGE2 = val eru: ERUId @ (dcl s: S,r:RescueDetails @ s := rescueDetailsToString(r); Skip ) @ Skip end", true, true, new String[0]);
		// 215
		addTestProgram(testData, "channels c1: int process P = begin actions A = val r : int @ c1!r -> Skip @ Skip end", true, true, new String[0]);
		// 216
		addTestProgram(testData, "types Id ::   type : (<ERU> | <CC>) identifier : token ERUId = Id Location = token Criticality = nat inv c == c < 4 String = seq of char RescueDetails :: target : Location criticality : Criticality process CallCentreProc = begin state erus: set of ERUId eruRescues: map ERUId to RescueDetails inv dom eruRescues subset erus and (forall i in set erus @ i.type = <ERU>) operations reAllocateERU(eru : ERUId, r : RescueDetails) frame wr eruRescues : map ERUId to RescueDetails rd erus: set of ERUId pre eru in set erus and eru in set dom eruRescues and eruRescues(eru) <> r post eru in set dom eruRescues and eruRescues(eru) = r actions FORK1 = (dcl eru : ERUId @ (dcl r : RescueDetails @ (dcl oldr: RescueDetails @  reAllocateERU(eru,r)))) @ Skip end",true,true,new String[0]);
		// 217
		addTestProgram(testData, "types SUBS = token STATUS = ( <ringing> | <speech> | <suspended>) values Connected = {<ringing>,<speech>,<suspended>} functions connected: (map SUBS to STATUS) * (map SUBS to SUBS) +> (inmap SUBS to SUBS) connected(status,number) == {} free: (map SUBS to STATUS) * (map SUBS to SUBS) * (set of SUBS) +> (set of SUBS) free(status,number,subs) == subs \\ dom(status) \\ rng(connected(status,number)) class Exchange = begin end", true, true, new String[0]);
		// 218
		addTestProgram(testData, "channels a:int process P = ||| i in set {1,2,3} @ begin @ a!i -> Skip end",false,true,true,new String[0]);
		// 219
		addTestProgram(testData, "class T = begin state a : int functions f:int * int -> int f(x,y) == a + x + y end", true, false, new String[0]);
		// 220
		addTestProgram(testData, "types Quantity = int Price = int class C = begin state sellerBids : seq of Quantity buyerBids : seq of Quantity prices : seq of Price inv len(sellerBids) = len(buyerBids) and len(sellerBids) = len(prices) end", true, true, new String[0]);
		// 221
		addTestProgram(testData, "functions f: int -> int f(a) == a+1 pre a > 0 process P = begin @ f(2) end ", true, true, new String[0]);
		// 222
		addTestProgram(testData, "class C = begin operations public doit: int ==> () doit(a) == Skip end process P = begin state s : C @ s.doit(1) end",true,true,new String[0]);
		// 223
		addTestProgram(testData, "functions f: int -> int f(a) == a+1 pre a > 0 process P = begin @ pre_f(2) end ", true, true, new String[0]);
		// 224
		addTestProgram(testData, "types mac :: a:int b:int process P = begin functions f: mac * int -> int f(x,y) == x.a+y @ f(mk_mac(1,2),2) end", true, true, new String[0]);
		// 225
		addTestProgram(testData, "functions f: int * int -> int f(x,y) == x+y pre x > 0 process P = begin actions A = [ pre_f(0,0) ] & Skip @ A end", true, true, new String[0]);
		// 226
		addTestProgram(testData,"types ERUId = nat RescueDetails ::a:int b:int process P = begin state erus : set of ERUId eruRescues : map ERUId to RescueDetails operations findIdleERUs() idleERUs: set of ERUId frame rd erus: set of ERUId rd eruRescues: map ERUId to RescueDetails post idleERUs = erus \\ dom eruRescues @ findIdleERUs() end",true,true,new String[0]);
		// 227
		addTestProgram(testData,"channels c: nat values a : nat = 10 - 11 b:nat = 20 - 10 process A = begin actions B = c!(a-b)->Skip @ Skip end",true,true,new String[0]);
		// 228
		addTestProgram(testData,"process P = begin actions B = A1(1,2) A1 = val a:int, b: nat @ Skip  @ A1(1,1) end", true, true, new String[0]);
		// 229
		addTestProgram(testData,"types Day = nat AvailDB = map Day to nat functions CkAvail (d:Day,av:AvailDB) n:nat post n = av(d)",true,true,new String[0]);
		// 230
		addTestProgram(testData, "class C = begin operations C:()==>C C() == Skip end",true,true,new String[0]);
		// 231
		addTestProgram(testData, "class C = begin operations C:()==>C C() == Skip end process P = begin state c:C @ c := new C() end",true,true,new String[0]);
		// 232
		addTestProgram(testData, "process A = begin state i:int := 0 m:map int to (map int to int) @ m(0)(0) := 1 end",true,true,new String[0]);
		// 233
		addTestProgram(testData, "process P = begin state a: nat * nat := mk_(0,0) t: (nat * nat) * (nat * nat) @ t.#1 := a end",true,true,new String[0]);
		// 234
		
		return testData;
	}

	
	
	public static void test() {
		PDefinition def=null;
        if (def instanceof AExplicitFunctionDefinition) {
            AExplicitFunctionDefinition f = (AExplicitFunctionDefinition)def;
            if (f.getPredef() != null) f.getPredef().setName(new LexNameToken("", new LexIdentifierToken("pre_"+def.getName().getName(), false, def.getLocation())));
           }

	}

}
