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
	Query averegeDiffQuery = em.createNamedQuery("findAverageDifferenceOfAllFirstcupUsers");
        Double averegeDiff = (Double) averegeDiffQuery.getSingleResult();
        return averegeDiff;
    }

    public int getAgeDifference(Date date) {
        Calendar userBD = GregorianCalendar.getInstance();
        userBD.setTime(date);
        
        Calendar dukesBD = new GregorianCalendar(1995, Calendar.MAY, 23);

        int yearsDif = dukesBD.get(Calendar.YEAR) - userBD.get(Calendar.YEAR);
        if ((yearsDif > 0) && 
            (dukesBD.get(Calendar.DAY_OF_YEAR) < userBD.get(Calendar.DAY_OF_YEAR))) {
            yearsDif--;
        } 
        else if ((yearsDif < 0) &&
                 (dukesBD.get(Calendar.DAY_OF_YEAR) > userBD.get(Calendar.DAY_OF_YEAR))) {
            yearsDif++;
        }

        FirstcupUser newUserEntity = new FirstcupUser(date, yearsDif);
        em.persist(newUserEntity);
        
        return yearsDif;
    }
}
