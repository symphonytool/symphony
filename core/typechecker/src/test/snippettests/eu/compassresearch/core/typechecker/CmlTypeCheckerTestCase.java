package eu.compassresearch.core.typechecker;

import static eu.compassresearch.core.typechecker.TestUtil.addTestProgram;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class CmlTypeCheckerTestCase extends AbstractTypeCheckerTestCase
{

	@Parameters
	public static Collection<Object[]> parameter()
	{

		List<Object[]> testData = new LinkedList<Object[]>();
		// 0 // We have a challenge with class types in type-defs at top level
		addTestProgram(testData, "class T = begin end class N = begin end types TorN = T | N", true, true, new String[0]);
		// 1
		addTestProgram(testData, "process T = begin operations o:int*int ==> int o(a,b) == return a+b @ o(10,10) end", true, true, new String[0]);
		// 2
		addTestProgram(testData, "types I = int process T = begin operations o:I*I ==> int o(a,b) == return a+b @ o(10,10) end", true, true, new String[0]);
		// 3
		addTestProgram(testData, "types R :: a : int b : int " + "process P = "
				+ "  " + " " + "begin " + "  state " + "    a : int "
				+ "    k : R   " + "  actions "
				+ "    A = cases k : mk_R(a1,b1) -> a:=a1+b1 end" + " @ A "
				+ "end", true, true, new String[0]);
		// 4
		addTestProgram(testData, "types Value = int ID = nat process P = begin operations CheckMac: Value * Value * ID ==> bool CheckMac(a,b,c) == return (a*b=c) Me: () ==> bool Me() == (dcl a : Value := 2 @ return CheckMac(a,2,4)) @ Skip end ", true, true, new String[0]);
		// 5
		addTestProgram(testData, "channels out: nat1 process S1 = || k in set {1,2,3} @ [{ }] begin @ out!k -> Skip end", true, true, new String[0]);
		// 6
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| {  } |] i in set {1,2,3} @ [{ c1 }] c1!i -> Skip end", true, false, new String[0]);
		// 7
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| {a } |] i in set {1,2,3} @ [{    }] c1!i -> Skip end", true, false, new String[0]);
		// 8
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| { c1 } |] i in set {1,2,3} @ [{  a }] c1!i -> Skip end", true, false, new String[0]);
		// 9
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| { } |] i in set {1,2,3} @ [{   }] Skip end", true, true, new String[0]);
		// 10
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| {c1, a} |] i in set {1,2,3} @ [{   }] c1!i -> Skip end", true, false, new String[0]);
		// 11
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| { } |] i in set {1,2,3} @ [{  c1, a }] c1!i -> Skip end", true, false, new String[0]);
		// 12
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| { } |] i in set {1,2,3} @ [{  a  }] c1!a -> Skip end", true, false, new String[0]);
		// 13
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| { c1 } |] i in set {1,2,3} @ [{   }] c1!a -> Skip end", true, false, new String[0]);
		// 14
		addTestProgram(testData, "process T = begin operations O: () ==> () O() == Skip @ O() end", true, true, new String[0]);
		// 15
		addTestProgram(testData, "process A = begin @ let b = 2 in [b = 2] & Skip end", true, true, new String[0]);
		// 16
		addTestProgram(testData, "values a : int = 42 process A = begin values b : int = 43 @Skip end", true, true, new String[0]);
		// 17
		addTestProgram(testData, "channels c process A = begin @ ||| k in set {1,2,3} @ [{ }]c!k -> Skip end", true, false, new String[0]);
		// 18
		addTestProgram(testData, "channels c:nat1 process A = begin @ ||| k in set {1,2,3} @ [{ }]c!k -> Skip end", true, true, new String[0]);
		// 19
		addTestProgram(testData, "process K = begin actions INIT = Skip @ INIT() end", true, true, new String[0]);
		// 20
		addTestProgram(testData, "process K = begin operations INIT: () ==> () INIT() == Skip @ INIT() end", true, true, new String[0]);
		// 21
		addTestProgram(testData, "values m: map int to int = {  1 |-> 2 } process K = begin state l:int functions f:map int to int -> int f(m) == m(42) @ l := f(m) end", true, true, new String[0]);
		// 22
		addTestProgram(testData, "values m = {  1 |-> 2 } process K = begin state l:int operations f:map int to int ==> int f(m) == return m(42) @ l := f(m) end", true, true, new String[0]);
		// 23
		addTestProgram(testData, "channels a: int types book = token values mbook = { mk_token(\"Book\") |-> 1} process P = begin @  a!(mbook(book))->Skip end", true, true, new String[0]);
		// 24
		addTestProgram(testData, "types k = int channels a class A = begin end", true, true, new String[0]);
		// 25
		addTestProgram(testData, "types S = seq of char RescueDetails::k : int functions rescueDetailsToString(r : RescueDetails) s: S post s <> [] process P = begin actions MERGE2 = val eru: ERUId @ (dcl s: S,r:RescueDetails @ s := rescueDetailsToString(r); Skip ) @ Skip end", true, true, new String[0]);
		// 26
		addTestProgram(testData, "channels c1: int process P = begin actions A = val r : int @ c1!r -> Skip @ Skip end", true, true, new String[0]);
		// 27
		addTestProgram(testData, "types Id ::   type : (<ERU> | <CC>) identifier : token ERUId = Id Location = token Criticality = nat inv c == c < 4 String = seq of char RescueDetails :: target : Location criticality : Criticality process CallCentreProc = begin state erus: set of ERUId eruRescues: map ERUId to RescueDetails inv dom eruRescues subset erus and (forall i in set erus @ i.type = <ERU>) operations reAllocateERU(eru : ERUId, r : RescueDetails) frame wr eruRescues : map ERUId to RescueDetails rd erus: set of ERUId pre eru in set erus and eru in set dom eruRescues and eruRescues(eru) <> r post eru in set dom eruRescues and eruRescues(eru) = r actions FORK1 = (dcl eru : ERUId @ (dcl r : RescueDetails @ (dcl oldr: RescueDetails @  reAllocateERU(eru,r)))) @ Skip end", true, true, new String[0]);
		// 28
		addTestProgram(testData, "types SUBS = token STATUS = ( <ringing> | <speech> | <suspended>) values Connected = {<ringing>,<speech>,<suspended>} functions connected: (map SUBS to STATUS) * (map SUBS to SUBS) +> (inmap SUBS to SUBS) connected(status,number) == { |-> } free: (map SUBS to STATUS) * (map SUBS to SUBS) * (set of SUBS) +> (set of SUBS) free(status,number,subs) == subs \\ dom(status) \\ rng(connected(status,number)) class Exchange = begin end", true, true, new String[0]);
		// 29
		addTestProgram(testData, "channels a:int process P = ||| i in set {1,2,3} @ begin @ a!i -> Skip end", true, true, new String[0]);
		// 30
		addTestProgram(testData, "class T = begin state a : int functions f:int * int -> int f(x,y) == a + x + y end", true, true, new String[0]);
		// 31
		addTestProgram(testData, "types Quantity = int Price = int class C = begin state sellerBids : seq of Quantity buyerBids : seq of Quantity prices : seq of Price inv len(sellerBids) = len(buyerBids) and len(sellerBids) = len(prices) end", true, true, new String[0]);
		// 32
		addTestProgram(testData, "functions f: int -> int f(a) == a+1 pre a > 0 process P = begin @ f(2) end ", true, true, new String[0]);
		// 33
		addTestProgram(testData, "class C = begin operations public doit: int ==> () doit(a) == Skip end process P = begin state s : C @ s.doit(1) end", true, true, new String[0]);
		// 34
		addTestProgram(testData, "functions f: int -> int f(a) == a+1 pre a > 0 process P = begin @ pre_f(2) end ", true, true, new String[0]);
		// 35
		addTestProgram(testData, "types mac :: a:int b:int process P = begin functions f: mac * int -> int f(x,y) == x.a+y @ f(mk_mac(1,2),2) end", true, true, new String[0]);
		// 36
		addTestProgram(testData, "functions f: int * int -> int f(x,y) == x+y pre x > 0 process P = begin actions A = [ pre_f(0,0) ] & Skip @ A end", true, true, new String[0]);
		// 37
		addTestProgram(testData, "types ERUId = nat RescueDetails ::a:int b:int process P = begin state erus : set of ERUId eruRescues : map ERUId to RescueDetails operations findIdleERUs() idleERUs: set of ERUId frame rd erus: set of ERUId rd eruRescues: map ERUId to RescueDetails post idleERUs = erus \\ dom eruRescues @ findIdleERUs() end", true, true, new String[0]);
		// 38
		addTestProgram(testData, "channels c: nat values a : nat = 10 - 11 b:nat = 20 - 10 process A = begin actions B = c!(a-b)->Skip @ Skip end", true, true, new String[0]);
		// 39
		addTestProgram(testData, "process P = begin actions B = A1(1,2) A1 = val a:int, b: nat @ Skip  @ A1(1,1) end", true, true, new String[0]);
		// 40
		addTestProgram(testData, "types Day = nat AvailDB = map Day to nat functions CkAvail (d:Day,av:AvailDB) n:nat post n = av(d)", true, true, new String[0]);
		// 41
		addTestProgram(testData, "class C = begin operations C:()==>C C() == Skip end", true, true, new String[0]);
		// 42
		addTestProgram(testData, "class C = begin operations C:()==>C C() == Skip end process P = begin state c:C @ c := new C() end", true, true, new String[0]);
		// 43
		addTestProgram(testData, "process A = begin state i:int := 0 m:map int to (map int to int) @ m(0)(0) := 1 end", true, true, new String[0]);
		// 44
		addTestProgram(testData, "process P = begin state a: nat * nat := mk_(0,0) t: (nat * nat) * (nat * nat) @ t.#1 := a end", true, true, new String[0]);
		// 45 //test case for bug http://sourceforge.net/p/compassresearch/tickets/12/
		addTestProgram(testData, "channels a : int class A = begin state hello : int := 4 operations  A : () ==> A  A() == Skip getHello : () ==> int getHello() == return hello end process Test =  begin @ (dcl v : A, out : int @ v := new A(); out := v.getHello();a.(out) -> Skip) end", true, true, new String[0]);
		// 46 //test case for bug http://sourceforge.net/p/compassresearch/tickets/23/
		addTestProgram(testData, "types CP :: c : nat p : nat process test =  begin state myCP : [CP] operations op1: () ==> () op1() ==  (  dcl x : nat @ x := myCP.c  ) op2: (nat) ==> () op2(n) ==  (  myCP.c :=n ) @ Skip end", true, true, new String[0]);
		// 47 //Test case like above, with no optional value
		addTestProgram(testData, "types CP :: c : nat p : nat process test =  begin state myCP : CP operations op1: () ==> () op1() ==  (  dcl x : nat @ x := myCP.c  ) op2: (nat) ==> () op2(n) ==  (  myCP.c :=n ) @ Skip end", true, true, new String[0]);

		// 48 // Test case for bug https://sourceforge.net/p/compassresearch/tickets/27/
		addTestProgram(testData, "functions Good : () -> nat Good () ==  let aux = 1 in aux process Test = begin state x : int functions	Bad : () -> nat	Bad () ==  let aux = 1 in aux @ Skip end", true, true, new String[0]);
		// 49 // Test case for bug https://sourceforge.net/p/compassresearch/tickets/27/
		addTestProgram(testData, "functions Good : () -> nat Good () ==  let aux = 1 in aux process Test = begin functions	Bad : () -> nat	Bad () ==  let aux = 1 in aux @ Skip end", true, true, new String[0]);
		// 50 // Test case for bug https://sourceforge.net/p/compassresearch/tickets/27/
		addTestProgram(testData, "process Test = begin functions Bad : () -> nat	Bad () ==  let aux = 1 in aux  Bad2 : () -> nat	Bad2 () ==  aux @ Skip end", true, false, new String[0]);
		// 51 // Test case for bug https://sourceforge.net/p/compassresearch/tickets/26/
		addTestProgram(testData, "process Test = begin functions AFunc: nat -> nat AFunc(l) == 1 BFunc: nat * nat -> nat BFunc(l,u) == AFunc(l) @ Skip end", true, true, new String[0]);
		// 52 // Test case for bug https://sourceforge.net/p/compassresearch/tickets/26/
		addTestProgram(testData, "process Test2 = begin functions AFunc: nat -> nat AFunc(avar) == 1 BFunc : () -> nat BFunc() == AFunc(2) @ Skip end", true, true, new String[0]);
		// 53 // Test case for bug https://sourceforge.net/p/compassresearch/tickets/26/
		addTestProgram(testData, "process Test = begin functions BFunc : () -> nat BFunc() == AFunc(2) @ Skip end process Test2 = begin functions AFunc: nat -> nat AFunc(avar) == 1 BFunc : () -> nat BFunc() == AFunc(2) @ Skip end", true, false, new String[0]);
		// 54 // Test case for bug http://sourceforge.net/p/compassresearch/tickets/48/
		addTestProgram(testData, "process Waiter = begin @ Wait \"ao\" ; Skip end", true, false, new String[0]);
		// 55 //Negative test AEnumVarsetExpression
		addTestProgram(testData, "channels c:int*int process A = begin @ c.(1).(1) -> Skip [| {c.(2).(4).(5)} |] c.(1).(1) -> Skip  end", true, false, new String[0]);
		// 56 //Check for AEnumVarsetExpression
		addTestProgram(testData, "channels c:int*int process A = begin @ c.(1).(1) -> Skip [| {c.(1).(1)} |] c.(1).(1) -> Skip  end", true, true, new String[0]);
		// 57 //Check for FatEnumVarsetExpression
		addTestProgram(testData, "channels c:int*int process A = begin @ c.(1).(1) -> Skip [| {|c|} |] c.(1).(1) -> Skip  end", true, true, new String[0]);
		// 58 //Check for FatEnumVarsetExpression
		addTestProgram(testData, "channels c1:int*int process A = begin state a:int @ [| {|c1|} |] i in set {1,2,3} @ [{ a }] c1!i -> Skip end", true, true, new String[0]);
		// 59 //Check for AEnumVarsetExpression. Wrong type in channel compared to product type. Negative test
		addTestProgram(testData, "channels c:int*char process A = begin @ c.(1).(1) -> Skip [| {c.(1).(1)} |] c.(1).(1) -> Skip  end", true, false, new String[0]);
		// 60 //Check TC in caseANameChannelExp within CompVarsetExpressions. Fixed lookup and type comparison
		addTestProgram(testData, "channels pay, transfer: nat process Card = val i: nat @ begin state value: nat @ Skip end process Cards = || i: nat @  [ {| pay.i,transfer.i |} union { transfer.j.i.n| j:nat,n:nat}] Card(i)", true, true, new String[0]);
		// 61 //Check TC in caseANameChannelExp within CompVarsetExpressions. Fixed lookup and type comparison
		addTestProgram(testData, "channels pay, transfer: nat * nat * nat process Card = val i: nat @ begin state value: nat @ Skip end process Cards = || i: nat @  [ {| pay.i,transfer.i |} union { transfer.j.i.n| j:nat,n:nat}] Card(i)", true, true, new String[0]);
		// 62 //Bug reported by AKM. The actionType in a Guarded action can be void type.
		addTestProgram(testData, "channels a b process test = begin state v : int := 2 @   (dcl x : int := 2 @ (([v = 2] & x := v + x ; a -> Skip) [] ([v = 1] & x := 3 + x ; b -> Skip ) ) ; [x = 4] & b -> Skip ) end", true, true, new String[0]);
		// 63 //
		addTestProgram(testData, "channels	a : int	process A =	val x : nat @ begin	@ a.x -> Skip end process B  = |~| i in set {1,2,3} @ A(i)", true, true, new String[0]);
		// 64 //
		addTestProgram(testData, "channels	a : int	process A =	val x : nat @ begin	@ a.x -> Skip end process B = ; i in seq {1,2,3} @ A(i)", true, true, new String[0]);
		//65 http://sourceforge.net/p/compassresearch/tickets/92/
		addTestProgram(testData, "types String2 = seq of char Process2 :: id: String2 functions findPos(q: seq of Process2,id: String2) pos : nat pre  exists p in set (elems q) @ p.id = id post true", true, true, new String[0]);
		addTestProgram(testData, "types String2 = seq of char Process2 :: id: String2 functions findPos(q: seq of Process2,id: String2) pos : nat pre  forall p in set (elems q) @ p.id = id post true", true, true, new String[0]);
		addTestProgram(testData, "types String2 = seq of char Process2 :: id: String2 functions findPos(q: seq of Process2,id: String2) pos : nat pre  let mk_(-,p)= mk_(7, mk_Process2([])) in p.id = id  post true", true, true, new String[0]);
		addTestProgram(testData, "types String2 = seq of char Process2 :: id: String2 functions findPos: seq of Process2* String2 -> nat findPos(q,id)==  let mk_(-,p)= mk_(7, mk_Process2([])) in p.id = id", true, true, new String[0]);
		
		
			
		return testData;

	}

	public CmlTypeCheckerTestCase(String cmlSource, boolean parsesOk,
			boolean typesOk, String[] errorMessages)
	{
		super(cmlSource, parsesOk, typesOk, errorMessages);
	}

}
