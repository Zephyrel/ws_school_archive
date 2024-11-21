package CSC285Test2;
import CSC285Test2.LinkManager;

public class CSC285Test2 {
	static class operator{
		protected char op;
		protected int priority;
		operator(){}
		operator(char a, int i){op=a;priority=i;}
	}
	static class rocket{
		protected int force;
		protected double angle;
		rocket(){}
		rocket(int f, double a){force=f;angle=a;}
	}
	public static void main(String[] args){
		rocket[] engines = {new rocket(30000, 75.2)};
		rocket[] corrections = {new rocket(0,12.3), 
								new rocket(15000,0.0),
								new rocket(-2000,5.0),
								new rocket(0, -14.5)};
		String[] expr={"a+(d%(c+A*b))"};
		rocket[] results = new rocket[engines.length];
		System.out.println("Note, A represents the rocket in the equation, lowercase letters represent corrections.");
		System.out.println("\n= Results =\n=================================================================\nRocket\t\tExpression\tForce\t\tAngle");
		for(int i=0; i<engines.length;i++){
			results[i]=parse(expr[i], engines[i], corrections);
			System.out.printf("%d\t\t%s\t%d\t%f",i,expr[i],results[i].force, results[i].angle);
		}

	}
	//parse should: (rules)
	//parse operands and operators and push them to the stack, in general
	//if taking a ')' operator, it should go 
	
	
	public static rocket parse(String arg, rocket engine, rocket[] corrections){
		char[] expr = arg.toCharArray();
		rocket eng = engine;
		rocket[] correct = corrections;
		int oprtrPrio=0;
		//char[] oprtr = {'%','*','+','(',')'};
		//int[] oprtrPrio = {3, 2, 1, -99, 99};
		
		LinkManager<operator> oprtrStack = new LinkManager<operator>();
		LinkManager<rocket> oprndStack = new LinkManager<rocket>();
		//Scan expression, store operands and operators into stacks
		for(int i=0;i<expr.length;i++){
			if(Character.isAlphabetic(expr[i])){
				//System.out.println(expr[i]); to help debugging purposes
				//I tried using a case statement and couldn't get it to work for some reason
				if(expr[i]=='A'){	oprndStack.push(eng);}else
				if(expr[i]=='a'){	oprndStack.push(correct[0]);}else
				if(expr[i]=='b'){	oprndStack.push(correct[1]);}else
				if(expr[i]=='c'){	oprndStack.push(correct[2]);}else
				if(expr[i]=='d'){	oprndStack.push(correct[3]);}else
				{System.out.println("Operand \""+expr[i]+"\" does not exist!");	System.exit(0);}

			}/*else if(Character.isDigit(expr[i])){	//Unused number/integer handling
				String digit=Character.toString(expr[i]);
				int length=1;
				for(int j=1;Character.isDigit(expr[i+j]);j++){
					digit+=Character.toString(expr[j]);
					length++;
				}
				oprndStack.add(new rocket(Integer.parseInt(digit), 0.0));
				i+=length;
			}*/else if(expr[i]=='%'||expr[i]=='*'||expr[i]=='+'||expr[i]=='('||expr[i]==')'){	//operator handling
				//'(' operator is pushed on the stack 
				//')' operator, pop, eval, and push operands until we find a '(' on the stack
				//System.out.println(expr[i]);
				if(expr[i]=='('){
					oprtrStack.push(new operator('(', -99));
				}else if(expr[i]==')'){
					while(oprtrStack.peek().op!='('){
						popEvalAndPush(oprtrStack, oprndStack);
					}
					oprtrStack.pop();//pop the ( node
				}else{	//neither a ( or ) operator
					if(expr[i]=='%'){	oprtrPrio=3;}else
					if(expr[i]=='*'){	oprtrPrio=2;}else
					if(expr[i]=='+'){	oprtrPrio=1;}else
					{oprtrPrio=-99;}
					if(oprtrStack.peek()==null){	//if this is going to be the first operator to go on the stack
						oprtrStack.push(new operator(expr[i],oprtrPrio));
					}
					oprtrStack.push(new operator(expr[i],oprtrPrio));
				}
			}//end of operator handling
		}//end of for loop
		while(oprndStack.size()!=1){
			popEvalAndPush(oprtrStack, oprndStack);
		}
		return oprndStack.pop();
	}
	public static rocket eval(rocket oper1, char oper, rocket oper2){//DONE
		//rocket result = new rocket();
		switch(oper){
			case '%':	return new rocket(oper2.force, oper1.angle+oper2.angle);
			case '*':	return new rocket(oper2.force+oper1.force, oper2.angle);
			case '+':	return new rocket(oper1.force+oper2.force, oper1.angle+oper2.angle);
			default:	System.out.println("Bad Operator!");return new rocket(0,0);
		}
	}
	public static void popEvalAndPush (LinkManager<operator> oprtrStack, LinkManager<rocket> oprndStack){//DONE
		rocket a,b,c = new rocket();
		char operandx=oprtrStack.pop().op;
		a=oprndStack.pop();
		b=oprndStack.pop();
		c=eval(b,operandx,a);
		oprndStack.push(c);
		return;
	}
}
