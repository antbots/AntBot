
package de.htwg_konstanz.antbots.common_java_package.helper;

/**
 *
 * @author Felix
 */
public interface Command {
    /** 
     * Dient dazu einen Befhel auszuführen
     */
    void execute();
    /** 
     * Dient dazu einen Befhel wiederherszustellen
     */
    void undo();
}