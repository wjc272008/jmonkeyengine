package com.g3d.collision.bih;

import com.g3d.bounding.BoundingBox;
import com.g3d.bounding.BoundingVolume;
import com.g3d.collision.Collidable;
import com.g3d.collision.CollisionResults;
import com.g3d.collision.SweepSphere;
import com.g3d.collision.UnsupportedCollisionException;
import com.g3d.export.G3DExporter;
import com.g3d.export.G3DImporter;
import com.g3d.export.InputCapsule;
import com.g3d.export.OutputCapsule;
import com.g3d.export.Savable;
import com.g3d.math.FastMath;
import com.g3d.math.Matrix4f;
import com.g3d.math.Ray;
import com.g3d.math.Vector3f;
import com.g3d.scene.Mesh;

import com.g3d.scene.VertexBuffer.Type;
import com.g3d.util.TempVars;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static java.lang.Math.max;

public class BIHTree implements Savable {

    private Mesh mesh;
    
    // checks made with the bound to reduce range of ray
    private CollisionResults boundResults = new CollisionResults();
    private BIHNode root;
    private int maxTrisPerNode;
    private int numTris;
    private float[] pointData;
    private int[] triIndices;
    private float[] bihSwapTmp;

    private static final TriangleAxisComparator[] comparators = new TriangleAxisComparator[3];

    static {
        comparators[0] = new TriangleAxisComparator(0);
        comparators[1] = new TriangleAxisComparator(1);
        comparators[2] = new TriangleAxisComparator(2);
    }

    public BIHTree(Mesh mesh, int maxTrisPerNode){
        this.mesh = mesh;
        this.maxTrisPerNode = maxTrisPerNode;

        if (maxTrisPerNode < 1 || mesh == null)
            throw new IllegalArgumentException();

        mesh.updateCounts();
        mesh.updateBound();

        FloatBuffer vb = (FloatBuffer) mesh.getBuffer(Type.Position).getData();
        ShortBuffer ib = (ShortBuffer) mesh.getBuffer(Type.Index).getData();
       
        numTris = mesh.getTriangleCount();
        pointData = new float[numTris * 3 * 3];
        int p = 0;
        for (int i = 0; i < numTris*3; i+=3){
            int vert = ib.get(i)*3;
            pointData[p++] = vb.get(vert++);
            pointData[p++] = vb.get(vert++);
            pointData[p++] = vb.get(vert);

            vert = ib.get(i+1)*3;
            pointData[p++] = vb.get(vert++);
            pointData[p++] = vb.get(vert++);
            pointData[p++] = vb.get(vert);
            
            vert = ib.get(i+2)*3;
            pointData[p++] = vb.get(vert++);
            pointData[p++] = vb.get(vert++);
            pointData[p++] = vb.get(vert);
        }
        
        bihSwapTmp = new float[9];

        triIndices = new int[numTris];
        for (int i = 0; i < numTris; i++)
            triIndices[i] = i;
    }

    public BIHTree(Mesh mesh){
        this(mesh, 21);
    }

    public BIHTree(){
    }

    public void construct(){
        BoundingBox sceneBbox = createBox(0, numTris-1);
        root = createNode(0, numTris-1, sceneBbox, 0);
    }

    private BoundingBox createBox(int l, int r) {
        TempVars vars = TempVars.get();
        assert vars.lock();
        Vector3f min = vars.vect1.set(new Vector3f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY));
        Vector3f max = vars.vect2.set(new Vector3f(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY));

        Vector3f v1 = vars.vect3,
                 v2 = vars.vect4,
                 v3 = vars.vect5;

        for (int i = l; i <= r; i++) {
            getTriangle(i, v1,v2,v3);
            BoundingBox.checkMinMax(min, max, v1);
            BoundingBox.checkMinMax(min, max, v2);
            BoundingBox.checkMinMax(min, max, v3);
        }

        BoundingBox bbox = new BoundingBox(min,max);
        assert vars.unlock();
        return bbox;
    }

    int getTriangleIndex(int triIndex){
        return triIndices[triIndex];
    }

     private int sortTriangles(int l, int r, float split, int axis){
        int pivot = l;
        int j = r;

        TempVars vars = TempVars.get();
        assert vars.lock();
        Vector3f v1 = vars.vect1,
                 v2 = vars.vect2,
                 v3 = vars.vect3;

        while (pivot <= j){
            getTriangle(pivot, v1, v2, v3);
            v1.addLocal(v2).addLocal(v3).multLocal(FastMath.ONE_THIRD);
            if (v1.get(axis) > split){
                swapTriangles(pivot, j);
                --j;
            }else{
                ++pivot;
            }
        }

        assert vars.unlock();
        pivot = (pivot == l && j < pivot) ? j : pivot;
        return pivot;
    }

    private void setMinMax(BoundingBox bbox, boolean doMin, int axis, float value){
        Vector3f min = bbox.getMin(null);
        Vector3f max = bbox.getMax(null);

        if (doMin)
            min.set(axis, value);
        else
            max.set(axis, value);

        bbox.setMinMax(min, max);
    }

    private float getMinMax(BoundingBox bbox, boolean doMin, int axis){
        if (doMin)
            return bbox.getMin(null).get(axis);
        else
            return bbox.getMax(null).get(axis);
    }

//    private BIHNode createNode2(int l, int r, BoundingBox nodeBbox, int depth){
//        if ((r - l) < maxTrisPerNode || depth > 100)
//            return createLeaf(l, r);
//
//        BoundingBox currentBox = createBox(l, r);
//        int axis = depth % 3;
//        float split = currentBox.getCenter().get(axis);
//
//        TriangleAxisComparator comparator = comparators[axis];
//        Arrays.sort(tris, l, r, comparator);
//        int splitIndex = -1;
//
//        float leftPlane, rightPlane = Float.POSITIVE_INFINITY;
//        leftPlane = tris[l].getExtreme(axis, false);
//        for (int i = l; i <= r; i++){
//            BIHTriangle tri = tris[i];
//            if (splitIndex == -1){
//                float v = tri.getCenter().get(axis);
//                if (v > split){
//                    if (i == 0){
//                        // no left plane
//                        splitIndex = -2;
//                    }else{
//                        splitIndex = i;
//                        // first triangle assigned to right
//                        rightPlane = tri.getExtreme(axis, true);
//                    }
//                }else{
//                    // triangle assigned to left
//                    float ex = tri.getExtreme(axis, false);
//                    if (ex > leftPlane)
//                        leftPlane = ex;
//                }
//            }else{
//                float ex = tri.getExtreme(axis, true);
//                if (ex < rightPlane)
//                    rightPlane = ex;
//            }
//        }
//
//        if (splitIndex < 0){
//            splitIndex = (r - l) / 2;
//
//            leftPlane = Float.NEGATIVE_INFINITY;
//            rightPlane = Float.POSITIVE_INFINITY;
//
//            for (int i = l; i < splitIndex; i++){
//                float ex = tris[i].getExtreme(axis, false);
//                if (ex > leftPlane){
//                    leftPlane = ex;
//                }
//            }
//            for (int i = splitIndex; i <= r; i++){
//                float ex = tris[i].getExtreme(axis, true);
//                if (ex < rightPlane){
//                    rightPlane = ex;
//                }
//            }
//        }
//
//        BIHNode node = new BIHNode(axis);
//        node.leftPlane = leftPlane;
//        node.rightPlane = rightPlane;
//
//        node.leftIndex = l;
//        node.rightIndex = r;
//
//        BoundingBox leftBbox = new BoundingBox(currentBox);
//        setMinMax(leftBbox, false, axis, split);
//        node.left = createNode2(l, splitIndex-1, leftBbox, depth+1);
//
//        BoundingBox rightBbox = new BoundingBox(currentBox);
//        setMinMax(rightBbox, true, axis, split);
//        node.right = createNode2(splitIndex, r, rightBbox, depth+1);
//
//        return node;
//    }

    private BIHNode createNode(int l, int r, BoundingBox nodeBbox, int depth) {
        if ((r - l) < maxTrisPerNode || depth > 100){
            return new BIHNode(l, r);
        }
        
        BoundingBox currentBox = createBox(l, r);

        Vector3f exteriorExt = nodeBbox.getExtent(null);
        Vector3f interiorExt = currentBox.getExtent(null);
        exteriorExt.subtractLocal(interiorExt);

        int axis = 0;
        if (exteriorExt.x > exteriorExt.y){
            if (exteriorExt.x > exteriorExt.z)
                axis = 0;
            else
                axis = 2;
        }else{
            if (exteriorExt.y > exteriorExt.z)
                axis = 1;
            else
                axis = 2;
        }
        if (exteriorExt.equals(Vector3f.ZERO))
            axis = 0;

//        Arrays.sort(tris, l, r, comparators[axis]);
        float split = currentBox.getCenter().get(axis);
        int pivot = sortTriangles(l, r, split, axis);
        if (pivot == l || pivot == r)
            pivot = (r + l) / 2;

        //If one of the partitions is empty, continue with recursion: same level but different bbox
        if (pivot < l){
            //Only right
            BoundingBox rbbox = new BoundingBox(currentBox);
            setMinMax(rbbox, true, axis, split);
            return createNode(l, r, rbbox, depth+1);
        }else if (pivot > r){
            //Only left
            BoundingBox lbbox = new BoundingBox(currentBox);
            setMinMax(lbbox, false, axis, split);
            return createNode(l, r, lbbox, depth+1);
        }else{
            //Build the node
            BIHNode node = new BIHNode(axis);

            //Left child
            BoundingBox lbbox = new BoundingBox(currentBox);
            setMinMax(lbbox, false, axis, split);

            //The left node right border is the plane most right
            node.leftPlane = getMinMax(createBox(l, max(l, pivot - 1)), false, axis);
            node.left = createNode(l, max(l, pivot - 1), lbbox, depth+1); //Recursive call

            //Right Child
            BoundingBox rbbox = new BoundingBox(currentBox);
            setMinMax(rbbox, true, axis, split);
            //The right node left border is the plane most left
            node.rightPlane = getMinMax(createBox(pivot, r), true, axis);
            node.right = createNode(pivot, r, rbbox, depth+1); //Recursive call

            return node;
        }
    }

    public void getTriangle(int index, Vector3f v1, Vector3f v2, Vector3f v3){
        int pointIndex = index * 9;

        v1.set(pointData[pointIndex++],
               pointData[pointIndex++],
               pointData[pointIndex++]);

        v2.set(pointData[pointIndex++],
               pointData[pointIndex++],
               pointData[pointIndex++]);
        
        v3.set(pointData[pointIndex++],
               pointData[pointIndex++],
               pointData[pointIndex++]);
    }

    public void swapTriangles(int index1, int index2){
        int p1 = index1 * 9;
        int p2 = index2 * 9;

        // store p1 in tmp
        System.arraycopy(pointData, p1, bihSwapTmp, 0, 9);

        // copy p2 to p1
        System.arraycopy(pointData, p2, pointData, p1, 9);

        // copy tmp to p2
        System.arraycopy(bihSwapTmp, 0, pointData, p2, 9);

        // swap indices
        int tmp2 = triIndices[index1];
        triIndices[index1] = triIndices[index2];
        triIndices[index2] = tmp2;
    }

    private int collideWithRay(Ray r, 
                               Matrix4f worldMatrix,
                               BoundingVolume worldBound,
                               CollisionResults results){

        boundResults.clear();
        worldBound.collideWith(r, boundResults);
        if (boundResults.size() > 0){
            float tMin = boundResults.getClosestCollision().getDistance();
            float tMax = boundResults.getFarthestCollision().getDistance();
            if (tMax <= 0)
                tMax = Float.POSITIVE_INFINITY;
            else if (tMin == tMax)
                tMin = 0;

            if (tMin <= 0)
                tMin = 0;
            
            //tMax = min(tMax, farPlane);

            return root.intersectWhere(r, worldMatrix, this, tMin, tMax, results);
        }
        return 0;
    }

    private int collideWithSweepSphere(SweepSphere ss,
                                       Matrix4f worldMatrix,
                                       BoundingVolume worldBound,
                                       CollisionResults results){

        Vector3f min = new Vector3f(ss.getCenter());
        min.subtractLocal(ss.getDimension());

        Vector3f max = new Vector3f(ss.getCenter());
        max.addLocal(ss.getVelocity()).addLocal(ss.getDimension());

        BoundingBox bbox = new BoundingBox();
        bbox.setMinMax(min, max);

        if (worldBound.intersectsBoundingBox(bbox)){
            return root.intersectWhere(ss, bbox, worldMatrix, this, results);
        }
        return 0;
    }

    public int collideWith(Collidable other, 
                           Matrix4f worldMatrix,
                           BoundingVolume worldBound,
                           CollisionResults results){
        
        if (other instanceof SweepSphere){
            SweepSphere ss = (SweepSphere) other;
            return collideWithSweepSphere(ss, worldMatrix, worldBound, results);
        }else if (other instanceof Ray){
            Ray ray = (Ray) other;
            return collideWithRay(ray, worldMatrix, worldBound, results);
        }else{
            throw new UnsupportedCollisionException();
        }
    }

    public void write(G3DExporter ex) throws IOException {
        OutputCapsule oc = ex.getCapsule(this);
        oc.write(mesh, "mesh", null);
        oc.write(root, "root", null);
        oc.write(maxTrisPerNode, "tris_per_node", 21);
        oc.write(pointData, "points", null);
        oc.write(triIndices, "indices", null);
    }
    
    public void read(G3DImporter im) throws IOException {
        InputCapsule ic = im.getCapsule(this);
        mesh = (Mesh) ic.readSavable("mesh", null);
        root = (BIHNode) ic.readSavable("root", null);
        maxTrisPerNode = ic.readInt("tris_per_node", 21);
        pointData = ic.readFloatArray("points", null);
        triIndices = ic.readIntArray("indices", null);
    }

}