
public class EvenPredicate implements MyPredicate<Integer> {
	@Override
	public boolean test(Integer n) {
		return n % 2 == 0;
	}
}
