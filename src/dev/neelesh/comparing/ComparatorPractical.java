package dev.neelesh.comparing;

import java.util.*;

public class ComparatorPractical {

    private static List<Student> students;

    class Student implements Comparable<Student> {
        String name;
        int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public int compareTo(Student that) {
            int res = this.age - that.age;
            return res == 0 ? 0 : (res < 0 ? -1 : 1);
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    static {
        ComparatorPractical practical = new ComparatorPractical();
        students = new ArrayList<>();
        students.add(practical.new Student("Neelesh", 24));
        students.add(practical.new Student("Jatin", 24));
        students.add(practical.new Student("Syam", 23));
        students.add(practical.new Student("Araf", 22));
        students.add(practical.new Student("Swapnika", 35));
        students.add(practical.new Student("Sandhya", 42));
    }

    public static void main(String[] args) {
        Comparator<Integer> compare = Integer::compareTo;
        Comparator<Integer> compareBasedOnUnitPlace = (a, b) -> {a = a%10; b = b%10; return a.compareTo(b);};
        List<Integer> l = new ArrayList<>(List.of(33, 42, 14, 21, 65));
        l.sort(compare);
        System.out.println("Sorted List: " + l);
        l.sort(compareBasedOnUnitPlace);
        System.out.println("List based on Units Placed Sorting: " + l);

        System.out.println(students);
        Collections.sort(students);
        System.out.println(students);
    }
}
