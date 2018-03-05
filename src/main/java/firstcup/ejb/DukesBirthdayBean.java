/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package firstcup.ejb;

import firstcup.entity.FirstcupUser;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * DukesBirthdayBean is a stateless session bean that calculates the age
 * difference between a user and Duke, who was born on May 23, 1995.
 */
@Stateless
public class DukesBirthdayBean {

    private static final Logger logger =
            Logger.getLogger("firstcup.ejb.DukesBirthdayBean");

    @PersistenceContext
    private EntityManager em;

    public Double getAverageAgeDifference() {
	Query averageDiffQuery = em.createNamedQuery("findAverageDifferenceOfAllFirstcupUsers");
        Double averageAgeDiff = (Double) averageDiffQuery.getSingleResult();
        logger.log(Level.INFO, "Average Age Difference is: {0}", averageAgeDiff);
        return averageAgeDiff;
    }

    public int getAgeDifference(Date date) {
        Calendar userBD = GregorianCalendar.getInstance();
        userBD.setTime(date);
        
        Calendar dukesBD = new GregorianCalendar(1995, Calendar.MAY, 23);

        int ageDiff = dukesBD.get(Calendar.YEAR) - userBD.get(Calendar.YEAR);
        logger.log(Level.INFO, "Raw age difference is: {0}", ageDiff);
        
        // If duke is younger than user (ageDiff > 0)
        // but his birthday occurs before the user's, 
        // then their age difference is redused by 1.
        if ((ageDiff > 0) && 
            (dukesBD.get(Calendar.DAY_OF_YEAR) < userBD.get(Calendar.DAY_OF_YEAR))) {
            ageDiff--;
        } 
        // If duke is older than user (AgeDiff < 0)
        // but his birthday occurs after the users, 
        // then their age difference is increased by 1.
        else if ((ageDiff < 0) &&
                 (dukesBD.get(Calendar.DAY_OF_YEAR) > userBD.get(Calendar.DAY_OF_YEAR))) {
            ageDiff++;
        }

        FirstcupUser newUserEntity = new FirstcupUser(date, ageDiff);
        em.persist(newUserEntity);
        
        logger.log(Level.INFO, "Final Age Difference is: {0}", ageDiff);
        
        return ageDiff;
    }
}
