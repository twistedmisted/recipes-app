package ua.kpi.klopotenkoapp.contract.util;

public enum VideoExtension {
    MP4("video/mp4"),
    WMV("video/wmv"),
    AVI("video/avi"),
    MKV("video/mkv");

    private static final VideoExtension[] VIDEO_EXTENSIONS = values();
    private final String extension;

    VideoExtension(String extension) {
        this.extension = extension;
    }

    public static boolean exists(String extension) {
        for (VideoExtension videoExtension : VIDEO_EXTENSIONS) {
            if (videoExtension.getExtension().equals(extension)) {
                return true;
            }
        }
        return false;
    }

    public String getExtension() {
        return extension;
    }
}
