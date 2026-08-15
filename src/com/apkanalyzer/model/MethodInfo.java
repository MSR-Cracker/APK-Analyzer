package com.apkanalyzer.model;

public class MethodInfo {
    private String className;
    private String methodName;
    private String signature;

    public MethodInfo(String className, String methodName, String signature) {
        this.className = className;
        this.methodName = methodName;
        this.signature = signature;
    }

    public String getClassName() { return className; }
    public String getMethodName() { return methodName; }
    public String getSignature() { return signature; }

    @Override
    public String toString() {
        return className + "->" + methodName + signature;
    }
}
