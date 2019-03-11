package lambdasinaction.chap6;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lambdasinaction.chap6.GroupingTransactions.Transaction;

public class PartitioningTransactions {
	private static final Double THRESHOLD = 5000.00;

	public static List<Transaction> transactions = Arrays.asList( 
			new Transaction(City.Frankfurt, 1500.0),
			new Transaction(City.New_York, 2300.0),
			new Transaction(City.London, 9900.0),
			new Transaction(City.Frankfurt, 1100.0),
			new Transaction(City.Tokyo, 7800.0),
			new Transaction(City.Zurich, 6700.0),
			new Transaction(City.Frankfurt, 5600.0),
			new Transaction(City.New_York, 4500.0),
			new Transaction(City.Zurich, 3400.0),
			new Transaction(City.London, 3200.0),
			new Transaction(City.New_York, 4600.0),
			new Transaction(City.Tokyo, 5700.0),
			new Transaction(City.Frankfurt, 4800.0) );
	
	public static void main(String ... args) {
		partitionByThreshold();
		groupByCityPartitionByThreshold();
	}
	
	public static void groupByCityPartitionByThreshold() {
		Map<City, Map<Boolean, List<Transaction>>> transactionsByCityAndThreshold =
				transactions.parallelStream().collect(Collectors.groupingBy(Transaction::getCity,
						Collectors.partitioningBy(t -> t.getValue()>=THRESHOLD)));
		System.out.println(transactionsByCityAndThreshold);
	}

    private static void partitionByThreshold() {
    	Map<Boolean, List<Transaction>> transactionsByThreshold =
    			transactions.stream().collect(Collectors.partitioningBy(t -> t.getValue() >= THRESHOLD));
    	System.out.println(transactionsByThreshold);
    }
    
	public static class Transaction {
		private final City City;
		private final double value;

		public Transaction(City City, double value) {
			this.City = City;
			this.value = value;
		}

		public City getCity() {
			return City;
		}

		public double getValue() {
			return value;
		}

		@Override
		public String toString() {
			return City + " " + value;
		}
	}

    public enum City {
        Tokyo, Zurich, Frankfurt, London, New_York
    }
}
