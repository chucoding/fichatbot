import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		
		//1. List, Map, String, int 등 자유롭게 활용하여 결과화면처럼 나타나도록 콘솔에 출력
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, List<Integer>> submap = new HashMap<String, List<Integer>>();
		List<Integer> semiList = new ArrayList<Integer>();
		
		semiList.add(10);
		semiList.add(11);
		semiList.add(12);
		
		List<Integer> finalList = new ArrayList<Integer>();
		
		finalList.add(11);
		finalList.add(12);
		finalList.add(23);
		
		submap.put("semi",semiList);
		submap.put("final", finalList);
		
		map.put("person", "사람");
		map.put("sports", "야구");
		map.put("score",submap);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("person", "사람2");
		map2.put("sports", "축구");
		
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("person", "사람3");
		map3.put("sports", "농구");
		
		list.add(map);
		list.add(map2);
		list.add(map3);
		
		System.out.println("정답은?");
		System.out.println(list.toString());		
		
		//2. 첫번째 사람의 두번째 세미 스코어값을 출력하시오.. 11로 나와야함!
		
		Map<String, Object> res = list.get(0);
		Map<String, List<Integer>> scoreMap = (Map<String, List<Integer>>) map.get("score");
		
		List<Integer> semi = scoreMap.get("semi");
		int value = semi.get(1);
		
		System.out.println(value);

	}

}
