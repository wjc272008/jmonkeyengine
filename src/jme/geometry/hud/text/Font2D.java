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

package jme.geometry.hud.text;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.Window;

import jme.exception.MonkeyRuntimeException;

import jme.texture.TextureManager;
import jme.utility.LoggingSystem;

/**
 * <code>Font2D</code> maintains display lists for each ASCII character 
 * defined by an image. <code>Font2D</code> assumes that the texture is 256x256
 * and that the characters are 16 pixels high by 16 pixels wide. The order of
 * the characters is also important:<br>
 * 
 * <img src ="fonttable.gif"><br>
 * 
 * After the font is loaded, it can be used with a call to <code>print</code>.
 * The <code>Font2D</code> class is also printed in Ortho mode and billboarded,
 * as well as depth buffering turned off. This means that the font will be 
 * placed at a two dimensional coordinate that corresponds to screen coordinates.
 * 
 * 
 * 
 * @author Mark Powell
 * @version $Id: Font2D.java,v 1.4 2003-09-05 15:43:13 mojomonkey Exp $
 */
public class Font2D {
    public static final int NORMAL = 0;
    public static final int ITALICS = 1;

    //texture name and 
    private int texId;
    private int base;

    //Color to render the font.
    private float red, green, blue, alpha;

    private boolean isBlended = true;

    /**
     * Constructor takes a path to the texture to use of the font base. This
     * image format must be compatible with <code>TextureManager</code>'s 
     * image types. 
     * 
     * @see jme.texture.TextureManager
     * 
     * @param texture the path to the image that defines the fonts.
     */
    public Font2D(String texture) {
        red = 1.0f;
        green = 1.0f;
        blue = 1.0f;
        alpha = 1.0f;

        if (!Window.isCreated()) {
            throw new MonkeyRuntimeException("Window must be created before Font2D.");
        }

        setFontTexture(texture);

        buildDisplayList();

        LoggingSystem.getLoggingSystem().getLogger().log(
            Level.INFO,
            "Successfully created Font2D using " + texture);
    }

    /**
     * <code>setFontTexture</code> sets the texture the <code>Font2D</code> 
     * object to that which is passed in. 
     * 
     * @param texture the new texture to use.
     */
    public void setFontTexture(String texture) {
        texId =
            TextureManager.getTextureManager().loadTexture(
                texture,
                GL.GL_LINEAR,
                GL.GL_LINEAR,
                false);
    }

    /**
     * <code>setColor</code> sets the RGBA values to render the font as.
     * By default the color is white with no transparency. 
     * 
     * @param r the red component of the color.
     * @param g the green component of the color.
     * @param b the blue component of the color.
     * @param a the alpha component of the color.
     */
    public void setColor(float r, float g, float b, float a) {
        red = r;
        green = g;
        blue = b;
        alpha = a;
    }

    /**
     * <code>deleteFont</code> deletes the current display list of font objects.
     * The font will be useless until a call to <code>buildDisplayLists</code> 
     * is made.
     */
    public void deleteFont() {
        GL.glDeleteLists(base, 256);
    }

    /**
     * <code>print</code> renders the specified string to a given (x,y) location.
     * The x, y location is in terms of screen coordinates. There are currently
     * two sets of fonts supported: NORMAL and ITALICS. 
     * 
     * @param x the x screen location to start the string render.
     * @param y the y screen location to start the string render.
     * @param text the String to render.
     * @param set the mode of font: NORMAL or ITALICS.
     */
    public void print(int x, int y, String text, int set) {
        if (set > 1) {
            set = 1;
        } else if (set < 0) {
            set = 0;
        }

        TextureManager.getTextureManager().bind(texId);

        //set the GL states to how we want them.
        if (isBlended) {
            GL.glEnable(GL.GL_BLEND);
        }
        GL.glDisable(GL.GL_DEPTH_TEST);
        GL.glEnable(GL.GL_TEXTURE_2D);
        GL.glMatrixMode(GL.GL_PROJECTION);
        GL.glPushMatrix();
        GL.glLoadIdentity();
        GL.glOrtho(0, Window.getWidth(), 0, Window.getHeight(), -1, 1);
        GL.glMatrixMode(GL.GL_MODELVIEW);
        GL.glPushMatrix();
        GL.glLoadIdentity();
        GL.glTranslatef(x, y, 0);
        GL.glListBase(base - 32 + (128 * set));

        //Put the string into a "pointer"
        ByteBuffer scratch =
            ByteBuffer.allocateDirect(text.getBytes().length).order(
                ByteOrder.nativeOrder());
        scratch.put(text.getBytes());
        scratch.flip();
        GL.glColor4f(red, green, blue, alpha);
        //call the list for each letter in the string.
        GL.glCallLists(scratch);

        //reset the GL states.
        if (isBlended) {
            GL.glDisable(GL.GL_BLEND);
        }
        GL.glMatrixMode(GL.GL_PROJECTION);
        GL.glPopMatrix();
        GL.glMatrixMode(GL.GL_MODELVIEW);
        GL.glPopMatrix();
        GL.glEnable(GL.GL_DEPTH_TEST);
        GL.glDisable(GL.GL_TEXTURE_2D);
    }

    /**
     * <code>buildDisplayList</code> sets up the 256 display lists that are
     * used to render each font character. Each list quad is 16x16, as defined
     * by the font image size.
     */
    public void buildDisplayList() {
        float cx;
        float cy;

        base = GL.glGenLists(256);
        TextureManager.getTextureManager().bind(texId);

        for (int loop = 0; loop < 256; loop++) {
            cx = (loop % 16) / 16.0f;
            cy = (loop / 16) / 16.0f;

            GL.glNewList(base + loop, GL.GL_COMPILE);
            GL.glBegin(GL.GL_QUADS);
            GL.glTexCoord2f(cx, 1 - cy - 0.0625f);
            GL.glVertex2i(0, 0);
            GL.glTexCoord2f(cx + 0.0625f, 1 - cy - 0.0625f);
            GL.glVertex2i(16, 0);
            GL.glTexCoord2f(cx + 0.0625f, 1 - cy);
            GL.glVertex2i(16, 16);
            GL.glTexCoord2f(cx, 1 - cy);
            GL.glVertex2i(0, 16);
            GL.glEnd();
            GL.glTranslatef(10, 0, 0);
            GL.glEndList();
        }
    }

    /**
     * <code>toString</code> returns the string representation of this
     * font object in the Format:<br><br>
     * jme.geometry.hud.text.Font2D@1c282a1<br>
     * Color: {RGBA COLOR}<br>
     * Blended: {BOOLEAN VALUE}<br>
     * Texture: {IMAGE FILE}<br>
     * 
     * @return the string representation of this object.
     */
    public String toString() {
        String string = super.toString();
        string += "\nColor: " + red + " " + green + " " + blue + " " + alpha;
        string += "\nBlended: " + isBlended;
        string += "\nTexture: " + texId;

        return string;
    }
}
