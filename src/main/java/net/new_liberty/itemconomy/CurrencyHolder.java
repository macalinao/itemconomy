/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.new_liberty.itemconomy;

/**
 *
 * @author simplyianm
 */
public abstract class CurrencyHolder {

    /**
     * Gets the total currency this CurrencyHolder is holding.
     *
     * @return
     */
    public abstract int balance();

    /**
     * Adds an amount of economy to this CurrencyHolder.
     *
     * @param amt
     * @return True if the add was successful
     */
    public abstract boolean add(int amt);

    /**
     * Removes some currency from this CurrencyHolder.
     *
     * @param amt
     * @return Overflow (non-zero value indicates failure, returned value is the
     * amount of single-value currency needed to complete a transaction
     */
    public abstract int remove(int amt);

    /**
     * Transfers money between CurrencyHolders.
     *
     * @param other
     * @param amt
     * @return
     */
    public boolean transfer(CurrencyHolder other, int amt) {
        if (remove(amt) != 0) {
            return false;
        }

        if (!other.add(amt)) {
            add(amt);
            return false;
        }

        return true;
    }

}
