package jisheng.buffer;

import java.nio.IntBuffer;

public class TestBuffer {
	public static void main(String[] args) {
		IntBuffer intBf=IntBuffer.allocate(10);
		int[] arrys= {1,2,3,4};
		int[] arrys2= new int[6];
		intBf.put(arrys);
		System.out.println("flip之前:"+intBf);
		intBf.put(arrys);
		intBf.flip();
		System.out.println("flip之后:"+intBf);
		for (int i = 0; i < intBf.limit(); i++) {
			System.out.println(intBf.get(i));
			
		}
		
	}

}
