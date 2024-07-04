package dev.neelesh.nestedclasses;

public class InnerClasses {
    public static void main(String[] args) {
        OuterClass2 outerClass = new OuterClass2();
        OuterClass2.InnerClass innerClass = outerClass.new InnerClass();
        System.out.println(innerClass.getOuterClassName());
    }
}

class OuterClass2 {

    private String outerClassName = "OuterClasses2";

    class InnerClass{
        public InnerClass() {
            System.out.println("Instantiating InnerClass");
        }

        public String getOuterClassName() {
            return outerClassName;
        }
    }
}
