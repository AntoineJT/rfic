package com.github.antoinejt.rfic;

public class FloppyDiskException extends Throwable {
	private static final long serialVersionUID = 1L;

	public FloppyDiskException() {
		try {
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public FloppyDiskException(String ex) {
		try {
			throw new Exception(ex);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
