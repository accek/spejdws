/*
 * Copyright (C) 2007 by Instytut Podstaw Informatyki Polskiej
 * Akademii Nauk (IPI PAN; Institute of Computer Science, Polish
 * Academy of Sciences; cf. www.ipipan.waw.pl).  All rights reserved.
 *
 * This file is part of Spejd.
 *
 * Spejd is free software: it may be distributed and/or modified under 
 * the terms of the GNU General Public License version 3 as published 
 * by the Free Software Foundation and appearing in the file doc/gpl.txt
 * included in the packaging of this file.
 *
 * A commercial license is available from IPI PAN (contact
 * Michal.Ciesiolka@ipipan.waw.pl or ipi@ipipan.waw.pl for more
 * information).  Licensees holding a valid commercial license from IPI
 * PAN may use this file in accordance with that license.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING
 * THE WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE.
 */ 
 
 
package ipipan.spejd.util;

public class TimeWatch {

    long time;
    long stime;

    static TimeWatch defaultTimer = new TimeWatch();

    public TimeWatch() {
	stime = time = System.currentTimeMillis();
    }

    public void reset() {
	stime = time = System.currentTimeMillis();
    }

    public void seset() {
	time = System.currentTimeMillis();
    }

    public long getInterval() {
	long ntime = System.currentTimeMillis();
	long itime = ntime - time;
	time = ntime;
	return itime;
    }

    public void display(String desc) {
	long ntime = System.currentTimeMillis();
	System.err.println(desc+(ntime-time) + " ("+(ntime-stime)+") ms");
	time = ntime;
    }

    public static TimeWatch getDefaultTimer() {
	return defaultTimer;
    }    
}
