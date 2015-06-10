package types;

import javaBytecodeGenerator.JavaClassGenerator;
import javaBytecodeGenerator.TestClassGenerator;

import org.apache.bcel.Constants;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.MethodGen;

import translation.Block;
import absyn.FixtureDeclaration;
import bytecode.CONSTRUCTORCALL;
import bytecode.LOAD;

/**
 * The signature of a piece of code of a Kitten class.
 *
 * @author <A HREF="mailto:info@l0lc47.tk">L0LC47</A>
 */

public class FixtureSignature extends CodeSignature {

    /**
     * Builds a signature for a fixture object.
     *
     * @param clazz the class where this code is defined
     * @param name the name of this code
     * @param abstractSyntax the abstract syntax of the declaration of this code
     */

    public FixtureSignature(ClassType clazz, String name, FixtureDeclaration abstractSyntax) {
    	super(clazz, VoidType.INSTANCE, TypeList.EMPTY, name, abstractSyntax);
    }

	/**
	 * Adds a prefix to the Kitten bytecode generated for this fixture.
	 *
	 * @param code the code already compiled for this method
	 * @return {@code code} itself
	 */

	@Override
	protected Block addPrefixToCode(Block code) {
		return code;
	}
	
    @Override
    public String toString() {
    	return getDefiningClass() + "." + getName();
    }

    public void createFixture(TestClassGenerator classGen) {
		MethodGen methodGen;
		methodGen = new MethodGen
		(Constants.ACC_PRIVATE | Constants.ACC_STATIC, // public and static
		org.apache.bcel.generic.Type.VOID, // return type
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