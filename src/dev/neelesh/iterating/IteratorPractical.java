package dev.neelesh.iterating;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorPractical {
    static StudentLinkedList sl1;

    class StudentLinkedList implements Iterable<StudentLinkedList>{
        String name;
        StudentLinkedList next;

        public StudentLinkedList(String name) {
            this.name = name;
            this.next = null;
        }

        public StudentLinkedList(String name, StudentLinkedList next) {
            this.name = name;
            this.next = next;
        }

        @Override
        public Iterator<StudentLinkedList> iterator() {
            return new Iterator<>() {
                StudentLinkedList current = StudentLinkedList.this;

                @Override
                public boolean hasNext() {
                    return current != null;
                }

                @Override
                public StudentLinkedList next() {
                    StudentLinkedList temp = current;
                    current = current.next;
                    return temp;
                }
            };
        }

        @Override
        public String toString() {
            return "StudentLinkedList{" +
                    "name='" + name + '\'' +
                    ", next=" + next +
                    '}';
        }
    }

    static {
        IteratorPractical ip = new IteratorPractical();
        StudentLinkedList sl5 = ip.new StudentLinkedList("Neelesh");
        StudentLinkedList sl4 = ip.new StudentLinkedList("Araf", sl5);
        StudentLinkedList sl3 = ip.new StudentLinkedList("Syam", sl4);
        StudentLinkedList sl2 = ip.new StudentLinkedList("Swapnika", sl3);
        sl1 = ip.new StudentLinkedList("Sandhya", sl2);
    }

    public static void main(String[] args) {
        Iterator<StudentLinkedList> iterator = sl1.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        for(StudentLinkedList sl : sl1) {
            System.out.println(sl.name);
        }

        List<Integer> list = new ArrayList<>(List.of(1,2,3,4,5));
        Iterator<Integer> itr = list.iterator();
        while(itr.hasNext()) {
            System.out.println(itr.next());
        }
    }
}
