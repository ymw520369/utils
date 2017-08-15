/**
 * 
 */
package org.alan.utils;

import java.lang.management.ManagementFactory;

/**
 * @author Alan
 *
 */
public class PidUtils {
	public static String getPid() {
		// get name representing the running Java virtual machine.
		String name = ManagementFactory.getRuntimeMXBean().getName();
		// get pid
		return name.split("@")[0];
	}
}
