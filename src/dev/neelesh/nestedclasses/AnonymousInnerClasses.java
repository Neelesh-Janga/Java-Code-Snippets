package dev.neelesh.nestedclasses;

public class AnonymousInnerClasses {
    public static void main(String[] args) {
        OuterClass4 outerClass = new OuterClass4(){
            @Override
            public void printOuterClassName() {
                System.out.println("Modified OuterClass Name");
            }
        };

        outerClass.printOuterClassName();
    }
}


class OuterClass4{

    private String outerClassName = "OuterClass4";

    public void printOuterClassName(){
        System.out.println(outerClassName);
    }
}
