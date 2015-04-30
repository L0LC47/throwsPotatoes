package absyn;

import java.io.FileWriter;

import semantical.TypeChecker;
import types.ClassType;
import types.TestSignature;
import types.TypeList;
import types.VoidType;

/**
 * A node of abstract syntax representing the declaration of a constructor
 * of a Kitten class.
 *
 * @author <A HREF="mailto:info@l0lc47.tk">L0LC47</A>
 */

public class TestDeclaration extends ClassMemberDeclaration {

    /**
     * The signature of this fixture or method. This is {@code null} if this
     * fixture or method has not been type-checked yet.
     */

    private TestSignature sig;
    
	private Command body;
	private String name;

	/**
	 * Constructs the abstract syntax of a fixture declaration.
	 *
	 * @param pos the starting position in the source file of
	 *            the concrete syntax represented by this abstract syntax
	 * @param body the abstract syntax of the body of the fixture
	 * @param next the abstract syntax of the declaration of the
	 *             subsequent class member, if any
	 */

	public TestDeclaration(int pos, String name, Command body, ClassMemberDeclaration next) {
		super(pos,next);
		this.name = name;
    	this.body = body;
	}
	
	/**
     * Specifies the code signature of this fixture.
     *
     * @param cSig the code signature of this fixture.
     */

    protected void setSignature(TestSignature cSig) {
    	this.sig = cSig;
    }

    /**
     * Yields the signature of this method or fixture declaration.
     *
     * @return the signature of this method or fixture declaration.
     *         Yields {@code null} if type-checking has not been performed yet
     */

    @Override
    public TestSignature getSignature() {
    	return sig;
    }
    
    /**
    * Yields the abstract syntax of the body of the constructor or method.
    *
    * @return the abstract syntax of the body of the constructor or method
    */

    public Command getBody() {
    	return body;
    }

    public String getName() {
    	return name;
    }
	/**
	 * Adds arcs between the dot node for this piece of abstract syntax
	 * and those representing the formal parameters and body of the fixture.
	 *
	 * @param where the file where the dot representation must be written
	 */

	protected void toDotAux(FileWriter where) throws java.io.IOException {
		linkToNode("body", getBody().toDot(where), where);
	}
	
	/**
	 * Adds the signature of this fixture declaration to the given class.
	 *
	 * @param clazz the class where the signature of this fixture
	 *              declaration must be added
	 */

	@Override 
	protected void addTo(ClassType clazz) {
		TestSignature cSig = new TestSignature (clazz, name, this);

		clazz.addTest(cSig);

		// we record the signature of this constructor inside this abstract syntax

		setSignature(cSig);
	}

	/**
	 * Type-checks this fixture declaration. Namely, it builds a type-checker
	 * whose only variable in scope is {@code this} of the defining class of the
	 * fixture, and where only return instructions of type {@code void} are allowed.
	 * It then type-checks the body of the fixture in that type-checker
	 * and checks that it does not contain any dead-code.
	 *
	 * @param clazz the semantical type of the class where this constructor occurs.
	 */

	@Override
	protected void typeCheckAux(ClassType clazz) {
		
		TypeChecker checker = new TypeChecker(VoidType.INSTANCE, clazz.getErrorMsg());
		checker = checker.putVar("this", clazz);

		// we type-check the body of the constructor in the resulting type-checker
		getBody().typeCheck(checker);

		// we check that there is no dead-code in the body of the constructor
		getBody().checkForDeadcode();

		// if our superclass exists, it must contain an empty constructor,
		// that will be chained to this constructor
		if (clazz.getSuperclass() != null && clazz.getSuperclass().constructorLookup(TypeList.EMPTY) == null)
			error(checker, clazz.getSuperclass() + " has no empty constructor");

		// constructors return nothing, so that we do not check whether
		// a return statement is always present at the end of every
		// syntactical execution path in the body of a constructor
	}
}