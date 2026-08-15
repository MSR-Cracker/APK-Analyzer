package com.apkanalyzer.analyzer;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class NativeDetector {

    private static final Map<String, String> KNOWN_PACKERS = new HashMap<>();

    static {
        KNOWN_PACKERS.put("libjiagu.so", "Qihoo 360 Packer");
        KNOWN_PACKERS.put("libqihoo.so", "Qihoo 360 Packer");
        KNOWN_PACKERS.put("libsecneo.so", "Bangcle / SecNeo Protection");
        KNOWN_PACKERS.put("libijm.so", "Ijiami Protection");
        KNOWN_PACKERS.put("libvGuard.so", "vGuard Protection");
        KNOWN_PACKERS.put("libeup.so", "Tencent Legu Protection");
    }

    public void analyzeNativeLibs(File apkFile) {
        System.out.println("\n[+] [Native & Security Detection]");
        boolean foundProtector = false;

        try (ZipFile zip = new ZipFile(apkFile)) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.startsWith("lib/") && name.endsWith(".so")) {
                    String fileName = name.substring(name.lastIndexOf('/') + 1);
                    if (KNOWN_PACKERS.containsKey(fileName)) {
                        System.out.println(" [!] Packer Detected: " + KNOWN_PACKERS.get(fileName) + " (" + fileName + ")");
                        foundProtector = true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(" [-] Error analyzing native libraries: " + e.getMessage());
        }

        if (!foundProtector) {
            System.out.println(" [✔] No commercial packer signatures detected in native libraries.");
        }
    }
}
