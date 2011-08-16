/*
 * Copyright (c) 2009-2010 jMonkeyEngine
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
package com.jme3.gde.core.properties;

import com.jme3.asset.TextureKey;
import com.jme3.gde.core.assets.ProjectAssetManager;
import com.jme3.gde.core.properties.preview.DDSPreview;
import com.jme3.gde.core.scene.PreviewRequest;
import com.jme3.gde.core.scene.SceneApplication;
import com.jme3.gde.core.scene.SceneListener;
import com.jme3.gde.core.scene.SceneRequest;
import com.jme3.gde.core.util.TreeUtil;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Logger;
import javax.swing.DefaultListSelectionModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import jme3tools.converters.ImageToAwt;
import org.openide.util.ImageUtilities;

/**
 * Displays all textures in the ProjectAssetManager,
 * lets you select one, and shows a preview of it.
 *
 * The user can de-select the currently selected texture to specify they
 * do not want a texture, and in that case null is returned.
 * 
 * @author bowens
 */
public class TextureBrowser extends javax.swing.JDialog implements TreeSelectionListener, WindowListener {

    private ProjectAssetManager assetManager;
    private TexturePropertyEditor editor;
    private DDSPreview ddsPreview;

    public TextureBrowser(java.awt.Frame parent, boolean modal, ProjectAssetManager assetManager, TexturePropertyEditor editor) {
        super(parent, modal);
        this.assetManager = assetManager;
        this.editor = editor;
        initComponents();
        loadAvailableTextures();
        setSelectedTexture((Texture) editor.getValue());
        setLocationRelativeTo(null);



    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        imagePreviewLabel = new javax.swing.JLabel();
        infoLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(TextureBrowser.class, "TextureBrowser.title")); // NOI18N

        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTree1);

        imagePreviewLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imagePreviewLabel.setText(org.openide.util.NbBundle.getMessage(TextureBrowser.class, "TextureBrowser.imagePreviewLabel.text")); // NOI18N
        jScrollPane2.setViewportView(imagePreviewLabel);

        infoLabel.setForeground(new java.awt.Color(153, 153, 153));
        infoLabel.setText(org.openide.util.NbBundle.getMessage(TextureBrowser.class, "TextureBrowser.infoLabel.text")); // NOI18N
        infoLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(infoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cancelButton.setText(org.openide.util.NbBundle.getMessage(TextureBrowser.class, "TextureBrowser.cancelButton.text")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText(org.openide.util.NbBundle.getMessage(TextureBrowser.class, "TextureBrowser.okButton.text")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cancelButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 423, Short.MAX_VALUE)
                .addComponent(okButton)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if (setTexture()) {
            dispose();
        }
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
    if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2) {
        if (setTexture()) {
            dispose();
        }
    }
}//GEN-LAST:event_jTree1MouseClicked

    private boolean setTexture() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();

        //Object nodeInfo = node.getUserObject();
        if (node != null && node.isLeaf()) {
            String selected = TreeUtil.getPath(node.getUserObjectPath());
            selected = selected.substring(0, selected.lastIndexOf("/"));
            Texture tex = assetManager.loadTexture(selected);
            editor.setValue(tex);
            editor.setAsText(selected);
            return true;
        }
        return false;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel imagePreviewLabel;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTree jTree1;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables

    private void loadAvailableTextures() {
        if (assetManager == null) {
            return;
        }

        String[] leaves = assetManager.getTextures();
        TreeUtil.createTree(jTree1, leaves);
        TreeUtil.expandTree(jTree1, (TreeNode) jTree1.getModel().getRoot(), 1);
        jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree1.addTreeSelectionListener(this);
    }

    private void setSelectedTexture(Texture texture) {
        if (texture != null) {
            Logger.getLogger(TextureBrowser.class.getName()).finer("Looking for Texture: " + texture.getName());
            String[] path = ("/" + texture.getName()).split("/");
            TreePath parent = new TreePath((TreeNode) jTree1.getModel().getRoot());
            jTree1.expandPath(TreeUtil.buildTreePath(jTree1, parent, path, 0, true));
            jTree1.getSelectionModel().setSelectionPath(TreeUtil.buildTreePath(jTree1, parent, path, 0, false));

        }
    }

    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();

        if (node == null) //Nothing is selected.	
        {
            return;
        }

        //Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            String selected = TreeUtil.getPath(node.getUserObjectPath());
            selected = selected.substring(0, selected.lastIndexOf("/"));
            Icon newicon = null;
            if (selected.toLowerCase().endsWith(".dds")) {
                if (ddsPreview == null) {
                    ddsPreview = new DDSPreview(assetManager);
                }
                ddsPreview.requestPreview(selected, (String) node.getUserObject(), 450, 450, imagePreviewLabel, infoLabel);

            } else {
                Texture tex = assetManager.loadTexture(selected);
                newicon = ImageUtilities.image2Icon(ImageToAwt.convert(tex.getImage(), false, true, 0));
                imagePreviewLabel.setIcon(newicon);
                infoLabel.setText(" " + node.getUserObject() + "    w : " + newicon.getIconWidth() + "    h : " + newicon.getIconHeight());
            }

        } else {
            imagePreviewLabel.setIcon(null);
            infoLabel.setText("");

        }

    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        if (ddsPreview != null) {
            ddsPreview.cleanUp();
        }
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    class ToggleSelectionModel extends DefaultListSelectionModel {

        boolean gestureStarted = false;

        @Override
        public void setSelectionInterval(int index0, int index1) {
            if (isSelectedIndex(index0) && !gestureStarted) {
                super.removeSelectionInterval(index0, index1);
            } else {
                super.setSelectionInterval(index0, index1);
            }
            gestureStarted = true;
        }

        @Override
        public void setValueIsAdjusting(boolean isAdjusting) {
            if (isAdjusting == false) {
                gestureStarted = false;
            }
        }
    }
}
