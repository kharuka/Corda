package java_bootcamp;

import net.corda.core.contracts.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.LedgerTransaction;
import sun.tools.jstat.Token;

import java.security.PublicKey;
import java.util.List;

import static net.corda.core.contracts.ContractsDSL.requireThat;

/* Our contract, governing how our state will evolve over time.
 * See src/main/java/examples/ArtContract.java for an example. */
public class TokenContract implements Contract{
    public static String ID = "java_bootcamp.TokenContract";

    public interface Commands extends CommandData {
        class Issue implements Commands { }
    }

    @Override
    public void verify(LedgerTransaction tx) throws IllegalArgumentException {
        if (tx.getInputStates().size() != 0)
            throw new IllegalArgumentException("Transaction should have no inputs");
        if (tx.getOutputStates().size() != 1)
            throw new IllegalArgumentException("Transaction should have one output");
        if (tx.getCommands().size() != 1)
            throw new IllegalArgumentException("Transaction should have one command");

        ContractState outputState = tx.getOutput(0);
        if (!(outputState instanceof TokenState))
            throw new IllegalArgumentException("Output should be a TokenState");
        TokenState outputTokenState = (TokenState) outputState;
        if (outputTokenState.getAmount() <= 0)
            throw new IllegalArgumentException("Token amount should be positive");

        Command<CommandData> command = tx.getCommand(0);
        if (!(command.getValue() instanceof TokenContract.Commands.Issue))
            throw new IllegalArgumentException("Command should be Issue");
        PublicKey issuerPublicKey = outputTokenState.getIssuer().getOwningKey();
        if (!(command.getSigners().contains(issuerPublicKey)))
            throw new IllegalArgumentException("Issuer must sign the issuance");
    }
}
