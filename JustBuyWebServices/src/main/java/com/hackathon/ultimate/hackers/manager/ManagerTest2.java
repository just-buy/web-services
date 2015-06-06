package com.hackathon.ultimate.hackers.manager;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hackathon.ultimate.hackers.config.HibernateModule;

public class ManagerTest2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Injector injector = Guice.createInjector(new HibernateModule());
		JustBuyManager manager = injector.getInstance(JustBuyManager.class);
		if (manager == null) {
			System.out.println("null");
		}

	}

}
