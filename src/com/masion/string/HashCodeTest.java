package com.masion.string;

import java.util.HashMap;
import java.util.Map;

public class HashCodeTest {

	public static void main(String[] args) {

		//Long buffer[] =new []  Long();
		Map<String, Integer> map = new HashMap<String, Integer>();

		try {

			for(int i=0; i<6000; i++){
				String node = "node" + i;
				String node2 = "node" + (i+1) ;
				int hash = (node+node2).hashCode();
				//String hashhash= Integer.valueOf(hash).toString();

				if(map.containsKey(node)|| map.containsKey(node+node2)){
					System.out.println("conflict: " + node + " = " + hash );
				}else{
					map.put(node, hash);
					map.put(node+node2, hash);
				}

				//System.out.println(node + " = " + hash );

			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
