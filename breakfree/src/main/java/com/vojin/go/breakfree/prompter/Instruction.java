package com.vojin.go.breakfree.prompter;



import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author Vojin Nikolic
 *
 ** Instruction annotation for instruction methods in the {@link InstructionsCollection} 
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Instruction {
    String instruction();
    String aliases();
    String description();
    
}
