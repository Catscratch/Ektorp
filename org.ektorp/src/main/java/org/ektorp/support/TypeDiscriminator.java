package org.ektorp.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to distinguish a type's documents in the database.
 * <p>
 * Declare on fields or getter methods in order for them to be used in generated views filter
 * conditions.
 * <p>
 * Declare on type in order specify a custom filter condition.
 * <p>
 * A TypeDiscriminator declared on type level cannot be mixed with TypeDiscriminators declared onb
 * fields.
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeDiscriminator {

  /**
   * If TypeDiscriminator is declared on type level, a filter condition must be specified. This
   * condition is inserted along other conditions in the generated views map function: function(doc)
   * { if(CONDITION INSERTED HERE &amp;&amp; doc.otherField) {emit(null, doc._id)} }
   * <p>
   * Not valid to use if declared on field or method level.
   */
  String value() default "";

}
