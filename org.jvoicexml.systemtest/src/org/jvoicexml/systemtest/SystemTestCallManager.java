/*
 * JVoiceXML - A free VoiceXML implementation.
 *
 * Copyright (C) 2006-2013 JVoiceXML group - http://jvoicexml.sourceforge.net
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Library General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Library General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.jvoicexml.systemtest;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.jvoicexml.JVoiceXml;

/**
 * Caller to JVoiceXML that actually performs the system test.
 *
 * @author Zhang Nan
 * @author Dirk Schnelle-Walka
 * @version $Revision$
 * @since 0.7
 */
public final class SystemTestCallManager {
    /** Logger for this class. */
    private static final Logger LOGGER = Logger
            .getLogger(SystemTestCallManager.class);

    /** Reference to jvoicexml. */
    private JVoiceXml jvxml;

    /** The port of text server. */
    private int textServerport;

    /** Test cases expression. */
    private String testcases;

    /** Test report instance. */
    private TestCaseListener listener;

    /** Test case library instance. */
    private TestCaseLibrary testcaseLibrary;

    /** Script factory.  */
    private ScriptFactory scriptFactory;

    /**
     * Sets the reference to JVoiceXml.
     * @param jvoicexml reference to JVoiceXML.
     * @since 0.7.3
     */
    public void setJVoiceXml(final JVoiceXml jvoicexml) {
        jvxml = jvoicexml;
    }

    /**
     * Starts the test cases.
     */
    public void performTests() {
        final Collection<TestCase> jobs = testcaseLibrary.fetch(testcases);
        LOGGER.info("There were " + jobs.size() + " test case(s).");

        final Thread testThread = selectRunningThread(true, jobs);
        if (testThread != null) {
            testThread.start();
        }
    }

    /**
     * @param create if true, create AutoTestThread, else InteractiveTestThread.
     * @param jobs test cases.
     * @return the thread will be start().
     */
    private Thread selectRunningThread(final boolean create,
            final Collection<TestCase> jobs) {
        final AutoTestThread testThread;
        if (create) {
            testThread = new AutoTestThread(jvxml, textServerport, jobs);
            testThread.setTestCaseListenr(listener);
            testThread.setScriptFactory(scriptFactory);
            return testThread;
        } else {
            LOGGER.warn("not implemented yet.");
            return null;
        }
    }

    /**
     * @param lib of test cases.
     */
    public void setTestcaseLibrary(final TestCaseLibrary lib) {
        this.testcaseLibrary = lib;
    }

    /**
     * @param cases expressions.
     */
    public void setTestcases(final String cases) {
        testcases = cases;
    }

    /**
     * @param port of Text Server.
     */
    public void setTextServerPort(final int port) {
        this.textServerport = port;
    }

    /**
     * Sets the listener for test case notifications.
     * @param testCaseListener the listener
     */
    public void setTestCaseListener(final TestCaseListener testCaseListener) {
        listener = testCaseListener;
    }

    /**
     * Sets the script factory to use.
     * @param factory which create script.
     */
    public void setScriptFactory(final ScriptFactory factory) {
        scriptFactory = factory;
    }
}
