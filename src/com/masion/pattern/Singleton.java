/**
 *
 */
package com.masion.pattern;

/**
 * @author zhang
 *
 */
public class Singleton {

	private static Singleton instance = null;

	/**
	 *
	 */
	public static Singleton getInstance() {

		System.out.println("Singleton before getInstance!  instance=" + instance);
		if (instance == null) {
			instance = new Singleton();
		}
		System.out.println("Singleton after getInstance!  instance=" + instance);

		return instance;
	}

	public void method() {

		System.out.println("Singleton method!");

	}

	private Singleton() {

		System.out.println("Singleton constructor!  instance=" + instance);

	}

	public static void main(String[] args) {

		try {

			Singleton a = Singleton.getInstance();
			System.out.println("Singleton a =" + a.hashCode());
			a.method();

			Singleton b = Singleton.class.newInstance();
			System.out.println("Singleton b =" + b.hashCode());
			b.method();

			try {

				Singleton c = (Singleton) Class.forName("com.dawning.sample.Singleton").newInstance();
				System.out.println("Singleton c =" + c.hashCode());
				c.method();

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
