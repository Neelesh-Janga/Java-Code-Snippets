package dev.neelesh.jni;

public class JNIDemo {

    public native void sayHello();

    static {
        System.loadLibrary("jni_demo");
    }

    public static void main(String[] args) {
        JNIDemo demo = new JNIDemo();
        demo.sayHello();
    }
}
