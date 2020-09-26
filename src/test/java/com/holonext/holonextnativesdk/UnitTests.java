package com.holonext.holonextnativesdk;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author FFC12
 * Here, unit tests of HoloNextNativeSDK's and Utility functions.
 */
public class UnitTests {
    @Test
    public void hntest_ExtractExtensionFromURI_glb(){
        String result = Util.ExtractFileExtensionFromURI("https://holonext.azurewebsites.net/api/v1/scene/shared-scene/5de12029362138005683f7b8/Office_Chair.glb");
        String expected = "glb";
        assertEquals("File extension (format) could not extract from file correctly.",expected,result);
    }

    @Test
    public void hntest_ExtractExtensionFromURI_glb2(){
        String result = Util.ExtractFileExtensionFromURI("https://holonext.azurewebsites.net/api/v1/scene/public/file/5de12029362138005683f7b8/Office_Chair.glb");
        String expected = "glb";
        assertEquals("File extension (format) could not extract from file correctly.",expected,result);
    }

    @Test
    public void hntest_ExtractExtensionFromURI_gltf(){
        String result = Util.ExtractFileExtensionFromURI("https://poly.googleusercontent.com/downloads/c/fp/1582664304658184/6XOcKsfBr4n/dbom-SZonsb/model.gltf");
        String expected = "gltf";
        assertEquals("File extension (format) could not extract from file correctly.",expected,result);
    }
}