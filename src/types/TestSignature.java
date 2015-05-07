package types;

import translation.Block;
import absyn.TestDeclaration;
import bytecode.CONSTRUCTORCALL;
import bytecode.LOAD;

/**
 * The signature of a piece of code of a Kitten class.
 *
 * @author <A HREF="mailto:info@l0lc47.tk">L0LC47</A>
 */

public class TestSignature extends ClassMemberSignature {

    /**
     * The intermediate Kitten code for this constructor or method.
     * This is {@code null} if this constructor or method has not been
     * translated yet.
     */

    private Block code;

    /**
     * Builds a signature for a test object.
     *
     * @param clazz the class where this code is defined
     * @param abstractSyntax the abstract syntax of the declaration of this code
     */

    private String name;
    
    public TestSignature(ClassType clazz, String name, TestDeclaration abstractSyntax) {
    	
    	super(clazz,abstractSyntax);
    	this.name = name;
    }

    @Override
    public boolean equals(Object other) {
    		if(getClass() == other.getClass()){
    			TestSignature otherT = (TestSignature) other;
    			return name == otherT.getName();
    		}
    		else
    			return false;
    }

    @Override
    public int hashCode() {
    	return getDefiningClass().hashCode();
    }

    @Override
    public String toString() {
    	return getDefiningClass().toString() + getName();
    }
    
    public String getName() {
    	return name;
    }

    /**
     * Yields the abstract syntax of this fixture declaration.
     *
     * @return the abstract syntax of this fixture declaration
     */

    @Override
    public TestDeclaration getAbstractSyntax() {
    	return (TestDeclaration) super.getAbstractSyntax();
    }

    /**
     * Yields the block where the Kitten bytecode of this fixture.
     *
     * @return the block where the Kitten bytecode of this fixture.
     */

    public Block getCode() {
    	return code;
    }

    /**
     * Sets the Kitten code of this fixture, adding
     * automatically the prefix expected for it.
     *
     * @param code the Kitten code
     */

    public void setCode(Block code) {
    	this.code = addPrefixToCode(code);
    }

    /**
     * Adds a prefix to the Kitten bytecode generated for this fixture. 
     * This allows for instance constructors to add a call to the
     * constructor to the superclass.
     *
     * @param code the code already compiled for this fixture
     * @return {@code code} with a prefix
     */

    // TODO:
    protected Block addPrefixToCode(Block code){
		// we prefix a piece of code that calls the constructor of
		// the superclass (if any)
		if (!getDefiningClass().getName().equals("Object")) {
			ClassType superclass = getDefiningClass().getSuperclass();

			code = new LOAD(0, getDefiningClass()).followedBy
				(new CONSTRUCTORCALL(superclass.constructorLookup(TypeList.EMPTY))
				.followedBy(code));
		}

		return code;
    }

    /**
     * Generates an invocation instruction that calls this fixture.
     *
     * @param classGen the class generator to be used to generate the
     *                 invocation instruction
     * @param invocationType the type of invocation required, as enumerated
     *                       inside {@code org.apache.bcel.Constants}
     * @return an invocation instruction that calls this fixture.
     */

    // TODO:
    /*protected InvokeInstruction createInvokeInstruction(JavaClassGenerator classGen, short invocationType) {
    	// we use the instruction factory in order to put automatically inside
    	// the constant pool a reference to the Java signature of this method or constructor
    	return classGen.getFactory().createInvoke
   			(getDefiningClass().toBCEL().toString(), // name of the class
			invocationType); // the type of invocation (static, special, ecc.)
    }*/
}