package java_bootcamp;

import com.google.common.collect.ImmutableList;
import de.javakaffee.kryoserializers.ArraysAsListSerializer;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;

import java.util.Arrays;
import java.util.List;

/* Our state, defining a shared fact on the ledger.
 * See src/main/java/examples/ArtState.java for an example. */
/*public class TokenState {

}*/

public class TokenState implements ContractState{
    private final Party issuer;
    private final Party owner;
    private final int amount;

    public TokenState(Party issuer, Party owner, int amount){
        this.issuer = issuer;
        this.owner = owner;
        this.amount = amount;
    }

    public Party getIssuer() {
        return issuer;
    }

    public Party getOwner() {
        return owner;
    }

    public int getAmount() {
        return amount;
    }

//    @Override
//    public List<AbstractParty> getParticipants() {
//        return ImmutableList.of(issuer, owner);
//    }

    public List<AbstractParty> getParticipants(){
        return Arrays.asList(issuer, owner);
    }

}
