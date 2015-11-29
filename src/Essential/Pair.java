package Essential;

/**
 * C++ std::pair 비스무리한 것
 * @author a
 *
 * @param <T1> std::pair first와 같음
 * @param <T2> std::pair second와 같음
 * 
 */

public class Pair<T1,T2>
{
	public T1 first;
	public T2 second;
	
	public Pair() { first = null; second = null; }
	public Pair(T1 newFirst, T2 newSecond) { first = newFirst; second = newSecond; }
}
