package types;

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

}