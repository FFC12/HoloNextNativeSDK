package com.holonext.holonextnativesdk;

/**
 * To specify renderer type that will be using ArView
 * By default, the filament renderer provided by Google is used. This is the renderer available in ArCore.
 * If custom is selected then you may have to implement it yourself or you need to make more adjustments to use
 * a different renderer that can be added in the future (such as HoloNextRenderer).
 * <p></p><b>IMPORTANT NOTE </b>: For now , only the default renderer is available.
 */
public enum RendererType{
    DEFAULT,
    CUSTOM
}
