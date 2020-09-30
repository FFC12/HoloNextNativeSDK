package com.holonext.holonextnativesdk.renderer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.TransformableNode;
import com.holonext.holonextnativesdk.HoloNextArViewer;
import com.holonext.holonextnativesdk.Util;
import com.holonext.holonextnativesdk.exception.HolonextSdkUnknownModelExtensionException;

public class HoloNextArRenderer {
    /**
     * Default URL.
     */
    private final static String DefaultModelUrl = "https://holonext.azurewebsites.net/api/v1/scene/public/file/5de12029362138005683f7b8/Office_Chair.glb";

    /**
     * Debug mode.If it is enabled, you can inspect while debugging, more detailed error messages.
     */
    private boolean debugMode = false;

    /**
     * An SceneForm class that will represent the 3d model to be retrieved from url
     */
    private ModelRenderable modelRenderable;

    /**
     * Set the debug mode
     * @param isEnabled
     */
    public void setDebugMode(boolean isEnabled){
        debugMode = isEnabled;
    }

    /**
     * Get the is debug mode enabled.
     * @return boolean
     */
    public boolean isDebugMode(){
        return debugMode;
    }

    /**
     * HoloNextArRenderer constructor.Using default URL.
     * @param view
     * @param context
     */
    public HoloNextArRenderer(HoloNextArViewer view, Context context){
        //use a default model URL.
        try {
            initRenderer(view,context,DefaultModelUrl);
        } catch (HolonextSdkUnknownModelExtensionException e) {
            e.printStackTrace();
            Log.e("Ar Renderer Error ",e.getMessage());
        }
    }

    /**
     * HoloNextArRenderer constructor.We can pass model url here for main usage.See the {@link HoloNextArViewer#init(String)} at line 136.
     * @param view
     * @param context
     * @param modelUrl
     */
    public HoloNextArRenderer(HoloNextArViewer view, Context context, String modelUrl){
        try {
            if (modelUrl.isEmpty() || modelUrl == null){
                initRenderer(view,context, DefaultModelUrl);
            }else {
                initRenderer(view, context, modelUrl);
            }
        } catch (HolonextSdkUnknownModelExtensionException e) {
            e.printStackTrace();
            Log.e("Ar Renderer Error ",e.getMessage());
        }
    }

    /**
     * Initialize renderer by using 3d model url.
     * @param view
     * @param context
     * @param modelUrl
     * @throws HolonextSdkUnknownModelExtensionException
     */
    private void initRenderer(HoloNextArViewer view, Context context, String modelUrl) throws HolonextSdkUnknownModelExtensionException{

        //Selected GLB as a default source type
        RenderableSource.SourceType modelFormat = RenderableSource.SourceType.GLB;

        //Specify extension of model file.
        if (Util.ExtractFileExtensionFromURI(modelUrl).equals("glb")){
            modelFormat = RenderableSource.SourceType.GLB;
        }else if (Util.ExtractFileExtensionFromURI(modelUrl).equals("gltf")){
            modelFormat = RenderableSource.SourceType.GLTF2;
        }else{
            throw new HolonextSdkUnknownModelExtensionException("Unknown source type ( model format ). GLB or GLTF types supported for now!");
        }

        //Build ModelRenderable to render our model.And specify options.
        ModelRenderable.builder()
                .setSource(context,
                        RenderableSource.builder()
                                .setSource(
                                        context,
                                        Uri.parse(modelUrl),
                                        modelFormat)
                                .setScale(1.0f).setRecenterMode(RenderableSource.RecenterMode.ROOT)
                                .build())
                .setRegistryId(modelUrl)
                .build()
                .thenAccept(renderable -> modelRenderable = renderable) //TODO: Will be documented that Java 1_7 not supported to lambda expressions.
                .exceptionally(throwable -> {
                    Toast.makeText(context,"Can't load model",Toast.LENGTH_LONG).show();
                    return null;
                });

        //We create a listener to listen if user hit to the plane.
        view.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            Anchor anchor = hitResult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(view.getArSceneView().getScene());

            TransformableNode node = new TransformableNode(view.getTransformationSystem());
            node.setParent(anchorNode);
            node.setRenderable(modelRenderable);
            node.select();

            if (debugMode)
                Toast.makeText(context, "Position : " + node.getWorldPosition().toString(), Toast.LENGTH_LONG).show();
        });
    }
}
