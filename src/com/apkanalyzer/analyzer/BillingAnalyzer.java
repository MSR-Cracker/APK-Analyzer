package com.apkanalyzer.analyzer;

import java.io.File;
import java.io.InputStream;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BillingAnalyzer {

    private static final String[] BILLING_KEYWORDS = {
        "com/android/vending/billing",
        "com/android/billingclient",
        "isPurchased",
        "isAcknowledged",
        "launchBillingFlow",
        "verifyPurchase"
    };

    public void scanBillingLogic(File apkFile) {
        System.out.println("\n[+] [In-App Billing & Logic Audit]");
        boolean billingFound = false;

        try (ZipFile zip = new ZipFile(apkFile)) {
            ZipEntry dexEntry = zip.getEntry("classes.dex");
            if (dexEntry != null) {
                try (InputStream is = zip.getInputStream(dexEntry)) {
                    byte[] bytes = is.readAllBytes();
                    String content = new String(bytes, "ISO-8859-1");

                    for (String keyword : BILLING_KEYWORDS) {
                        if (content.contains(keyword)) {
                            System.out.println(" [!] Identified Billing Marker: " + keyword);
                            billingFound = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(" [-] Error scanning DEX for billing logic: " + e.getMessage());
        }

        if (billingFound) {
            System.out.println(" [!] Security Risk: Local Purchase verification triggers detected.");
            System.out.println(" [!] Audit Recommendation: Ensure server-side validation using Google Developer APIs.");
        } else {
            System.out.println(" [✔] No standard local Billing client signatures found.");
        }
    }
}
