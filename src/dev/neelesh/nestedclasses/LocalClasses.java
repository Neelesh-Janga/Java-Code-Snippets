package dev.neelesh.nestedclasses;

public class LocalClasses {
    public static void main(String[] args) {
        OuterClass3 outerClass = new OuterClass3();
        System.out.println(outerClass.getOuterClassName());
    }
}


class OuterClass3{

    private String outerClassName = "OuterClass3";

    public String getOuterClassName(){
        String methodName = "getOuterClassName";
        class InnerClass{
            public InnerClass(){
                System.out.println("Instantiating InnerClass");
            }

            public String getOuterClassName() {
                return outerClassName;
            }

            public String getMethodName() {
                return methodName;
            }
        }

//        Triggers complier error because of the fact that local classes can only have final or effectively final variables
//        methodName = "Method Name Change";

        InnerClass innerClass = new InnerClass();
        System.out.println(innerClass.getMethodName());
        return innerClass.getOuterClassName();
    }
}