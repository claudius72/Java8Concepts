package lambdasinaction.chap3;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
public class ExecuteAround {
	
	private static class Pair<S, T> {
		S first;
		T second;
		
		Pair(S first, T second) {
			this.first = first;
			this.second = second;
		}

		S getFirst() {
			return first;
		}

		T getSecond() {
			return second;
		}
	}

	public static void main(String ...args) throws IOException{

        // method we want to refactor to make more flexible: processFileLimited
        String result = processFileLimited();
        System.out.println(result);
        System.out.println("---");

        // we're now using the refactored method: processFile
		String oneLine = processFile((BufferedReader b) -> b.readLine());
		System.out.println(oneLine);
        System.out.println("---");

		String twoLines = processFile((BufferedReader b) -> b.readLine() + b.readLine());
		System.out.println(twoLines);
        System.out.println("---");
		
		String mostFrequentWord = processFile((BufferedReader b) -> {
			Map<String, Long> wordMap = b.lines()
					.flatMap(Pattern.compile("\\s+")::splitAsStream)
					.map(w -> new Pair<String, Integer>(w, 1))
					.collect(Collectors.groupingBy(Pair::getFirst, Collectors.counting()));
			
			String word = wordMap.entrySet().stream()
					.max(Map.Entry.comparingByValue())
					.get()
					.getKey();
			return word.toString();
		});
		System.out.println(mostFrequentWord);
        System.out.println("---");
	}

	// regular file reader
    public static String processFileLimited() throws IOException {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("/home/claudiu/git/Java8InAction/src/main/resources/lambdasinaction/chap3/data.txt"))) {
            return br.readLine();
        }
    }

    // illustrates the execute around pattern: the file processor is passed as a parameter
    // whereas the boilerplate code (made of try-with-resources, FileReader, BufferedReader) is static
	public static String processFile(BufferedReaderProcessor p) throws IOException {
		try(BufferedReader br = new BufferedReader(new FileReader("/home/claudiu/git/Java8InAction/src/main/resources/lambdasinaction/chap3/data.txt"))){
			return p.process(br);
		}

	}

	public interface BufferedReaderProcessor{
		public String process(BufferedReader b) throws IOException;

	}
}
