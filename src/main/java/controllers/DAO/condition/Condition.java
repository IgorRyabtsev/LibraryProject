package main.java.controllers.DAO.condition;

/**
 * Created by igor on 14.04.16.
 */

/**
 * Interface for finding book/author/order by condition
 * @author igor
 */

public interface Condition {
    /**
     * Returns the part of sql expression for finding book/author/order by condition,
     * id depends on realezation
     * @return part of sql expression
     */
    String getExpression();
}
