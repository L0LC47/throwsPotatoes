package absyn;

import java.io.FileWriter;

import bytecode.CONST;
import bytecode.NEWSTRING;
import bytecode.RETURN;
import bytecode.VIRTUALCALL;
import semantical.TypeChecker;
import translation.Block;
import types.BooleanType;
import types.ClassType;
import types.CodeSignature;
import types.IntType;
import types.TypeList;
import types.VoidType;

/**
 * A node of abstract syntax representing a {@code return} command.
 *
 * @author <A HREF="mailto:info@l0lc47.tk">L0LC47</A>
 */

public class Assert extends Command {

	/**
	 * The abstract syntax of the expression that has to be checked
	 */

	private Expression condition;

	/**
	 * Constructs the abstract syntax of a {@code assert} command.
	 *
	 * @param pos the position in the source file where it starts
	 *            the concrete syntax represented by this abstract syntax
	 * @param condition the abstract syntax of the expression
	 *  		that has to be checked. It must be {@code boolean}
	 */

	public Assert(int pos, Expression condition) {
		super(pos);

		this.condition = condition;
	}


	/**
	 * Adds abstract syntax class-specific information in the dot file
	 * representing the abstract syntax of the {@code assert} command.
	 * This amounts to adding an arc from the node for the {@code assert}
	 * command to the abstract syntax for {@link #condition}, if any.
	 *
	 * @param where the file where the dot representation must be written
	 */

	@Override
	protected void toDotAux(FileWriter where) throws java.io.IOException {
			linkToNode("asserted", condition.toDot(where), where);
	}

	/**
	 * Performs the type-checking of the {@code assert} command
	 * by using a given type-checker. It checks that the condition is
	 * a Boolean expression.
	 * It returns the same type-checker passed as a parameter.
	 *
	 * @param checker the type-checker to be used for type-checking
	 * @return the type-checker {@code checker} itself
	 */

	@Override
	protected TypeChecker typeCheckAux(TypeChecker checker) {
		// the condition of the assert must be a Boolean expression
		condition.mustBeBoolean(checker);
		if(!checker.isAssertAllowed())
			error("assert not allowed here");

			return checker;
	}

	/**
	 * Checks that this {@code assert} command does not contain <i>dead-code</i>, that is,
	 * commands that can never be executed. This is always false for {@code assert} commands.
	 *
	 * @return false
	 */

	@Override
	public boolean checkForDeadcode() {
		return false;
	}

	/**
	 * Translates this command into intermediate
	 * Kitten bytecode. Namely, it returns a code which evaluates the
	 * [@link #condition} of the conditional and then continues with
	 * the code for the compilation of {@link #then} or {@link #_else}.
	 * It then continues with {@code continuation}.
	 *
	 * @param continuation the continuation to be executed after this command
	 * @return the code executing this command and then
	 *         the {@code continuation}
	 */

	@Override
	public Block translate(Block continuation) {
		String out = makeFailureMessage();
		Block failed = new Block(new RETURN(IntType.INSTANCE));
		failed = new CONST(-1).followedBy(failed);
		failed = new VIRTUALCALL(ClassType.mkFromFileName("String.kit"),
				ClassType.mkFromFileName("String.kit").methodLookup("output", TypeList.EMPTY)).followedBy(failed);
		failed = new NEWSTRING(out).followedBy(failed);
		
		
		return condition.translateAsTest(continuation, failed);
	}
	

	private String makeFailureMessage() {
		//String filename = getTypeChecker().getFileName();
		String pos = getTypeChecker().calcPos(getPos());
		return "failed at " + pos; 
		//return "\t\tAssert fallita @" + filename + ":" + pos + "\n";
	}
	
}