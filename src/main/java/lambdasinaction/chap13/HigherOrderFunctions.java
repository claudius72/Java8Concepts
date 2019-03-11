package lambdasinaction.chap13;

import java.util.Arrays;

public class HigherOrderFunctions {
	public static void main(String[] args) {
    	Arrays.asList(3, 13, 22)
		.stream()
		.map(num -> num * 3)
		.forEach(System.out::println);
	}
}
