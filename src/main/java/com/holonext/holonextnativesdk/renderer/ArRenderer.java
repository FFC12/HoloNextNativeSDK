package com.holonext.holonextnativesdk.renderer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.TransformableNode;
import com.holonext.holonextnativesdk.ArView;
import com.holonext.holonextnativesdk.Util;
import com.holonext.holonextnativesdk.exception.HolonextSdkUnknownModelExtensionException;

public class ArRenderer {
    private boolean debugMode = false;
    private ModelRenderable modelRenderable;

    public void setDebugMode(boolean isEnabled){
        debugMode = isEnabled;
    }

    public boolean isDebugMode(){
        return debugMode;
    }

    public ArRenderer(ArView view,Context context){
        //use a default model URL.
        String modelUrl = "https://holonext.azurewebsites.net/api/v1/scene/public/file/5de12029362138005683f7b8/Office_Chair.glb";
        try {
            initRenderer(view,context,modelUrl);
        } catch (HolonextSdkUnknownModelExtensionException e) {
            e.printStackTrace();
            Log.e("Ar Renderer Error ",e.getMessage());
        }
    }

    public ArRenderer(ArView view,Context context, String modelUrl){
        try {
            initRenderer(view,context,modelUrl);
        } catch (HolonextSdkUnknownModelExtensionException e) {
            e.printStackTrace();
            Log.e("Ar Renderer Error ",e.getMessage());
        }
    }

    private void initRenderer(ArView view,Context context, String modelUrl) throws HolonextSdkUnknownModelExtensionException{
        //Selected GLB as a default source type
        RenderableSource.SourceType modelFormat = RenderableSource.SourceType.GLB;

        if (Util.ExtractFileExtensionFromURI(modelUrl).equals("glb")){
            modelFormat = RenderableSource.SourceType.GLB;
        }else if (Util.ExtractFileExtensionFromURI(modelUrl).equals("gltf")){
            modelFormat = RenderableSource.SourceType.GLTF2;
        }else{
            throw new HolonextSdkUnknownModelExtensionException("Unknown source type ( model format ). GLB or GLTF types supported for now!");
        }

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

        view.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            Anchor anchor = hitResult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(view.getArSceneView().getScene());

            TransformableNode node = new TransformableNode(view.getTransformationSystem());
            node.setParent(anchorNode);
            node.setRenderable(modelRenderable);
            node.select();

            if(debugMode)
                Toast.makeText(context,"Position : " + node.getWorldPosition().toString(),Toast.LENGTH_LONG).show();
        });
    }
}
