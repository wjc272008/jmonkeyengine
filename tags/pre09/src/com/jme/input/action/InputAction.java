/*
 * Copyright (c) 2003, jMonkeyEngine - Mojo Monkey Coding
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this 
 * list of conditions and the following disclaimer. 
 * 
 * Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation 
 * and/or other materials provided with the distribution. 
 * 
 * Neither the name of the Mojo Monkey Coding, jME, jMonkey Engine, nor the 
 * names of its contributors may be used to endorse or promote products derived 
 * from this software without specific prior written permission. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */
package com.jme.input.action;

/**
 * <code>InputAction</code> defines an interface for creating input actions. 
 * These actions can correspond to any event defined by a key value. The
 * <code>InputHandler</code> class typically maintains a collection of
 * the actions and when required calls the actions <code>performAction</code>
 * method.
 * 
 * @see com.jme.input.InputHandler
 * @author Mark Powell
 * @version $Id: InputAction.java,v 1.4 2004-03-25 17:14:42 mojomonkey Exp $
 */
public interface InputAction {
    
    /**
     * 
     * <code>setSpeed</code> defines the speed at which this action occurs.
     * @param speed the speed at which this action occurs.
     */
    public void setSpeed(float speed);
    /**
     * 
     * <code>performAction</code> defines the appropriate action to take when
     * called. The action is completely class specific.
     * @param time the time value for the action.
     */
    public void performAction(float time);
    
    /**
     * 
     * <code>getKey</code> retrieves the key associated with this action. 
     * @return the key associated with the action.
     */
    public String getKey();
    
    /**
     * 
     * <code>setKey</code> sets the key associated with this action.
     * @param key the key associated with the action.
     */
    public void setKey(String key);
}