
public class MaxIntBiFunction implements MyBiFunction<Integer, Integer, Integer> {
	@Override
	public Integer apply(Integer x, Integer y) {
		return (x > y) ? x : y;
	}
}
