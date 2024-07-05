package dev.neelesh.Streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams {

    static List<Person> persons;

    class Person{
        String name;
        int age;
        char gender;

        public Person(String name, int age, char gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    '}';
        }
    }

    static {
        Streams s = new Streams();
        persons = List.of(s.new Person("Neelesh", 10, 'M'),
                s.new Person("Swapnika", 21, 'F'),
                s.new Person("Venkat", 31, 'M'),
                s.new Person("Sandhya", 52, 'F'),
                s.new Person("Sameer", 51, 'M'),
                s.new Person("Ali", 51, 'M'));
    }

    public static void main(String[] args) {
        System.out.println(filterNamesBasedOnStartingLetter(Arrays.asList("Amar", "Anudeep", "Neel")));
        System.out.println(checkIfAnyNameStartsWithN(Arrays.asList("Amar", "Anudeep", "Neel")));
        System.out.println(filterOnNameAndAge(persons));
        System.out.println(mapToString(persons));
        System.out.println(countPeopleStartsWithA(Arrays.asList("Amar", "Anudeep", "Neel")));
        sumOfAllAges(persons);
        System.out.println(maxAge(persons));
        System.out.println(minAge(persons));
        System.out.println(removeDuplicates(persons));
        System.out.println(covertToMap(persons));
        System.out.println(iterateMethod());
        System.out.println(uniqueNamedPersons());
        System.out.println(findNumberOfMalesAgedLessThan50(persons));
        System.out.println(findSumOfAgesOfMalesLessThan50(persons));
        Arrays.stream(filterNamesLongerThan4Letters(persons)).forEach(System.out::println);
        System.out.println(concatenateUsingReduce(new String[]{"Hello", " ", "World"}));
        System.out.println(sortNamesAsc(persons));
        System.out.println(sortNamesDesc(persons));
        System.out.println(sortBasedOnName(persons));
        System.out.println(sortDescBasedOnName(persons));
        System.out.println(groupByGender(persons));
        System.out.println(groupByGenderAndGetCountOfEachGender(persons));
        System.out.println(groupByGenderAndMaxGenderCount(persons));
        System.out.println(avgAgeOfMaleAndFemale(persons));
        System.out.println(findMaxAgeInEachGender(persons));
        System.out.println(findSecondMaxAge(persons));
    }

    public static List<String> filterNamesBasedOnStartingLetter(List<String> list) {
        list.stream().filter(s -> s.startsWith("A")).forEach(System.out::println);
        return list.stream().filter(s -> s.startsWith("A")).collect(Collectors.toList());
    }

    public static String checkIfAnyNameStartsWithN(List<String> list) {
        Optional<String> optional =  list.stream().filter(s -> s.startsWith("N")).findAny();
        System.out.println(optional);
        return optional.orElse(null);
    }

    public static List<Person> filterOnNameAndAge(List<Person> persons){
        return persons.stream()
                .filter(person -> person.name.equals("Neelesh"))
                .filter(person -> person.age > 20)
                .collect(Collectors.toList());
    }

    public static List<String> mapToString(List<Person> persons){
        return persons.stream()
                .map(Person::toString)
                .collect(Collectors.toList());
    }

    public static int countPeopleStartsWithA(List<String> persons){
        return (int) persons.stream()
                .filter(p -> p.startsWith("A"))
                .count();
    }

    public static void sumOfAllAges(List<Person> persons){
//        This will work and there is another approach using IntSummaryStatistics object
//        return persons.stream()
//                .map(person -> person.age)
//                .reduce(0, Integer::sum);

        IntSummaryStatistics stats = persons.stream().collect(Collectors.summarizingInt(person -> person.age));
        System.out.println("Sum = " + stats.getSum());
        System.out.println("Count = " + stats.getCount());
        System.out.println("Max = " + stats.getMax());
        System.out.println("Min = " + stats.getMin());
    }

    public static int maxAge(List<Person> persons){
        return persons.stream().max((p1, p2) -> (p1.age - p2.age > 0 ? 1 : -1)).get().age;
    }

    public static int minAge(List<Person> persons){
        return persons.stream().min((p1, p2) -> (p1.age - p2.age < 0 ? -1 : 1)).get().age;
    }

    public static Set<String> removeDuplicates(List<Person> persons){
        return persons.stream().map(p -> p.name).collect(Collectors.toSet());
    }

    public static Map<Integer, Person> covertToMap(List<Person> persons){
        return persons.stream().collect(Collectors.toMap(p -> new Random().nextInt(1, 100), p -> p));
    }

    public static List<Integer> iterateMethod(){
        return Stream.iterate(1, element -> element + 1)
                .filter(i -> i % 2 == 0).limit(5).collect(Collectors.toList());
    }

    public static List<String> uniqueNamedPersons(){
        return persons.stream().map(p -> p.name).distinct().collect(Collectors.toList());
    }

    public static long findNumberOfMalesAgedLessThan50(List<Person> persons){
        return persons.stream().filter(person -> person.age < 50).count();
    }

    public static int findSumOfAgesOfMalesLessThan50(List<Person> persons){
        return persons.stream()
                .filter(person -> person.gender == 'M')
                .filter(p -> p.age < 50)
                .map(p -> p.age)
                .reduce(0, Integer::sum);
    }

    public static String[] filterNamesLongerThan4Letters(List<Person> persons){
        return persons.stream()
                .filter(person -> person.name.length() > 4)
                .map(person -> person.name)
                .toArray(String[]::new);
    }

    public static String concatenateUsingReduce(String[] list){
//        return Arrays.stream(list).reduce("", (a, b) -> a + b);
        return Arrays.stream(list).reduce((a, b) -> a + "" + b).get();
    }

    public static List<String> sortNamesAsc(List<Person> persons){
        return persons.stream().map(person -> person.name).sorted().toList();
    }

    public static List<String> sortNamesDesc(List<Person> persons){
        return persons.stream().map(person -> person.name).sorted(Collections.reverseOrder()).toList();
    }

    public static boolean anyElementsStartsWithA(List<Person> persons){
        return persons.stream().anyMatch(p -> p.name.startsWith("A"));
    }

    public static boolean allElementsStartsWithA(List<Person> persons){
        return persons.stream().allMatch(p -> p.name.startsWith("A"));
    }

    public static boolean noneElementsStartsWithA(List<Person> persons){
        return persons.stream().noneMatch(p -> p.name.startsWith("A"));
    }

    public static List<Person> sortBasedOnName(List<Person> persons){
        return persons.stream().sorted((p1, p2) -> p1.name.compareTo(p2.name)).toList();
    }

    public static List<Person> sortDescBasedOnName(List<Person> persons){
        return persons.stream().sorted((p1, p2) -> p2.name.compareTo(p1.name)).toList();
    }

    public static Map<Character, List<Person>> groupByGender(List<Person> persons){
        return persons.stream().collect(Collectors.groupingBy(person -> person.gender));
    }

    public static Map<Character, Long> groupByGenderAndGetCountOfEachGender(List<Person> persons){
        return persons.stream().collect(Collectors.groupingBy(person -> person.gender, Collectors.counting()));
    }

    public static Map.Entry<Character, Long> groupByGenderAndMaxGenderCount(List<Person> persons){
        return persons.stream().collect(Collectors.groupingBy(person -> person.gender, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue()).get();
    }

    public static Map<Character, Double> avgAgeOfMaleAndFemale(List<Person> persons){
        return persons.stream()
                .collect(Collectors.groupingBy(
                        person -> person.gender,
                        Collectors.averagingDouble(person -> person.age)
                ));
    }

    public static Map<Character, Optional<Person>> findMaxAgeInEachGender(List<Person> persons){
        Map<Character, Optional<Person>> map = persons.stream().collect(Collectors.groupingBy(
                person -> person.gender,
                Collectors.maxBy(Comparator.comparing(person -> person.age)))
        );

        return map;
    }

    public static List<Person> findSecondMaxAge(List<Person> persons){
        return persons.stream()
                .sorted(Comparator.comparing(p -> p.age, Collections.reverseOrder()))
                .skip(1)
                .limit(1)
                .toList();
    }
}


