package org.ektorp.impl.docref;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.LinkedHashSet;
import java.util.Set;
import org.ektorp.docref.CascadeType;
import org.ektorp.docref.DocumentReferences;
import org.ektorp.docref.FetchType;
import org.ektorp.support.CouchDbDocument;

@SuppressFBWarnings("serial")
public class SetLounge extends CouchDbDocument {

  @DocumentReferences(fetch = FetchType.EAGER, backReference = "loungeId", orderBy = "shoeSize",
      descendingSortOrder = false, cascade = CascadeType.ALL)
  private Set<Person> seatedPeople = new LinkedHashSet<Person>();
  private String color;

  public Set<Person> getSeatedPeople() {
    return seatedPeople;
  }

  public void setSeatedPeople(Set<Person> seatedPeople) {
    this.seatedPeople = seatedPeople;
  }

  public void sitDown(Person person) {
    person.setLoungeId(getId());
    getSeatedPeople().add(person);
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getColor() {
    return color;
  }

}
