package com.green.finale.utils;

import java.util.ArrayList;
import java.util.List;

import com.green.finale.entity.City;
import com.green.finale.service.LocationService;

public class Test {
	private static LocationService locaService;
	
	public static void get(int num,@SuppressWarnings("rawtypes") List list) {
		for(int i = 0;i<list.size()-1;i++) {
			if(num ==  (int)list.get(i)) {
				list.remove(i);
			}
		}
		System.out.println(list);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		List<Integer> listInt = new ArrayList<Integer>();
//		for(int i=0;i<10;i++) {
//			listInt.add(i);
//		}
//		System.out.println(listInt.toString());
//		get(5,listInt);
		List<City> listWard = new ArrayList<City>();
		listWard = locaService.getCityList();
		System.out.println(listWard.toString());
		
	}

}
