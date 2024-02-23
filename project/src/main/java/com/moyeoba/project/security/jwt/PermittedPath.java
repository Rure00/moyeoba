package com.moyeoba.project.security.jwt;

public class PermittedPath {
    private final static String[] OPEN_PATH = new String[] {
            "/test/*", "/login/*"
    };

    public static boolean isOpen(String url) {
        for(String path: OPEN_PATH) {
            boolean result = true;
            String openUrl = path.split("/\\*")[0];
            for(int i =0; i < openUrl.length(); i++) {
                if(openUrl.charAt(i) != url.charAt(i)) {
                    result = false;
                    break;
                }
            }

            if(result) return true;
        }

        return false;
    }
}
