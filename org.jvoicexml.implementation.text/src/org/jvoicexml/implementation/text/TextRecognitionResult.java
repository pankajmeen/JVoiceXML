/*
 * JVoiceXML - A free VoiceXML implementation.
 *
 * Copyright (C) 2007-2015 JVoiceXML group - http://jvoicexml.sourceforge.net
 * The JVoiceXML group hereby disclaims all copyright interest in the
 * library `JVoiceXML' (a free VoiceXML implementation).
 * JVoiceXML group, $Date$, Dirk Schnelle-Walka, project lead
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Library General Public
 *  License as published by the Free Software Foundation; either
 *  version 2 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Library General Public License for more details.
 *
 *  You should have received a copy of the GNU Library General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package org.jvoicexml.implementation.text;

import org.jvoicexml.RecognitionResult;
import org.jvoicexml.event.error.SemanticError;
import org.jvoicexml.interpreter.datamodel.DataModel;
import org.jvoicexml.xml.srgs.ModeType;

/**
 * A recognition result for text based input. Since textual input is less error
 * prone than pure speech recognition we will always accept the result with full
 * confidence.
 *
 * @author Dirk Schnelle-Walka
 * @since 0.6
 */
final class TextRecognitionResult implements RecognitionResult {
    /** The semantic interpretation of the utterance. */
    private final Object interpretation;

    /** The received utterance. */
    private final String utterance;

    /** The last reached mark. */
    private String mark;

    /**
     * Constructs a new object.
     * 
     * @param text
     *            the received text.
     * @param semanticInterpretation
     *            the semantic interpretation of the input
     */
    public TextRecognitionResult(final String text,
            final Object semanticInterpretation) {
        utterance = text;
        interpretation = semanticInterpretation;
    }

    /**
     * {@inheritDoc}
     */
    public float getConfidence() {
        return 1.0f;
    }

    /**
     * {@inheritDoc}
     */
    public ModeType getMode() {
        return ModeType.VOICE;
    }

    /**
     * {@inheritDoc}
     */
    public String getUtterance() {
        return utterance;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isAccepted() {
        return interpretation != null;
    }

    /**
     * {@inheritDoc}
     */
    public void setMark(final String newMark) {
        mark = newMark;
    }

    /**
     * {@inheritDoc}
     */
    public String getMark() {
        return mark;
    }

    /**
     * {@inheritDoc}
     */
    public String[] getWords() {
        if (utterance == null) {
            return null;
        }
        return utterance.split(" ");
    }

    /**
     * {@inheritDoc}
     */
    public float[] getWordsConfidence() {
        final String[] words = getWords();
        final float[] confidences = new float[words.length];
        for (int i = 0; i < confidences.length; i++) {
            confidences[i] = 1.0f;
        }
        return confidences;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getSemanticInterpretation(final DataModel model)
            throws SemanticError {
        return interpretation;
    }
}
