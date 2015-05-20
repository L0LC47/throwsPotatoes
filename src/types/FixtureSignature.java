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
     * The intermediate Kitten code for this constructor or method.
     * This is {@code null} if this constructor or method has not been
     * translated yet.
     */

    private Block code;
    private static int counter;

    /**
     * Builds a signature for a fixture object.
     *
     * @param clazz the class where this code is defined
     * @param abstractSyntax the abstract syntax of the declaration of this code
     */

    public FixtureSignature(ClassType clazz, FixtureDeclaration abstractSyntax) {
    	super(clazz, VoidType.INSTANCE, TypeList.EMPTY, "fixture".concat(String.valueOf(++counter)), abstractSyntax);
    }
/*
 // TODO: check
    @Override
    public String toString() {
    	return getDefiningClass().toString();
    }
*/
    /**
     * Yields the abstract syntax of this fixture declaration.
     *
     * @return the abstract syntax of this fixture declaration
     */

    @Override
    public FixtureDeclaration getAbstractSyntax() {
    	return (FixtureDeclaration) super.getAbstractSyntax();
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

}