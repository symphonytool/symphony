/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.compassresearch.core.analysis.modelchecker.api;

/**
 *
 * @author Gleydson
 */
@SuppressWarnings("serial")
public class FormulaIntegrationException extends Exception {

    private String msg;

    public FormulaIntegrationException(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return msg;
    }
}
