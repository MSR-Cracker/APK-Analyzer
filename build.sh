#!/bin/bash
echo "[*] Compiling APK-Analyzer..."
mkdir -p bin
javac -d bin src/com/apkanalyzer/*/*.java
jar cfe APKAnalyzer.jar com.apkanalyzer.cli.Main -C bin .
echo "[✔] Built APKAnalyzer.jar successfully."
