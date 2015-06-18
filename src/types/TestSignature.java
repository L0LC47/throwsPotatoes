package types;

import javaBytecodeGenerator.JavaClassGenerator;
import javaBytecodeGenerator.TestClassGenerator;

import org.apache.bcel.Constants;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.MethodGen;

import translation.Block;
import absyn.TestDeclaration;
import bytecode.CONSTRUCTORCALL;
import bytecode.LOAD;

/**
 * The signature of a piece of code of a Kitten class.
 *
 * @author <A HREF="mailto:info@l0lc47.tk">L0LC47</A>
 */

public class TestSignature extends CodeSignature {
    
    public TestSignature(ClassType clazz, String name, TestDeclaration abstractSyntax) {
    	super(clazz,BooleanType.INSTANCE , TypeList.EMPTY, name, abstractSyntax);
    }

    @Override
    public boolean equals(Object other) {
    		if(getClass() == other.getClass()){
    			TestSignature otherT = (TestSignature) other;
    			return getName() == otherT.getName();
    		}
    		else
    			return false;
    }

    @Override
    public String toString() {
    	return getDefiningClass() + "."
   			+ getName() + "=test";
    }
    
    /**
     * Adds a prefix to the Kitten bytecode generated for this fixture. 
     * This allows for instance constructors to add a call to the
     * constructor to the superclass.
     *
     * @param code the code already compiled for this fixture
     * @return {@code code} with a prefix
     */

    protected Block addPrefixToCode(Block code){
    	return code;
    }

    public void createTest(TestClassGenerator classGen) {
		MethodGen methodGen;
		methodGen = new MethodGen
		(Constants.ACC_PRIVATE | Constants.ACC_STATIC, // private and static
		//org.apache.bcel.generic.Type.getType(runTime.String), // return type
		new org.apache.bcel.generic.ObjectType(runTime.String.class.getName()),
		new org.apache.bcel.generic.Type[]{getDefiningClass().toBCEL()},
		null, // parameters names: we do not care
		getName(), // method's name
		classGen.getClassName(), // defining class
		classGen.generateJavaBytecode(getCode()), // bytecode of the method
		classGen.getConstantPool()
		); // constant pool
    
		// we must always call these methods before the getMethod()
		// method below. They set the number of local variables and stack
		// elements used by the code of the method
		methodGen.setMaxStack();
		methodGen.setMaxLocals();

		// we add a method to the class that we are generating
		classGen.addMethod(methodGen.getMethod());
    }
    
	public INVOKESTATIC createINVOKESTATIC(JavaClassGenerator classGen) {
		return (INVOKESTATIC) createInvokeInstruction(classGen, Constants.INVOKESTATIC);
	}
    
}