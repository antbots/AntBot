/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htwg_konstanz.antbots.common_java_package.helper;

import java.util.Stack;

/**
 *
 * @author Felix
 */
public class CommandManager {
    /** 
     * Stellt den Stack dar der die letzten Befehle speichert
     */
    private Stack<Command> undos = new Stack<Command>();
    /** 
     * Stellt den Stack dar der die zurückgesetzten Befehle speichert
     */
    private Stack<Command> redos = new Stack<Command>();
    

    /** 
     * Dient dazu einen Befhel auszuführen
     * @param c Stellt den Befehl dar der ausgeführt werden soll
     */
    public void executeCommand(Command c) {
            c.execute();
            undos.push(c);
            redos.clear();
    }
    
    /** 
     * Prüft ob der Befehl rückgängig gemacht werden kann
     * @return Kann der Befehl rückgänig gemacht werden liefert diese Methode true zurück
     */
    public boolean isUndoAvailable() {
            return !undos.empty();
    }

    /** 
     * Macht den Befehl Rückgängig
     */
    public void undo() {
            if(!undos.empty()){
                Command command1 = undos.pop();
                command1.undo();
                redos.push(command1);
            }
    }

    /** 
     * Prüft ob der Befehl wiederhergestellt werden kann
     * @return Kann der Befehl wiederhergestellt werden liefert diese Methode true zurück
     */
    public boolean isRedoAvailable() {
            return !redos.empty();
    }

    /** 
     * Stellt den Befehl wieder her
     */
    public void redo() {
            if(!redos.empty()){
                Command command1 = redos.pop();
                command1.execute();
                undos.push(command1);
            }
    }
}

