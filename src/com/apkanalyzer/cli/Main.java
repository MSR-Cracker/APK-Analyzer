package com.apkanalyzer.cli;

import com.apkanalyzer.analyzer.BillingAnalyzer;
import com.apkanalyzer.analyzer.NativeDetector;
import com.apkanalyzer.dex.DexScanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   🛡️ APK SECURITY & PURCHASE LOGIC ANALYZER      ");
        System.out.println("==================================================");

        if (args.length < 1) {
            System.out.println("Usage: java -jar APKAnalyzer.jar <path-to-apk>");
            return;
        }

        File apkFile = new File(args[0]);
        if (!apkFile.exists()) {
            System.out.println("[-] Error: APK file not found at " + args[0]);
            return;
        }

        System.out.println("[+] Target APK: " + apkFile.getName());
        
        // 1. Scan Protections and Shared Libraries
        NativeDetector nativeDetector = new NativeDetector();
        nativeDetector.analyzeNativeLibs(apkFile);

        // 2. Scan DEX & Billing Verification Logic
        BillingAnalyzer billingAnalyzer = new BillingAnalyzer();
        billingAnalyzer.scanBillingLogic(apkFile);

        System.out.println("\n[✔] Analysis Completed Successfully.");
    }
}
