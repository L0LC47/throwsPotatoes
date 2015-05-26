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

public class TestSignature extends CodeSignature {
    
    public TestSignature(ClassType clazz, String name, TestDeclaration abstractSyntax) {
    	super(clazz, VoidType.INSTANCE, TypeList.EMPTY, name, abstractSyntax);
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
/*
    @Override
    public int hashCode() {
    	return getDefiningClass().hashCode();
    }

*/
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

}