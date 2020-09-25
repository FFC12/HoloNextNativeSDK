package com.holonext.holonextnativesdk.renderer;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
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
    private ModelRenderable modelRenderable;

    public ArRenderer(ArView view,Context context){
        //use a default model URL.
        String modelUrl = "https://holonext.azurewebsites.net/api/v1/scene/shared-scene/5f69f1a76512d13ac47ad598/beer_test.glb";
        try {
            initRenderer(view,context,modelUrl);
        } catch (HolonextSdkUnknownModelExtensionException e) {
            e.printStackTrace();
        }
    }

    public ArRenderer(ArView view,Context context, String modelUrl){
        try {
            initRenderer(view,context,modelUrl);
        } catch (HolonextSdkUnknownModelExtensionException e) {
            e.printStackTrace();
        }
    }

    private void initRenderer(ArView view,Context context, String modelUrl) throws HolonextSdkUnknownModelExtensionException{
        //Selected GLB as a default source type
        RenderableSource.SourceType modelFormat = RenderableSource.SourceType.GLB;

        if (Util.ExtractFileExtensionFromURI(modelUrl) == "glb"){
            modelFormat = RenderableSource.SourceType.GLB;
        }else if (Util.ExtractFileExtensionFromURI(modelUrl) == "gltf"){
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
                                .setScale(1.0f)
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
//        node.setLocalScale(new Vector3(1.0f * scaleFactor,1.0f * scaleFactor, 1.0f * scaleFactor));
            node.setParent(anchorNode);
            node.setRenderable(modelRenderable);
            node.select();
        });
    }
}
