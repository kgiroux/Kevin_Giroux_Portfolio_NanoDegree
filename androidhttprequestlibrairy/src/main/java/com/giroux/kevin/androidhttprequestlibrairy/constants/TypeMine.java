package com.giroux.kevin.androidhttprequestlibrairy.constants;

/**
 * Created by kevin on 28/04/2016. Kevin Giroux Portfolio
 */
public enum TypeMine {
    APPLICATION("application"),
    APPLICATION_JAVASCRIPT("application/javascript"),
    APPLICATION_STREAM("application/octet-stream"),
    APPLICATION_OGG("application/ogg"),
    APPLICATION_PDF("application/pdf"),
    APPLICATION_XHTML("application/xhtml+xml"),
    APPLICATION_JSON("application/json"),
    APPLICATION_XML("application/xml"),
    APPLICATION_ZIP("application/zip"),
    AUDIO("audio"),
    AUDIO_MPEG("audio/mpeg"),
    AUDIO_X_MS_WMA("audio/x-ms-wma"),
    IMAGE("image"),
    IMAGE_GIF("image/gif"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_TIFF("image/tiff"),
    IMAGE_MICROSOFT_ICON("image/vnd.microsoft.icon"),
    IMAGE_X_ICONE("image/x-icon"),
    IMAGE_SVG("image/svg+xml"),
    IMAGE_WEBP("image/webp"),
    MODEL("model"),
    MULTIPART("multipart"),
    MULTIPART_MIXED("multipart/mixed"),
    MULTIPART_ALTERNATIVE("multipart/alternative"),
    MULTIPART_RELATED("multipart/related"),
    TEXT("text"),
    TEXT_CSS("text/css"),
    TEXT_CSV("text/csv"),
    TEXT_HTML("text/html"),
    TEXT_JAVASCRIPT("text/javascript"),
    TEXT_PLAIN("text/plain"),
    TEXT_XML("text/xml"),
    VIDEO("video"),
    VIDEO_MPEG("video/mpeg"),
    VIDEO_MP4("video/mp4"),
    VIDEO_QUICKTIME("video/quicktime"),
    VIDEO_WMV("video/x-ms-wmv"),
    VIDEO_MSVIDEO("video/x-msvideo"),
    VIDEO_XFLV("video/x-flv"),
    VIDEO_WEBM("video/webm"),
    VND_DOCUMENT_OPEN("application/vnd.oasis.opendocument.text"),
    VND_SPEADSHEET("application/vnd.oasis.opendocument.spreadsheet"),
    VND_PRESENTATIOn_OPEN("application/vnd.oasis.opendocument.presentation"),
    VND_GRAPHIC_OPEN("application/vnd.oasis.opendocument.graphics"),
    VND_MSEXCEL("application/vnd.ms-excel"),
    VND_OPEN_XML("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    VND_PPT("application/vnd.ms-powerpoint"),
    VND_PPT_2007("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    VND_WORD("application/msword"),
    VND_WORD2007("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    VND_MOZILLA_XUL("application/vnd.mozilla.xul+xml");

    private final String type;

    private TypeMine(String s) {
        type = s;
    }

    public boolean equalsName(String otherName) {
        return otherName != null && type.equals(otherName);
    }

    public String toString() {
        return this.type;
    }
    }
