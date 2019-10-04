package org.ektorp.impl.docref;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.LinkedHashSet;
import java.util.Set;
import org.ektorp.docref.DocumentReferences;
import org.ektorp.docref.FetchType;

@SuppressFBWarnings("serial")
public class LazyLounge extends BasicSofa {

  @DocumentReferences(fetch = FetchType.LAZY, backReference = "loungeId")
  private Set<Person> seatedPeople = new LinkedHashSet<Person>();

  public void setSeatedPeople(Set<Person> seatedPeople) {
    this.seatedPeople = seatedPeople;
  }

  public Set<Person> getSeatedPeople() {
    return seatedPeople;
  }

}
