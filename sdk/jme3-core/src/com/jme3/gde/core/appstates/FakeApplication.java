/*
 * Copyright (c) 2003-2012 jMonkeyEngine
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * 
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jme3.gde.core.appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.audio.Listener;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.Renderer;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import com.jme3.system.JmeContext.Type;
import com.jme3.system.Timer;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;

/**
 * TODO: Temporary implementation before new scene system!
 *
 * @author normenhansen
 */
public class FakeApplication extends SimpleApplication {
//    private Lookup lookup;

    private Node rootNode;
    private Node guiNode;
    private AssetManager assetManager;
    private Camera cam;
    private FakeAppStateManager appStateManager;
    private FakeRenderManager renderManager;

    public FakeApplication(Node rootNode, Node guiNode, AssetManager assetManager, Camera cam) {
        this.rootNode = rootNode;
        this.guiNode = guiNode;
        this.assetManager = assetManager;
        this.cam = cam;
        this.appStateManager = new FakeAppStateManager(this);
    }

    @Override
    public void createCanvas() {
        defaultFakeError(true);
    }

    @Override
    public void destroy() {
        defaultFakeError(true);
    }

    @Override
    protected void destroyInput() {
        defaultFakeError(true);
    }

    @Override
    public <V> Future<V> enqueue(Callable<V> callable) {
        return super.enqueue(callable);
    }

    @Override
    public void gainFocus() {
        defaultFakeError(true);
    }

    @Override
    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public AudioRenderer getAudioRenderer() {
        defaultFakeError();
        return null;
    }

    @Override
    public Camera getCamera() {
        return cam;
    }

    @Override
    public JmeContext getContext() {
        defaultFakeError();
        return null;
    }

    @Override
    public ViewPort getGuiViewPort() {
        //TODO
        defaultFakeError();
        return null;
    }

    @Override
    public InputManager getInputManager() {
        defaultFakeError();
        return null;
    }

    @Override
    public Listener getListener() {
        defaultFakeError();
        return null;
    }

    @Override
    public RenderManager getRenderManager() {
        defaultFakeError();
        return null;
    }

    @Override
    public Renderer getRenderer() {
        defaultFakeError();
        return null;
    }

    @Override
    public FakeAppStateManager getStateManager() {
        return appStateManager;
    }

    @Override
    public Timer getTimer() {
        defaultFakeError();
        return null;
    }

    @Override
    public ViewPort getViewPort() {
        //TODO
        defaultFakeError();
        return null;
    }

    @Override
    public boolean isPauseOnLostFocus() {
        return true;
    }

    @Override
    public void loseFocus() {
        defaultFakeError(true);
    }

    @Override
    public void initialize() {
        defaultFakeError(true);
    }

    @Override
    public void handleError(String errMsg, Throwable t) {
        defaultFakeError(true);
    }

    @Override
    public void requestClose(boolean esc) {
        defaultFakeError();
    }

    @Override
    public void reshape(int w, int h) {
        defaultFakeError();
    }

    @Override
    public void restart() {
        defaultFakeError();
    }

    @Override
    public void setAssetManager(AssetManager assetManager) {
        defaultFakeError(true);
    }

    @Override
    public void setSettings(AppSettings settings) {
        defaultFakeError();
    }

    @Override
    public void setPauseOnLostFocus(boolean pauseOnLostFocus) {
        defaultFakeError();
    }

    @Override
    public void start() {
        defaultFakeError();
    }

    @Override
    public void start(Type contextType) {
        defaultFakeError();
    }

    @Override
    public void startCanvas() {
        defaultFakeError();
    }

    @Override
    public void startCanvas(boolean waitFor) {
        defaultFakeError();
    }

    @Override
    public void stop() {
        defaultFakeError();
    }

    @Override
    public void stop(boolean waitFor) {
        defaultFakeError();
    }

    @Override
    public void update() {
        defaultFakeError(true);
    }

    /*
     * SimpleApplication
     */
    @Override
    public void simpleInitApp() {
        defaultFakeError(true);
    }

    @Override
    public FlyByCamera getFlyByCamera() {
        defaultFakeError();
        return null;
    }

    @Override
    public Node getGuiNode() {
        return guiNode;
    }

    @Override
    public Node getRootNode() {
        return rootNode;
    }

    @Override
    public boolean isShowSettings() {
        return true;
    }

    @Override
    public void setDisplayFps(boolean show) {
        defaultFakeError();
    }

    @Override
    public void setDisplayStatView(boolean show) {
        defaultFakeError();
    }

    @Override
    public void setTimer(Timer timer) {
        defaultFakeError();
    }

    @Override
    public void setShowSettings(boolean showSettings) {
        defaultFakeError();
    }

    @Override
    public void simpleRender(RenderManager rm) {
        defaultFakeError();
    }

    @Override
    public void simpleUpdate(float tpf) {
        defaultFakeError();
    }

    private class FakeRenderManager extends RenderManager {

        public FakeRenderManager(Renderer renderer) {
            super(null);
        }
        //TODO: also nice messages
    }

    /*
     * Internal
     */
    private void defaultFakeError() {
        defaultFakeError(false);
    }

    private void defaultFakeError(boolean severe) {
        ByteArrayOutputStream str = new ByteArrayOutputStream();
        new Throwable().printStackTrace(new PrintStream(new BufferedOutputStream(str)));
        String trace = "No stack trace available.";
        try {
            trace = str.toString("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Exceptions.printStackTrace(ex);
        }
        int idx = trace.indexOf("com.jme3.gde");
        while (idx != -1) {
            if (idx < 50) {
                continue;
            } else {
                trace = trace.substring(0, idx);
                break;
            }
        }
        showError("Fake app is fake!" + (severe ? "And WTF are you trying to do anyway?\n" : "\n") + trace);
    }

    private void showError(String msg) {
        DialogDisplayer.getDefault().notifyLater(new NotifyDescriptor.Message(
                msg,
                NotifyDescriptor.WARNING_MESSAGE));
    }

    public void updateFake(float tpf) {
//        System.out.println("UPDATE");
        appStateManager.update(tpf);
    }

    public void renderFake() {
        appStateManager.render(renderManager);
    }

    public static class FakeAppStateManager extends AppStateManager {

        private AppStateManagerNode node;
        ArrayList<AppState> states = new ArrayList<AppState>();

        public FakeAppStateManager(Application app) {
            super(app);
        }

        public List<AppState> getAddedStates() {
            return states;
        }

        @Override
        public boolean attach(AppState state) {
            boolean ret = super.attach(state);
            try {
                states.add(state);
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
//            DialogDisplayer.getDefault().notifyLater(new NotifyDescriptor.Message(
//                    "attach state",
//                    NotifyDescriptor.WARNING_MESSAGE));
            if (node != null) {
//                DialogDisplayer.getDefault().notifyLater(new NotifyDescriptor.Message(
//                        "refresh node",
//                        NotifyDescriptor.WARNING_MESSAGE));
                node.refresh();
            }
            return ret;
        }

        @Override
        public boolean detach(AppState state) {
            try {
                states.remove(state);
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
            return super.detach(state);
        }

        public void setNode(AppStateManagerNode node) {
            this.node = node;
        }
    }
}
