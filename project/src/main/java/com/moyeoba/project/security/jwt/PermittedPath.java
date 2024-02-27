package com.moyeoba.project.security.jwt;

public class PermittedPath {
    private final static String[] OPEN_PATH = new String[] {
            "/test/*", "/login/*", "/swagger-ui/*", "/configuration/ui", "/v3/api-docs/*"
    };

    public static boolean isOpen(String url) {
        for(String path: OPEN_PATH) {
            boolean result = true;

            String openUrl = "";
            if(path.split("/\\*").length != 0) {
                openUrl = path.split("/\\*")[0];
            } else {
                openUrl = path;
            }



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
