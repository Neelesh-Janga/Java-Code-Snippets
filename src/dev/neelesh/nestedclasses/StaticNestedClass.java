package dev.neelesh.nestedclasses;

public class StaticNestedClass {

    public static void main(String[] args) {
        OuterClass1 outerClass = new OuterClass1();
        OuterClass1.InnerStaticClass innerStaticClass = new OuterClass1.InnerStaticClass();
        System.out.println(innerStaticClass.getOuterClassName(outerClass));
    }
}

class OuterClass1{

    private String outerClassName = "OuterClass1";

    static class InnerStaticClass{
        public InnerStaticClass(){
            System.out.println("Instantiating InnerStaticClass");
        }

        public String getOuterClassName(OuterClass1 outerClass){
            return outerClass.outerClassName;
        }
    }
}